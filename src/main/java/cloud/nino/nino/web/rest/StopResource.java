package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.StopService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.StopDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Stop.
 */
@RestController
@RequestMapping("/api")
public class StopResource {

    private final Logger log = LoggerFactory.getLogger(StopResource.class);

    private static final String ENTITY_NAME = "stop";

    private final StopService stopService;

    public StopResource(StopService stopService) {
        this.stopService = stopService;
    }

    /**
     * POST  /stops : Create a new stop.
     *
     * @param stopDTO the stopDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stopDTO, or with status 400 (Bad Request) if the stop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stops")
    @Timed
    public ResponseEntity<StopDTO> createStop(@Valid @RequestBody StopDTO stopDTO) throws URISyntaxException {
        log.debug("REST request to save Stop : {}", stopDTO);
        if (stopDTO.getId() != null) {
            throw new BadRequestAlertException("A new stop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StopDTO result = stopService.save(stopDTO);
        return ResponseEntity.created(new URI("/api/stops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stops : Updates an existing stop.
     *
     * @param stopDTO the stopDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stopDTO,
     * or with status 400 (Bad Request) if the stopDTO is not valid,
     * or with status 500 (Internal Server Error) if the stopDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stops")
    @Timed
    public ResponseEntity<StopDTO> updateStop(@Valid @RequestBody StopDTO stopDTO) throws URISyntaxException {
        log.debug("REST request to update Stop : {}", stopDTO);
        if (stopDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StopDTO result = stopService.save(stopDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stopDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stops : get all the stops.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stops in body
     */
    @GetMapping("/stops")
    @Timed
    public List<StopDTO> getAllStops() {
        log.debug("REST request to get all Stops");
        return stopService.findAll();
    }

    /**
     * GET  /stops/:id : get the "id" stop.
     *
     * @param id the id of the stopDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stopDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stops/{id}")
    @Timed
    public ResponseEntity<StopDTO> getStop(@PathVariable Long id) {
        log.debug("REST request to get Stop : {}", id);
        Optional<StopDTO> stopDTO = stopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stopDTO);
    }

    /**
     * DELETE  /stops/:id : delete the "id" stop.
     *
     * @param id the id of the stopDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stops/{id}")
    @Timed
    public ResponseEntity<Void> deleteStop(@PathVariable Long id) {
        log.debug("REST request to delete Stop : {}", id);
        stopService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
