package cloud.nino.nino.web.rest.custom;

import cloud.nino.nino.domain.User;
import cloud.nino.nino.service.PrivacyService;
import cloud.nino.nino.service.UserService;
import cloud.nino.nino.service.dto.PrivacyDTO;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Privacy.
 */
@RestController
@RequestMapping("/api/custom")
public class PrivacyCustomResource {

    private final Logger log = LoggerFactory.getLogger(PrivacyCustomResource.class);

    private static final String ENTITY_NAME = "privacy";

    private final PrivacyService privacyService;

    private final UserService userService;

    public PrivacyCustomResource(PrivacyService privacyService, UserService userService) {
        this.privacyService = privacyService;
        this.userService = userService;
    }

    /**
     * POST  /privacies : Create a new privacy.
     *
     * @param privacyDTO the privacyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new privacyDTO, or with status 400 (Bad Request) if the privacy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/privacies")
    @Timed
    public ResponseEntity<PrivacyDTO> createPrivacy(@RequestBody PrivacyDTO privacyDTO) throws URISyntaxException {
        log.debug("REST request to save Privacy : {}", privacyDTO);
        if (privacyDTO.getId() != null) {
            throw new BadRequestAlertException("A new privacy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        privacyDTO.setTime(ZonedDateTime.now());
        userService.getCurrentUser().map(user -> {
            privacyDTO.setUserId(user.getId());
            return user;
        });
        PrivacyDTO result = privacyService.save(privacyDTO);
        return ResponseEntity.created(new URI("/api/privacies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/privacies/by-email-and-active-user")
    @Timed
    public ResponseEntity<PrivacyDTO> consentPrivacyAndActiveUser(@RequestParam String email) throws URISyntaxException {
        log.debug("REST request to consent Privacy by email: {}", email);
        Optional<User> userOptional = userService.getUserWithAuthoritiesByEmail(email);
        if(userOptional.isPresent()){
            userService.activateRegistration(userOptional.get());
            PrivacyDTO privacyDTO = new PrivacyDTO();
            privacyDTO.setPrivacy(true);
            privacyDTO.setUserId(userOptional.get().getId());
            return createPrivacy(privacyDTO);
        }else {
            throw new BadRequestAlertException("User could not found", ENTITY_NAME, "usernotfound");
        }
    }

    /**
     * PUT  /privacies : Updates an existing privacy.
     *
     * @param privacyDTO the privacyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated privacyDTO,
     * or with status 400 (Bad Request) if the privacyDTO is not valid,
     * or with status 500 (Internal Server Error) if the privacyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/privacies")
    @Timed
    public ResponseEntity<PrivacyDTO> updatePrivacy(@RequestBody PrivacyDTO privacyDTO) throws URISyntaxException {
        log.debug("REST request to update Privacy : {}", privacyDTO);
        if (privacyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrivacyDTO result = privacyService.save(privacyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, privacyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /privacies : get all the privacies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of privacies in body
     */
    @GetMapping("/privacies")
    @Timed
    public List<PrivacyDTO> getAllPrivacies() {
        log.debug("REST request to get all Privacies");
        return privacyService.findAll();
    }

    /**
     * GET  /privacies/:id : get the "id" privacy.
     *
     * @param id the id of the privacyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the privacyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/privacies/{id}")
    @Timed
    public ResponseEntity<PrivacyDTO> getPrivacy(@PathVariable Long id) {
        log.debug("REST request to get Privacy : {}", id);
        Optional<PrivacyDTO> privacyDTO = privacyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(privacyDTO);
    }

    /**
     * DELETE  /privacies/:id : delete the "id" privacy.
     *
     * @param id the id of the privacyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/privacies/{id}")
    @Timed
    public ResponseEntity<Void> deletePrivacy(@PathVariable Long id) {
        log.debug("REST request to delete Privacy : {}", id);
        privacyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
