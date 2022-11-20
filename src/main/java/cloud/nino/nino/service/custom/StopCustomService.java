package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.Stop;
import cloud.nino.nino.domain.Visit;
import cloud.nino.nino.domain.elasticsearch.Place;
import cloud.nino.nino.repository.StopRepository;
import cloud.nino.nino.repository.custom.StopCustomRepository;
import cloud.nino.nino.repository.custom.VisitCustomRepository;
import cloud.nino.nino.repository.elasticsearch.PlaceSearchRepository;
import cloud.nino.nino.service.dto.StopDTO;
import cloud.nino.nino.service.mapper.StopMapper;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Stop.
 */
@Service
@Transactional
public class StopCustomService {

    private final Logger log = LoggerFactory.getLogger(StopCustomService.class);

    private final StopRepository stopRepository;
    private final StopCustomRepository stopCustomRepository;
    private final VisitCustomRepository visitCustomRepository;
    private final StopMapper stopMapper;
    private final PlaceSearchRepository placeSearchRepository;

    public StopCustomService(StopRepository stopRepository, StopCustomRepository stopCustomRepository, VisitCustomRepository visitCustomRepository, StopMapper stopMapper, PlaceSearchRepository placeSearchRepository) {
        this.stopRepository = stopRepository;
        this.stopCustomRepository = stopCustomRepository;
        this.visitCustomRepository = visitCustomRepository;
        this.stopMapper = stopMapper;
        this.placeSearchRepository = placeSearchRepository;
    }

    /**
     * Save a stop.
     *
     * @param stopDTO the entity to save
     * @return the persisted entity
     */
    public StopDTO save(StopDTO stopDTO) {
        log.debug("Request to save Stop : {}", stopDTO);
        if (StringUtils.isBlank(stopDTO.getPoiId())) {
            throw new BadRequestAlertException("Invalid poi id", "poi", "idnull");
        }
        Optional<Place> placeOptional = placeSearchRepository.findOneById(stopDTO.getPoiId());
        if (!placeOptional.isPresent()) {
            throw new BadRequestAlertException("Invalid poi id", "poi", "idnull");
        }
        if (stopDTO.getVisitId() == null) {
            throw new BadRequestAlertException("Invalid visit id", "poi", "idnull");
        }
        Optional<Visit> visitOptional = this.visitCustomRepository.findById(stopDTO.getVisitId());
        if (!visitOptional.isPresent()) {
            throw new BadRequestAlertException("Invalid visit id", "poi", "idnull");
        }

        Stop stop = stopMapper.toEntity(stopDTO);
        if (stop.getId() == null) {
            Stop existingStio = null;
            for (Stop visitStop : visitOptional.get().getStops()) {
                if (StringUtils.equalsIgnoreCase(visitStop.getPoiId(), stop.getPoiId())) {
                    existingStio = visitStop;
                }
            }
            if (existingStio == null) {
                stop = stopRepository.save(stop);
            } else {
                stop = existingStio;
            }
        } else {
            stop = stopRepository.save(stop);

        }
        return stopMapper.toDto(stop);
    }

    /**
     * Get all the stops.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StopDTO> findAll() {
        log.debug("Request to get all Stops");
        return stopRepository.findAll().stream()
            .map(stopMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<StopDTO> findOne(Long id) {
        log.debug("Request to get Stop : {}", id);
        return stopRepository.findById(id)
            .map(stopMapper::toDto);
    }

    /**
     * Delete the stop by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Stop : {}", id);
        stopRepository.deleteById(id);
    }

    public Collection<StopDTO> findByVisitId(Long visitId) {
        return stopCustomRepository.findByVisit_Id(visitId).stream().map(stopMapper::toDto).collect(Collectors.toSet());
    }
}
