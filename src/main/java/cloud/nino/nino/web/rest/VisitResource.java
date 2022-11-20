package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.VisitService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.VisitDTO;
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
 * REST controller for managing Visit.
 */
@RestController
@RequestMapping("/api")
public class VisitResource {

    private final Logger log = LoggerFactory.getLogger(VisitResource.class);

    private static final String ENTITY_NAME = "visit";

    private final VisitService visitService;

    public VisitResource(VisitService visitService) {
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
    public ResponseEntity<VisitDTO> createVisit(@RequestBody VisitDTO visitDTO) throws URISyntaxException {
        log.debug("REST request to save Visit : {}", visitDTO);
        if (visitDTO.getId() != null) {
            throw new BadRequestAlertException("A new visit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VisitDTO result = visitService.save(visitDTO);
        return ResponseEntity.created(new URI("/api/visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody VisitDTO visitDTO) throws URISyntaxException {
        log.debug("REST request to update Visit : {}", visitDTO);
        if (visitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VisitDTO result = visitService.save(visitDTO);
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
}
