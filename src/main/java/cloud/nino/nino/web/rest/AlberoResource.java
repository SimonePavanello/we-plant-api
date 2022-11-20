package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.AlberoService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.AlberoDTO;
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
 * REST controller for managing Albero.
 */
@RestController
@RequestMapping("/api")
public class AlberoResource {

    private final Logger log = LoggerFactory.getLogger(AlberoResource.class);

    private static final String ENTITY_NAME = "albero";

    private final AlberoService alberoService;

    public AlberoResource(AlberoService alberoService) {
        this.alberoService = alberoService;
    }

    /**
     * POST  /alberos : Create a new albero.
     *
     * @param alberoDTO the alberoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alberoDTO, or with status 400 (Bad Request) if the albero has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alberos")
    @Timed
    public ResponseEntity<AlberoDTO> createAlbero(@RequestBody AlberoDTO alberoDTO) throws URISyntaxException {
        log.debug("REST request to save Albero : {}", alberoDTO);
        if (alberoDTO.getId() != null) {
            throw new BadRequestAlertException("A new albero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlberoDTO result = alberoService.save(alberoDTO);
        return ResponseEntity.created(new URI("/api/alberos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alberos : Updates an existing albero.
     *
     * @param alberoDTO the alberoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alberoDTO,
     * or with status 400 (Bad Request) if the alberoDTO is not valid,
     * or with status 500 (Internal Server Error) if the alberoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alberos")
    @Timed
    public ResponseEntity<AlberoDTO> updateAlbero(@RequestBody AlberoDTO alberoDTO) throws URISyntaxException {
        log.debug("REST request to update Albero : {}", alberoDTO);
        if (alberoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlberoDTO result = alberoService.save(alberoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alberoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alberos : get all the alberos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alberos in body
     */
    @GetMapping("/alberos")
    @Timed
    public List<AlberoDTO> getAllAlberos() {
        log.debug("REST request to get all Alberos");
        return alberoService.findAll();
    }

    /**
     * GET  /alberos/:id : get the "id" albero.
     *
     * @param id the id of the alberoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alberoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alberos/{id}")
    @Timed
    public ResponseEntity<AlberoDTO> getAlbero(@PathVariable Long id) {
        log.debug("REST request to get Albero : {}", id);
        Optional<AlberoDTO> alberoDTO = alberoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alberoDTO);
    }

    /**
     * DELETE  /alberos/:id : delete the "id" albero.
     *
     * @param id the id of the alberoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alberos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlbero(@PathVariable Long id) {
        log.debug("REST request to delete Albero : {}", id);
        alberoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
