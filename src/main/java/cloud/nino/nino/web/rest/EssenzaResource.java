package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.EssenzaService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.EssenzaDTO;
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
 * REST controller for managing Essenza.
 */
@RestController
@RequestMapping("/api")
public class EssenzaResource {

    private final Logger log = LoggerFactory.getLogger(EssenzaResource.class);

    private static final String ENTITY_NAME = "essenza";

    private final EssenzaService essenzaService;

    public EssenzaResource(EssenzaService essenzaService) {
        this.essenzaService = essenzaService;
    }

    /**
     * POST  /essenzas : Create a new essenza.
     *
     * @param essenzaDTO the essenzaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new essenzaDTO, or with status 400 (Bad Request) if the essenza has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/essenzas")
    @Timed
    public ResponseEntity<EssenzaDTO> createEssenza(@Valid @RequestBody EssenzaDTO essenzaDTO) throws URISyntaxException {
        log.debug("REST request to save Essenza : {}", essenzaDTO);
        if (essenzaDTO.getId() != null) {
            throw new BadRequestAlertException("A new essenza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EssenzaDTO result = essenzaService.save(essenzaDTO);
        return ResponseEntity.created(new URI("/api/essenzas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /essenzas : Updates an existing essenza.
     *
     * @param essenzaDTO the essenzaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated essenzaDTO,
     * or with status 400 (Bad Request) if the essenzaDTO is not valid,
     * or with status 500 (Internal Server Error) if the essenzaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/essenzas")
    @Timed
    public ResponseEntity<EssenzaDTO> updateEssenza(@Valid @RequestBody EssenzaDTO essenzaDTO) throws URISyntaxException {
        log.debug("REST request to update Essenza : {}", essenzaDTO);
        if (essenzaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EssenzaDTO result = essenzaService.save(essenzaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, essenzaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /essenzas : get all the essenzas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of essenzas in body
     */
    @GetMapping("/essenzas")
    @Timed
    public List<EssenzaDTO> getAllEssenzas() {
        log.debug("REST request to get all Essenzas");
        return essenzaService.findAll();
    }

    /**
     * GET  /essenzas/:id : get the "id" essenza.
     *
     * @param id the id of the essenzaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the essenzaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/essenzas/{id}")
    @Timed
    public ResponseEntity<EssenzaDTO> getEssenza(@PathVariable Long id) {
        log.debug("REST request to get Essenza : {}", id);
        Optional<EssenzaDTO> essenzaDTO = essenzaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(essenzaDTO);
    }

    /**
     * DELETE  /essenzas/:id : delete the "id" essenza.
     *
     * @param id the id of the essenzaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/essenzas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEssenza(@PathVariable Long id) {
        log.debug("REST request to delete Essenza : {}", id);
        essenzaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
