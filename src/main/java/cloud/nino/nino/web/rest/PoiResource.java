package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.PoiService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.PoiDTO;
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
 * REST controller for managing Poi.
 */
@RestController
@RequestMapping("/api")
public class PoiResource {

    private final Logger log = LoggerFactory.getLogger(PoiResource.class);

    private static final String ENTITY_NAME = "poi";

    private final PoiService poiService;

    public PoiResource(PoiService poiService) {
        this.poiService = poiService;
    }

    /**
     * POST  /pois : Create a new poi.
     *
     * @param poiDTO the poiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poiDTO, or with status 400 (Bad Request) if the poi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pois")
    @Timed
    public ResponseEntity<PoiDTO> createPoi(@RequestBody PoiDTO poiDTO) throws URISyntaxException {
        log.debug("REST request to save Poi : {}", poiDTO);
        if (poiDTO.getId() != null) {
            throw new BadRequestAlertException("A new poi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoiDTO result = poiService.save(poiDTO);
        return ResponseEntity.created(new URI("/api/pois/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pois : Updates an existing poi.
     *
     * @param poiDTO the poiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poiDTO,
     * or with status 400 (Bad Request) if the poiDTO is not valid,
     * or with status 500 (Internal Server Error) if the poiDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pois")
    @Timed
    public ResponseEntity<PoiDTO> updatePoi(@RequestBody PoiDTO poiDTO) throws URISyntaxException {
        log.debug("REST request to update Poi : {}", poiDTO);
        if (poiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoiDTO result = poiService.save(poiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poiDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pois : get all the pois.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pois in body
     */
    @GetMapping("/pois")
    @Timed
    public List<PoiDTO> getAllPois() {
        log.debug("REST request to get all Pois");
        return poiService.findAll();
    }

    /**
     * GET  /pois/:id : get the "id" poi.
     *
     * @param id the id of the poiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pois/{id}")
    @Timed
    public ResponseEntity<PoiDTO> getPoi(@PathVariable Long id) {
        log.debug("REST request to get Poi : {}", id);
        Optional<PoiDTO> poiDTO = poiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poiDTO);
    }

    /**
     * DELETE  /pois/:id : delete the "id" poi.
     *
     * @param id the id of the poiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pois/{id}")
    @Timed
    public ResponseEntity<Void> deletePoi(@PathVariable Long id) {
        log.debug("REST request to delete Poi : {}", id);
        poiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
