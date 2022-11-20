package cloud.nino.nino.web.rest.custom;

import cloud.nino.nino.service.custom.EssenzaAuditCustomService;
import cloud.nino.nino.service.dto.EssenzaAuditDTO;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EssenzaAudit.
 */
@RestController
@RequestMapping("/api/custom")
public class EssenzaAuditCustomResource {

    private final Logger log = LoggerFactory.getLogger(EssenzaAuditCustomResource.class);

    private static final String ENTITY_NAME = "essenzaAudit";

    private final EssenzaAuditCustomService essenzaAuditCustomService;

    public EssenzaAuditCustomResource(EssenzaAuditCustomService essenzaAuditCustomService) {
        this.essenzaAuditCustomService = essenzaAuditCustomService;
    }

    /**
     * POST  /essenza-audits : Create a new essenzaAudit.
     *
     * @param essenzaAuditDTO the essenzaAuditDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new essenzaAuditDTO, or with status 400 (Bad Request) if the essenzaAudit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/essenza-audits")
    @Timed
    public ResponseEntity<EssenzaAuditDTO> createEssenzaAudit(@Valid @RequestBody EssenzaAuditDTO essenzaAuditDTO) throws URISyntaxException {
        log.debug("REST request to save EssenzaAudit : {}", essenzaAuditDTO);
        if (essenzaAuditDTO.getId() != null) {
            throw new BadRequestAlertException("A new essenzaAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EssenzaAuditDTO result = essenzaAuditCustomService.save(essenzaAuditDTO);
        return ResponseEntity.created(new URI("/api/essenza-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /essenza-audits : Updates an existing essenzaAudit.
     *
     * @param essenzaAuditDTO the essenzaAuditDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated essenzaAuditDTO,
     * or with status 400 (Bad Request) if the essenzaAuditDTO is not valid,
     * or with status 500 (Internal Server Error) if the essenzaAuditDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/essenza-audits")
    @Timed
    public ResponseEntity<EssenzaAuditDTO> updateEssenzaAudit(@Valid @RequestBody EssenzaAuditDTO essenzaAuditDTO) throws URISyntaxException {
        log.debug("REST request to update EssenzaAudit : {}", essenzaAuditDTO);
        if (essenzaAuditDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EssenzaAuditDTO result = essenzaAuditCustomService.save(essenzaAuditDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, essenzaAuditDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /essenza-audits : get all the essenzaAudits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of essenzaAudits in body
     */
    @GetMapping("/essenza-audits")
    @Timed
    public List<EssenzaAuditDTO> getAllEssenzaAudits() {
        log.debug("REST request to get all EssenzaAudits");
        return essenzaAuditCustomService.findAll();
    }

    /**
     * GET  /essenza-audits/:id : get the "id" essenzaAudit.
     *
     * @param id the id of the essenzaAuditDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the essenzaAuditDTO, or with status 404 (Not Found)
     */
    @GetMapping("/essenza-audits/{id}")
    @Timed
    public ResponseEntity<EssenzaAuditDTO> getEssenzaAudit(@PathVariable Long id) {
        log.debug("REST request to get EssenzaAudit : {}", id);
        Optional<EssenzaAuditDTO> essenzaAuditDTO = essenzaAuditCustomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(essenzaAuditDTO);
    }


    /**
     * GET  /essenza-audits/by-essenza/:id : get the "id" essenzaAudit.
     *
     * @param id the id of the essenzaAuditDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the essenzaAuditDTO, or with status 404 (Not Found)
     */
    @GetMapping("/essenza-audits/by-essenza/{id}")
    @Timed
    public ResponseEntity<EssenzaAuditDTO> getEssenzaAuditByEssenza(@PathVariable Long id) {
        log.debug("REST request to get EssenzaAudit : {}", id);
        Optional<EssenzaAuditDTO> essenzaAuditDTO = essenzaAuditCustomService.getEssenzaAuditByEssenza(id);
        return ResponseUtil.wrapOrNotFound(essenzaAuditDTO);
    }

    /**
     * DELETE  /essenza-audits/:id : delete the "id" essenzaAudit.
     *
     * @param id the id of the essenzaAuditDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/essenza-audits/{id}")
    @Timed
    public ResponseEntity<Void> deleteEssenzaAudit(@PathVariable Long id) {
        log.debug("REST request to delete EssenzaAudit : {}", id);
        essenzaAuditCustomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
