package cloud.nino.nino.web.rest.custom;

import cloud.nino.nino.service.custom.AlberoCustomService;
import cloud.nino.nino.service.dto.AlberoDTO;
import cloud.nino.nino.service.dto.EssenzaDTO;
import cloud.nino.nino.service.dto.custom.AlberoCustomDTO;
import cloud.nino.nino.service.dto.custom.AlberoUserOperations;
import cloud.nino.nino.web.rest.errors.BadRequestAlertException;
import cloud.nino.nino.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST controller for managing Albero.
 */
@RestController
@RequestMapping("/api/custom")
public class AlberoCustomResource {

    private final Logger log = LoggerFactory.getLogger(AlberoCustomResource.class);

    private static final String ENTITY_NAME = "albero";

    private final AlberoCustomService alberoCustomService;

    public AlberoCustomResource(AlberoCustomService alberoCustomService) {
        this.alberoCustomService = alberoCustomService;
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
        AlberoDTO result = alberoCustomService.save(alberoDTO);
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
        AlberoDTO result = alberoCustomService.save(alberoDTO);
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
    public List<AlberoCustomDTO> getAllAlberos(Pageable pageable) {
        log.debug("REST request to get all Alberos");
        return alberoCustomService.findAll(pageable);
    }


    /**
     * GET  /alberos/export-history/:mainId : get the "id" alberoVisit.
     *
     * @param mainId the id of the alberoVisitDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alberoVisitDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alberos/export-history/{mainId}")
    @Timed
    public Void downloadAlberoHistory(@PathVariable Long mainId) {
        log.debug("REST request to download Albero history: {}", mainId);
        alberoCustomService.downloadAlberoHistory(Stream.of(mainId).collect(Collectors.toList()));
        return null;
    }

    /**
     * GET  /alberos/export-history-all
     */
    @GetMapping("/alberos/export-history-all")
    @Timed
    public ResponseEntity<Resource> downloadAlberoHistoryAll() {
        log.debug("REST request to download all Alberos history: {}");
        Resource file = alberoCustomService.downloadAlberoHistoryAll();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=Estrazione-we-plant.xlsx").contentType(MediaType.valueOf("application/vnd.ms-excel")).body(file);
    }

    /**
     * GET  /alberos/albero-without-plant-id : get all the alberos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alberos in body
     */
    @GetMapping("/alberos/albero-without-plant-id")
    @Timed
    public List<AlberoDTO> getAllAlberosWithoutPlantId() {
        log.debug("REST request to get all Alberos without plant id");
        return alberoCustomService.findAllWithoutPlantId();
    }


    /**
     * GET  /alberos/albero-without-plant-id : get all the alberos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alberos in body
     */
    @GetMapping("/alberos/user-operations")
    @Timed
    public List<AlberoUserOperations> getUserOperationsOnTrees(@RequestParam ZonedDateTime from, @RequestParam ZonedDateTime to) {
        log.debug("REST request to get all user operations");
        return alberoCustomService.getUserOperationsOnTrees(from, to);
    }

    /**
     * GET  /alberos/essenza-list : get all the alberos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alberos in body
     */
    @GetMapping("/alberos/essenza-list")
    @Timed
    public List<EssenzaDTO> getEssenzaList() {
        log.debug("REST request to get all Alberos");
        return alberoCustomService.findAllEssenza();
    }

    /**
     * GET  /alberos/:id : get the "id" albero.
     *
     * @param id the id of the alberoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alberoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alberos/{id}")
    @Timed
    public ResponseEntity<AlberoCustomDTO> getAlbero(@PathVariable Long id) {
        log.debug("REST request to get Albero : {}", id);
        Optional<AlberoCustomDTO> alberoDTO = alberoCustomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alberoDTO);
    }

    /**
     * GET  /alberos/is-id-pianta-free:idPianta : get the "id" albero.
     *
     * @param idPianta
     * @return boolean
     */
    @GetMapping("/alberos/is-id-pianta-free/{idPianta}")
    @Timed
    public Boolean isIdPiantaFree(@PathVariable Integer idPianta) {
        log.debug("REST request to check if idPianta available : {}", idPianta);
        return alberoCustomService.isIdPiantaFree(idPianta);
    }


    /**
     * GET  /alberos/by-codice-area/:codiceArea  get all the alberos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alberos in body
     */
    @GetMapping("/alberos/by-codice-area/{codiceArea}")
    @Timed
    public List<AlberoDTO> getAlberosByCodiceArea(@PathVariable Integer codiceArea) {
        log.debug("REST request to get all Alberos by codice area");
        return alberoCustomService.getAlberosByCodiceArea(codiceArea);
    }

    /**
     * GET  /alberos/:id : get the "id" albero.
     *
     * @param idPianta the id of the alberoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alberoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alberos/by-id-pianta/{idPianta}")
    @Timed
    public ResponseEntity<AlberoCustomDTO> getAlberoByIdPianta(@PathVariable Integer idPianta) {
        log.debug("REST request to get Albero by id pianta: {}", idPianta);
        Optional<AlberoCustomDTO> alberoDTO = alberoCustomService.findByIdPianta(idPianta);
        return ResponseUtil.wrapOrNotFound(alberoDTO);
    }


    /**
     * POST  /alberos/albero-and-essenza-audit : Create a new albero.
     *
     * @param alberoCustomDTO the alberoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alberoDTO, or with status 400 (Bad Request) if the albero has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alberos/albero-and-essenza-audit")
    @Timed
    public ResponseEntity<AlberoCustomDTO> createAlberoAndEssenzaAudit(@RequestBody AlberoCustomDTO alberoCustomDTO) throws URISyntaxException {
        log.debug("REST request to save Albero : {}", alberoCustomDTO);
        AlberoCustomDTO result = alberoCustomService.saveAlberoAndEssenzaAudit(alberoCustomDTO);
        return ResponseEntity.created(new URI("/api/alberos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
        alberoCustomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
