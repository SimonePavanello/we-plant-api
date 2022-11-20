package cloud.nino.nino.web.rest.custom;

import cloud.nino.nino.service.custom.VisitCustomService;
import cloud.nino.nino.service.dto.StopDTO;
import cloud.nino.nino.service.dto.VisitDTO;
import cloud.nino.nino.service.dto.custom.StartEndPoint;
import cloud.nino.nino.service.dto.custom.StartVisitInput;
import cloud.nino.nino.service.dto.custom.StopReached;
import cloud.nino.nino.service.dto.custom.VisitCustomDTO;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Visit.
 */
@RestController
@RequestMapping("/api/custom")
public class VisitCustomResource {

    private final Logger log = LoggerFactory.getLogger(VisitCustomResource.class);

    private static final String ENTITY_NAME = "visit";

    private final VisitCustomService visitService;

    public VisitCustomResource(VisitCustomService visitService) {
        this.visitService = visitService;
    }

    /**
     * POST  /visits : Create a new visit.
     *
     * @param visitDTO the visitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitDTO, or with status 400 (Bad Request) if the visit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visits")
    @Timed
    public ResponseEntity<VisitDTO> createVisit(@Valid @RequestBody VisitDTO visitDTO) throws URISyntaxException {
        log.debug("REST request to save Visit : {}", visitDTO);
        if (visitDTO.getId() != null) {
            throw new BadRequestAlertException("A new visit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VisitDTO result = visitService.save(visitDTO);
        return ResponseEntity.created(new URI("/api/visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/visits/start-point")
    @Timed
    public ResponseEntity<StopDTO> startPoint(@Valid @RequestBody StartEndPoint startEndPoint) throws URISyntaxException {
        log.debug("REST request to save start point : {}", startEndPoint);

        Optional<StopDTO> result = visitService.startPoint(startEndPoint, true);
        return ResponseUtil.wrapOrNotFound(result);
    }
    @PostMapping("/visits/end-point")
    @Timed
    public ResponseEntity<StopDTO> endPoint(@Valid @RequestBody StartEndPoint startEndPoint) throws URISyntaxException {
        log.debug("REST request to save end point : {}", startEndPoint);

        Optional<StopDTO> result = visitService.startPoint(startEndPoint, false);
        return ResponseUtil.wrapOrNotFound(result);
    }


    @PostMapping("/visits/start")
    @Timed
    public ResponseEntity<VisitCustomDTO> start(@Valid @RequestBody StartVisitInput startVisitInput) throws URISyntaxException {
        log.debug("REST request to save start Visit : {}", startVisitInput);

        Optional<VisitCustomDTO> result = visitService.start(startVisitInput);
        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * PUT  /visits : Updates an existing visit.
     *
     * @param visitDTO the visitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitDTO,
     * or with status 400 (Bad Request) if the visitDTO is not valid,
     * or with status 500 (Internal Server Error) if the visitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visits")
    @Timed
    public ResponseEntity<VisitDTO> updateVisit(@Valid @RequestBody VisitDTO visitDTO) throws URISyntaxException {
        log.debug("REST request to update Visit : {}", visitDTO);
        if (visitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VisitDTO result = visitService.save(visitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/visits/current-position")
    @Timed
    public ResponseEntity<VisitDTO> updateVisitCurrentPosition(@Valid @RequestBody VisitDTO visitDTO) throws URISyntaxException {
        log.debug("REST request to update Visit : {}", visitDTO);
        if (visitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VisitDTO result = visitService.update(visitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visits : get all the visits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of visits in body
     */
    @GetMapping("/visits")
    @Timed
    public List<VisitDTO> getAllVisits() {
        log.debug("REST request to get all Visits");
        return visitService.findAll();
    }

    @PostMapping("/visits/continue")
    @Timed
    public ResponseEntity<VisitCustomDTO> continueVisit(@Valid @RequestBody StartVisitInput startVisitInput) {
        log.debug("REST request to continue Visit : {}", startVisitInput);
        Optional<VisitCustomDTO> visitDTO = visitService.continueVisit(startVisitInput);
        return ResponseUtil.wrapOrNotFound(visitDTO);

    }

    @PostMapping("/visits/stop/reached")
    @Timed
    public ResponseEntity<StopDTO> stopReached(@Valid @RequestBody StopReached stopReached) {
        log.debug("REST request to continue Visit : {}", stopReached);
        Optional<StopDTO> visitDTO = visitService.stopReached(stopReached);
        return ResponseUtil.wrapOrNotFound(visitDTO);

    }

    /**
     * GET  /visits/:id : get the "id" visit.
     *
     * @param id the id of the visitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/visits/details/{id}/{currentTime}")
    @Timed
    public ResponseEntity<VisitCustomDTO> getVisitWithDetailsTest(@PathVariable Long id, @PathVariable ZonedDateTime currentTime) {
        log.debug("REST request to get Visit : {}", id);
        Optional<VisitCustomDTO> visitDTO = visitService.findOneWithDetails(id, currentTime);
        return ResponseUtil.wrapOrNotFound(visitDTO);
    }


    /**
     * GET  /visits/:id : get the "id" visit.
     *
     * @param id the id of the visitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/visits/details/{id}")
    @Timed
    public ResponseEntity<VisitCustomDTO> getVisitWithDetails(@PathVariable Long id) {
        log.debug("REST request to get Visit : {}", id);
        Optional<VisitCustomDTO> visitDTO = visitService.findOneWithDetails(id, null);
        return ResponseUtil.wrapOrNotFound(visitDTO);
    }

    @GetMapping("/visits/details/by-user/{username}")
    @Timed
    public ResponseEntity<VisitCustomDTO> getVisitWithDetails(@PathVariable String username) {
        log.debug("REST request to get Visit by username: {}", username);
        Optional<VisitCustomDTO> visitDTO = visitService.findOneWithDetailsByUserName(username);
        return ResponseUtil.wrapOrNotFound(visitDTO);
    }


    /**
     * GET  /visits/event-and-location:eventAndLocationId : get the "id" visit.
     *
     * @param eventAndLocationId the id of the event and location
     * @return the ResponseEntity with status 200 (OK) and the list of visits in body
     */
    @GetMapping("/visits/event-and-location/{eventAndLocationId}")
    @Timed
    public List<VisitDTO> getAllVisits(@PathVariable Long eventAndLocationId) {
        log.debug("REST request to get Visit by event and location : {}", eventAndLocationId);
        return visitService.findAllByEventAndlocationId(eventAndLocationId);
    }

    /**
     * GET  /visits/:id : get the "id" visit.
     *
     * @param id the id of the visitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/visits/{id}")
    @Timed
    public ResponseEntity<VisitDTO> getVisit(@PathVariable Long id) {
        log.debug("REST request to get Visit : {}", id);
        Optional<VisitDTO> visitDTO = visitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visitDTO);
    }

    /**
     * DELETE  /visits/:id : delete the "id" visit.
     *
     * @param id the id of the visitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visits/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        log.debug("REST request to delete Visit : {}", id);
        visitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @DeleteMapping("/visits/start-point/{id}")
    @Timed
    public ResponseEntity<Void> deleteStartPoint(@PathVariable Long id) {
        log.debug("REST request to delete Visit : {}", id);
        visitService.deleteStartPoint(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @DeleteMapping("/visits/end-point/{id}")
    @Timed
    public ResponseEntity<Void> deleteStopPoint(@PathVariable Long id) {
        log.debug("REST request to delete Visit : {}", id);
        visitService.deleteStopPoint(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
