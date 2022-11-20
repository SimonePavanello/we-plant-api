package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.Stop;
import cloud.nino.nino.domain.User;
import cloud.nino.nino.domain.Visit;
import cloud.nino.nino.domain.elasticsearch.Place;
import cloud.nino.nino.domain.enumeration.StopType;
import cloud.nino.nino.repository.UserRepository;
import cloud.nino.nino.repository.custom.EventAndLocationCustomRepository;
import cloud.nino.nino.repository.custom.StopCustomRepository;
import cloud.nino.nino.repository.custom.VisitCustomRepository;
import cloud.nino.nino.repository.elasticsearch.PlaceSearchRepository;
import cloud.nino.nino.security.SecurityUtils;
import cloud.nino.nino.service.dto.StopDTO;
import cloud.nino.nino.service.dto.VisitDTO;
import cloud.nino.nino.service.dto.custom.*;
import cloud.nino.nino.service.mapper.StopMapper;
import cloud.nino.nino.service.mapper.VisitMapper;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.errors.CustomParameterizedException;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.graphhopper.directions.api.client.ApiException;
import com.graphhopper.directions.api.client.api.RoutingApi;
import com.graphhopper.directions.api.client.model.RouteResponse;
import com.graphhopper.directions.api.client.model.RouteResponsePath;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Visit.
 */
@Service
@Transactional
public class VisitCustomService {

    private final Logger log = LoggerFactory.getLogger(VisitCustomService.class);

    private final VisitCustomRepository visitRepository;
    private final PlaceSearchRepository placeSearchRepository;
    private final VisitMapper visitMapper;
    private final StopMapper stopMapper;
    private final RoutingApi routing;
    private final StopCustomRepository stopCustomRepository;
    private final UserRepository userRepository;
    private final GeoApiContext geoApiContext;
    private final EventAndLocationCustomRepository eventAndLocationCustomRepository;
    @Value("${graphhopper.key}")
    private String graphhopperKey;

    @Value("${google.maps.key}")
    private String googleMapsKey;

    public VisitCustomService(VisitCustomRepository visitRepository, PlaceSearchRepository placeSearchRepository, VisitMapper visitMapper, StopMapper stopMapper, StopCustomRepository stopCustomRepository, UserRepository userRepository, EventAndLocationCustomRepository eventAndLocationCustomRepository) {
        this.visitRepository = visitRepository;
        this.placeSearchRepository = placeSearchRepository;
        this.visitMapper = visitMapper;
        this.stopMapper = stopMapper;
        this.stopCustomRepository = stopCustomRepository;
        this.userRepository = userRepository;
        this.eventAndLocationCustomRepository = eventAndLocationCustomRepository;
        routing = new RoutingApi();
        geoApiContext = new GeoApiContext.Builder()
            .apiKey("AIzaSyCC3gd9rWXp1hJ9sPMMT_dftVuwaxzHQ48")
            .build();
    }


