package cloud.nino.nino.web.rest;

import com.codahale.metrics.annotation.Timed;
import cloud.nino.nino.service.NinoUserService;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import cloud.nino.nino.service.dto.NinoUserDTO;
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
 * REST controller for managing NinoUser.
 */
@RestController
@RequestMapping("/api")
public class NinoUserResource {

    private final Logger log = LoggerFactory.getLogger(NinoUserResource.class);

    private static final String ENTITY_NAME = "ninoUser";

    private final NinoUserService ninoUserService;

    public NinoUserResource(NinoUserService ninoUserService) {
        this.ninoUserService = ninoUserService;
    }

    /**
     * POST  /nino-users : Create a new ninoUser.
     *
     * @param ninoUserDTO the ninoUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ninoUserDTO, or with status 400 (Bad Request) if the ninoUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nino-users")
    @Timed
    public ResponseEntity<NinoUserDTO> createNinoUser(@RequestBody NinoUserDTO ninoUserDTO) throws URISyntaxException {
        log.debug("REST request to save NinoUser : {}", ninoUserDTO);
        if (ninoUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new ninoUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NinoUserDTO result = ninoUserService.save(ninoUserDTO);
        return ResponseEntity.created(new URI("/api/nino-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nino-users : Updates an existing ninoUser.
     *
     * @param ninoUserDTO the ninoUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ninoUserDTO,
     * or with status 400 (Bad Request) if the ninoUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the ninoUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nino-users")
    @Timed
    public ResponseEntity<NinoUserDTO> updateNinoUser(@RequestBody NinoUserDTO ninoUserDTO) throws URISyntaxException {
        log.debug("REST request to update NinoUser : {}", ninoUserDTO);
        if (ninoUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NinoUserDTO result = ninoUserService.save(ninoUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ninoUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nino-users : get all the ninoUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ninoUsers in body
     */
    @GetMapping("/nino-users")
    @Timed
    public List<NinoUserDTO> getAllNinoUsers() {
        log.debug("REST request to get all NinoUsers");
        return ninoUserService.findAll();
    }

    /**
     * GET  /nino-users/:id : get the "id" ninoUser.
     *
     * @param id the id of the ninoUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ninoUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/nino-users/{id}")
    @Timed
    public ResponseEntity<NinoUserDTO> getNinoUser(@PathVariable Long id) {
        log.debug("REST request to get NinoUser : {}", id);
        Optional<NinoUserDTO> ninoUserDTO = ninoUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ninoUserDTO);
    }

    /**
     * DELETE  /nino-users/:id : delete the "id" ninoUser.
     *
     * @param id the id of the ninoUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nino-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteNinoUser(@PathVariable Long id) {
        log.debug("REST request to delete NinoUser : {}", id);
        ninoUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
