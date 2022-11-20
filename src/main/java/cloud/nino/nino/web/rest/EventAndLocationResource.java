package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.EventAndLocationService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.EventAndLocationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EventAndLocation.
 */
@RestController
@RequestMapping("/api")
public class EventAndLocationResource {

    private final Logger log = LoggerFactory.getLogger(EventAndLocationResource.class);

    private static final String ENTITY_NAME = "eventAndLocation";

    private final EventAndLocationService eventAndLocationService;

    public EventAndLocationResource(EventAndLocationService eventAndLocationService) {
        this.eventAndLocationService = eventAndLocationService;
    }

    /**
     * POST  /event-and-locations : Create a new eventAndLocation.
     *
     * @param eventAndLocationDTO the eventAndLocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventAndLocationDTO, or with status 400 (Bad Request) if the eventAndLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-and-locations")
    @Timed
    public ResponseEntity<EventAndLocationDTO> createEventAndLocation(@RequestBody EventAndLocationDTO eventAndLocationDTO) throws URISyntaxException {
        log.debug("REST request to save EventAndLocation : {}", eventAndLocationDTO);
        if (eventAndLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventAndLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventAndLocationDTO result = eventAndLocationService.save(eventAndLocationDTO);
        return ResponseEntity.created(new URI("/api/event-and-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-and-locations : Updates an existing eventAndLocation.
     *
     * @param eventAndLocationDTO the eventAndLocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventAndLocationDTO,
     * or with status 400 (Bad Request) if the eventAndLocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventAndLocationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-and-locations")
    @Timed
    public ResponseEntity<EventAndLocationDTO> updateEventAndLocation(@RequestBody EventAndLocationDTO eventAndLocationDTO) throws URISyntaxException {
        log.debug("REST request to update EventAndLocation : {}", eventAndLocationDTO);
        if (eventAndLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventAndLocationDTO result = eventAndLocationService.save(eventAndLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventAndLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-and-locations : get all the eventAndLocations.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of eventAndLocations in body
     */
    @GetMapping("/event-and-locations")
    @Timed
    public List<EventAndLocationDTO> getAllEventAndLocations(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all EventAndLocations");
        return eventAndLocationService.findAll();
    }

    /**
     * GET  /event-and-locations/:id : get the "id" eventAndLocation.
     *
     * @param id the id of the eventAndLocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventAndLocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/event-and-locations/{id}")
    @Timed
    public ResponseEntity<EventAndLocationDTO> getEventAndLocation(@PathVariable Long id) {
        log.debug("REST request to get EventAndLocation : {}", id);
        Optional<EventAndLocationDTO> eventAndLocationDTO = eventAndLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventAndLocationDTO);
    }

    /**
     * DELETE  /event-and-locations/:id : delete the "id" eventAndLocation.
     *
     * @param id the id of the eventAndLocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-and-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventAndLocation(@PathVariable Long id) {
        log.debug("REST request to delete EventAndLocation : {}", id);
        eventAndLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
