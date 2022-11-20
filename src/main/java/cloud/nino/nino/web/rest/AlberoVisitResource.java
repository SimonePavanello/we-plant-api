package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.AlberoVisitService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.AlberoVisitDTO;
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
 * REST controller for managing AlberoVisit.
 */
@RestController
@RequestMapping("/api")
public class AlberoVisitResource {

    private final Logger log = LoggerFactory.getLogger(AlberoVisitResource.class);

    private static final String ENTITY_NAME = "alberoVisit";

    private final AlberoVisitService alberoVisitService;

    public AlberoVisitResource(AlberoVisitService alberoVisitService) {
        this.alberoVisitService = alberoVisitService;
    }

    /**
     * POST  /albero-visits : Create a new alberoVisit.
     *
     * @param alberoVisitDTO the alberoVisitDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alberoVisitDTO, or with status 400 (Bad Request) if the alberoVisit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/albero-visits")
    @Timed
    public ResponseEntity<AlberoVisitDTO> createAlberoVisit(@RequestBody AlberoVisitDTO alberoVisitDTO) throws URISyntaxException {
        log.debug("REST request to save AlberoVisit : {}", alberoVisitDTO);
        if (alberoVisitDTO.getId() != null) {
            throw new BadRequestAlertException("A new alberoVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlberoVisitDTO result = alberoVisitService.save(alberoVisitDTO);
        return ResponseEntity.created(new URI("/api/albero-visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /albero-visits : Updates an existing alberoVisit.
     *
     * @param alberoVisitDTO the alberoVisitDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alberoVisitDTO,
     * or with status 400 (Bad Request) if the alberoVisitDTO is not valid,
     * or with status 500 (Internal Server Error) if the alberoVisitDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/albero-visits")
    @Timed
    public ResponseEntity<AlberoVisitDTO> updateAlberoVisit(@RequestBody AlberoVisitDTO alberoVisitDTO) throws URISyntaxException {
        log.debug("REST request to update AlberoVisit : {}", alberoVisitDTO);
        if (alberoVisitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlberoVisitDTO result = alberoVisitService.save(alberoVisitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alberoVisitDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /albero-visits : get all the alberoVisits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alberoVisits in body
     */
    @GetMapping("/albero-visits")
    @Timed
    public List<AlberoVisitDTO> getAllAlberoVisits() {
        log.debug("REST request to get all AlberoVisits");
        return alberoVisitService.findAll();
    }

    /**
     * GET  /albero-visits/:id : get the "id" alberoVisit.
     *
     * @param id the id of the alberoVisitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alberoVisitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/albero-visits/{id}")
    @Timed
    public ResponseEntity<AlberoVisitDTO> getAlberoVisit(@PathVariable Long id) {
        log.debug("REST request to get AlberoVisit : {}", id);
        Optional<AlberoVisitDTO> alberoVisitDTO = alberoVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alberoVisitDTO);
    }

    /**
     * DELETE  /albero-visits/:id : delete the "id" alberoVisit.
     *
     * @param id the id of the alberoVisitDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/albero-visits/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlberoVisit(@PathVariable Long id) {
        log.debug("REST request to delete AlberoVisit : {}", id);
        alberoVisitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