    /**
     * Save a visit.
     *
     * @param visitDTO the entity to save
     * @return the persisted entity
     */
    public VisitDTO save(VisitDTO visitDTO) {
        log.debug("Request to save Visit : {}", visitDTO);
        if (visitDTO.getId() != null) {
            visitDTO.setModifiedDate(ZonedDateTime.now());
        } else {
            User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new UsernameNotFoundException("Current user not found"))).get();
            visitDTO.setActive(true);
            visitDTO.setInProgress(false);
            visitDTO.setUserId(user.getId());
            visitDTO.setModifiedDate(ZonedDateTime.now());
            visitDTO.setCreatedDate(ZonedDateTime.now());
            /*TODO: Logica di test da cambiare*/
            eventAndLocationCustomRepository.findAll().stream().findFirst().map(eventAndLocation -> {
                visitDTO.setEventAndlocationId(eventAndLocation.getId());
                return eventAndLocation;
            });
        }
        Visit visit = visitMapper.toEntity(visitDTO);
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }

    /**
     * Get all the visits.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<VisitDTO> findAll() {
        log.debug("Request to get all Visits");
        return visitRepository.findAll().stream()
            .map(visitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one visit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VisitCustomDTO> findOneWithDetails(Long id, ZonedDateTime currentTime) {
        log.debug("Request to get Visit : {}", id);
        Optional<Visit> visitOptional = visitRepository.findById(id);
        if (!visitOptional.isPresent()) {
            return Optional.empty();
        }
        Visit visit = visitOptional.get();
        VisitDTO visitDTO = visitMapper.toDto(visit);
        VisitCustomDTO visitCustomDTO = new VisitCustomDTO(visitDTO, stopMapper.toDto(visit.getStartPoint()), stopMapper.toDto(visit.getEndPoint()));
        visitCustomDTO.setStops(visit.getStops().stream().map(stop -> {
            StopDTO stopDTO = stopMapper.toDto(stop);
            StopCustomDTO stopCustomDTO = new StopCustomDTO(stopDTO);
            Optional<Place> placeOptional = placeSearchRepository.findOneById(stopDTO.getPoiId());
            if (placeOptional.isPresent()) {
                stopCustomDTO.setLat(placeOptional.get().getLat());
                stopCustomDTO.setLon(placeOptional.get().getLon());
                stopCustomDTO.setTags(placeOptional.get().getTags());
            }
            return stopCustomDTO;
        }).collect(Collectors.toSet()));

        if (visit.getStartPoint() != null && visit.getEndPoint() != null && visit.getLastLat() != null && visit.getLastLon() != null) {
            try {
                visitCustomDTO.setRouteUrl("https://www.google.com/maps/dir");
                LinkedList<LatLng> points = new LinkedList<>();

                if (visit.isInProgress()) {
                    LatLng initialPoint = new LatLng(visit.getLastLat().doubleValue(), visit.getLastLon().doubleValue());
                    points.add(initialPoint);
                    visitCustomDTO.setRouteUrl(String.format("%s/%s", visitCustomDTO.getRouteUrl(), initialPoint));
                } else {
                    if (visit.getStartPoint().getStopType() == StopType.MY_POSITION) {
                        LatLng initialPoint = new LatLng(visit.getStartPoint().getLat().doubleValue(), visit.getStartPoint().getLon().doubleValue());
                        points.add(initialPoint);
                        visitCustomDTO.setRouteUrl(String.format("%s/%s", visitCustomDTO.getRouteUrl(), initialPoint));
                    } else {
                        Optional<Place> placeOptional = placeSearchRepository.findOneById(visit.getStartPoint().getPoiId());
                        LatLng initialPoint = new LatLng(placeOptional.get().getLat().doubleValue(), placeOptional.get().getLon().doubleValue());
                        points.add(initialPoint);
                        visitCustomDTO.setRouteUrl(String.format("%s/%s", visitCustomDTO.getRouteUrl(), initialPoint));
                    }

                }

                LinkedList<LatLng> stopsCoordinates = new LinkedList<>();
                visitCustomDTO.getStops().forEach(stopCustomDTO -> {
                    if (BooleanUtils.isNotTrue(stopCustomDTO.isReached())) {
                        LatLng position = new LatLng(stopCustomDTO.getLat().doubleValue(), stopCustomDTO.getLon().doubleValue());
                        stopsCoordinates.add(position);
                        visitCustomDTO.setRouteUrl(String.format("%s/%s", visitCustomDTO.getRouteUrl(), position));
                    }
                });
                if (BooleanUtils.isNotTrue(visit.getEndPoint().isReached())) {
                    if (visit.getEndPoint().getStopType() == StopType.MY_POSITION) {
                        LatLng position = new LatLng(visit.getEndPoint().getLat().doubleValue(), visit.getEndPoint().getLon().doubleValue());
                        stopsCoordinates.add(position);
                        visitCustomDTO.setRouteUrl(String.format("%s/%s", visitCustomDTO.getRouteUrl(), position));
                    } else {
                        Optional<Place> placeOptional = placeSearchRepository.findOneById(visit.getEndPoint().getPoiId());
                        LatLng position = new LatLng(placeOptional.get().getLat().doubleValue(), placeOptional.get().getLon().doubleValue());
                        stopsCoordinates.add(position);
                        visitCustomDTO.setRouteUrl(String.format("%s/%s", visitCustomDTO.getRouteUrl(), position));
                    }

                }
                points.addAll(stopsCoordinates);

                if (points.size() > 1) {
                    DirectionsResult directionsResult = googleDirectionsRequest(points);
                    RouteResponsePath routeResponsePath = null;
                    visitCustomDTO.setDirectionsResult(directionsResult);
                    List<StopCustomDTO> notReachedStops = visitCustomDTO.getStops().stream().filter(stopCustomDTO -> BooleanUtils.isNotTrue(stopCustomDTO.isReached())).collect(Collectors.toList());
                    if (!notReachedStops.isEmpty()) {
                        visitCustomDTO.setNextStop(notReachedStops.get(0));
                    }/*
                    LinkedList<String> nextStopPoints = new LinkedList<>();
                    nextStopPoints.add(points.getFirst());
                    nextStopPoints.add(points.get(1));
                    RouteResponse nextStopRouteResponse = graphhopperRequest(nextStopPoints);
                    if (!nextStopRouteResponse.getPaths().isEmpty()) {
                        visitCustomDTO.setNextStopTime(nextStopRouteResponse.getPaths().get(0).getTime());
                    }*/
                    if (routeResponsePath != null) {
                        currentTime = currentTime != null ? currentTime : ZonedDateTime.now();
                        ZonedDateTime programmedEndTime = visitCustomDTO.getStartTime().plusSeconds(visitCustomDTO.getMaxVisitTime() / 1000);
                        ZonedDateTime actualEndTime = currentTime.plusSeconds(routeResponsePath.getTime() / 1000);
                        long minutes = programmedEndTime.until(actualEndTime, ChronoUnit.MINUTES);
                        if (minutes >= 0) {
                            visitCustomDTO.setTimeMessage(String.format("Per completare la visita avrai bisogno di almeno %d minuti in più", minutes));
                        } else {
                            visitCustomDTO.setTimeMessage(String.format("Puoi completare la visita effettuando una sosta di circa %d minuti per ogni tappa", minutes * -1 / notReachedStops.size()));
                        }
                    }
                } else {
                    visitCustomDTO.setTimeMessage("Visita completata");
                }


            } catch (InterruptedException e) {
                log.error("", e);
                e.printStackTrace();
            } catch (IOException e) {
                log.error("", e);
            } catch (com.google.maps.errors.ApiException e) {
                log.error("", e);
            }

        }

        return Optional.of(visitCustomDTO);

    }

    private RouteResponse graphhopperRequest(LinkedList<String> points) throws ApiException {
        return routing.routeGet(points,
            false, graphhopperKey, "it", true,
            "foot", true, true, Arrays.asList(),
            false, "fastest", null, null, null,
            null, null, Arrays.asList(), null,
            null, null, null, null, null);
    }

    private DirectionsResult googleDirectionsRequest(LinkedList<LatLng> points) throws InterruptedException, com.google.maps.errors.ApiException, IOException {
        List<LatLng> waypoints = new ArrayList<>();
        LatLng[] latLngs = new LatLng[0];
        if (points.size() > 2) {
            for (int i = 1; i < points.size() - 1; i++) {
                waypoints.add(points.get(i));
            }
            latLngs = new LatLng[waypoints.size()];
            waypoints.toArray(latLngs);
        }

        return DirectionsApi.newRequest(geoApiContext)
            .origin(points.get(0))
            .destination(points.getLast())
            .waypoints(latLngs)
            .optimizeWaypoints(true)
            .mode(TravelMode.WALKING).await();
    }

    /**
     * Delete the visit by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);
        visitRepository.deleteById(id);
    }

    public Optional<VisitCustomDTO> start(@Valid StartVisitInput startVisitInput) {
        Optional<Visit> visit = visitRepository.findById(startVisitInput.getId());
        if (!visit.isPresent()) {
            return Optional.empty();
        }
        visit.get().setInProgress(true);
        visit.get().setStartTime(startVisitInput.getCurrentTime() != null ? startVisitInput.getCurrentTime() : ZonedDateTime.now());
        if (startVisitInput.getLat() != null) {
            visit.get().setLastLat(startVisitInput.getLat());
        }
        if (startVisitInput.getLon() != null) {
            visit.get().setLastLon(startVisitInput.getLon());
        }
        visitRepository.save(visit.get());
        if (BooleanUtils.isTrue(startVisitInput.getReset())) {
            visit.get().getStops().forEach(stop -> {
                if (BooleanUtils.isTrue(stop.isReached())) {
                    stop.reached(false);
                    stopCustomRepository.save(stop);
                }
            });
        }
        return findOneWithDetails(visit.get().getId(), startVisitInput.getCurrentTime());
    }

    public Optional<VisitCustomDTO> continueVisit(@Valid StartVisitInput startVisitInput) {
        Optional<Visit> visit = visitRepository.findById(startVisitInput.getId());
        if (!visit.isPresent()) {
            return Optional.empty();
        }
        visit.get().setLastLat(startVisitInput.getLat());
        visit.get().setLastLon(startVisitInput.getLon());
        visitRepository.save(visit.get());
        return findOneWithDetails(visit.get().getId(), startVisitInput.getCurrentTime());
    }

    /**
     * Get one visit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VisitDTO> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id)
            .map(visitMapper::toDto);
    }

    public Optional<StopDTO> stopReached(@Valid StopReached stopReached) {
        Optional<Stop> stop = stopCustomRepository.findById(stopReached.getId());
        if (!stop.isPresent()) {
            return Optional.empty();
        }
        stop.get().reached(true);
        stopCustomRepository.save(stop.get());
        Optional<Place> placeOptional = placeSearchRepository.findOneById(stop.get().getPoiId());
        if (!placeOptional.isPresent()) {
            throw new BadRequestAlertException("Invalid poi id", "poi", "idnull");
        }
        stop.get().getVisit().setLastLat(placeOptional.get().getLat());
        stop.get().getVisit().setLastLon(placeOptional.get().getLon());
        visitRepository.save(stop.get().getVisit());
        return stopCustomRepository.findById(stopReached.getId()).map(stopMapper::toDto);
    }

    public Optional<VisitCustomDTO> findOneWithDetailsByUserName(String username) {
        User user = this.userRepository.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found!"));
        List<Visit> visits = visitRepository.findByUserAndActiveOrderByModifiedDateDesc(user, true);
        if (visits.isEmpty()) {
            Map map = new HashMap();
            map.put("visitNotCreated", true);
            throw new CustomParameterizedException("Visit not yet created", map);
        }
        return findOneWithDetails(visits.get(0).getId(), null);
    }

    public VisitDTO update(@Valid VisitDTO visitDTO) {
        Visit visit = this.visitRepository.findById(visitDTO.getId()).get();
        visit.setLastLat(visitDTO.getLastLat());
        visit.setLastLon(visitDTO.getLastLon());
        return visitMapper.toDto(this.visitRepository.save(visit));
    }

    public Optional<StopDTO> startPoint(@Valid StartEndPoint startEndPoint, boolean isStart) {
        log.debug("Request to save start point : {}", startEndPoint);

        if (startEndPoint.getVisitId() == null) {
            throw new BadRequestAlertException("Invalid visit id", "poi", "idnull");
        }

        if (!StringUtils.isBlank(startEndPoint.getPoiId())) {
            Optional<Place> placeOptional = placeSearchRepository.findOneById(startEndPoint.getPoiId());
            if (!placeOptional.isPresent()) {
                throw new BadRequestAlertException("Invalid poi id", "poi", "idnull");
            }
        }
        if (StringUtils.isBlank(startEndPoint.getPoiId()) && (startEndPoint.getLat() == null || startEndPoint.getLon() == null)) {
            throw new BadRequestAlertException("Invalid poi id and invalid coordinates", "poi", "idnull");
        }
        Optional<Visit> visitOptional = this.visitRepository.findById(startEndPoint.getVisitId());
        if (!visitOptional.isPresent()) {
            throw new BadRequestAlertException("Invalid visit id", "poi", "idnull");
        }

        Stop stop = new Stop();
        stop.setStopType(startEndPoint.getLat() != null && startEndPoint.getLon() != null ? StopType.MY_POSITION : StopType.REGULAR);
        stop.setPoiId(startEndPoint.getPoiId());
        stop.setReached(false);
        stop.setLat(startEndPoint.getLat());
        stop.setLon(startEndPoint.getLon());
        stop = stopCustomRepository.save(stop);
        if (isStart) {
            visitOptional.get().setStartPoint(stop);
            visitRepository.save(visitOptional.get());
        } else {
            visitOptional.get().setEndPoint(stop);
            visitRepository.save(visitOptional.get());
        }

        return Optional.ofNullable(stopMapper.toDto(stop));
    }

    public void deleteStartPoint(Long id) {
        Optional<Visit> visitOptional = this.visitRepository.findById(id);
        Stop stop = visitOptional.get().getStartPoint();
        stopCustomRepository.delete(stop);
        visitOptional.get().setStartPoint(null);
        this.visitRepository.save(visitOptional.get());
    }

    public void deleteStopPoint(Long id) {
        Optional<Visit> visitOptional = this.visitRepository.findById(id);
        Stop stop = visitOptional.get().getEndPoint();
        stopCustomRepository.delete(stop);
        visitOptional.get().setEndPoint(null);
        this.visitRepository.save(visitOptional.get());
    }

    public List<VisitDTO> findAllByEventAndlocationId(Long eventAndlocationId) {
        log.debug("Request to get all Visits");
        return visitRepository.findAllByEventAndlocation_Id(eventAndlocationId).stream()
            .map(visitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
