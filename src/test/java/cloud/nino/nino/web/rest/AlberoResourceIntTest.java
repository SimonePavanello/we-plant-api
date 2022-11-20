package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.Albero;
import cloud.nino.nino.repository.AlberoRepository;
import cloud.nino.nino.service.AlberoService;
import cloud.nino.nino.service.dto.AlberoDTO;
import cloud.nino.nino.service.mapper.AlberoMapper;
import cloud.nino.nino.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static cloud.nino.nino.web.rest.TestUtil.sameInstant;
import static cloud.nino.nino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cloud.nino.nino.domain.enumeration.TipoDiSuolo;
/**
 * Test class for the AlberoResource REST controller.
 *
 * @see AlberoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class AlberoResourceIntTest {

    private static final Long DEFAULT_ENTITYID = 1L;
    private static final Long UPDATED_ENTITYID = 2L;

    private static final Integer DEFAULT_ID_PIANTA = 1;
    private static final Integer UPDATED_ID_PIANTA = 2;

    private static final Integer DEFAULT_CODICE_AREA = 1;
    private static final Integer UPDATED_CODICE_AREA = 2;

    private static final String DEFAULT_NOME_COMUNE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMUNE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLASSE_ALTEZZA = 1;
    private static final Integer UPDATED_CLASSE_ALTEZZA = 2;

    private static final Double DEFAULT_ALTEZZA = 1D;
    private static final Double UPDATED_ALTEZZA = 2D;

    private static final Integer DEFAULT_DIAMETRO_FUSTO = 1;
    private static final Integer UPDATED_DIAMETRO_FUSTO = 2;

    private static final Double DEFAULT_DIAMETRO = 1D;
    private static final Double UPDATED_DIAMETRO = 2D;

    private static final String DEFAULT_WKT = "AAAAAAAAAA";
    private static final String UPDATED_WKT = "BBBBBBBBBB";

    private static final String DEFAULT_AGGIORNAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_AGGIORNAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA = "BBBBBBBBBB";

    private static final TipoDiSuolo DEFAULT_TIPO_DI_SUOLO = TipoDiSuolo.AREA_VERDE;
    private static final TipoDiSuolo UPDATED_TIPO_DI_SUOLO = TipoDiSuolo.ZANELLA;

    private static final ZonedDateTime DEFAULT_DATA_IMPIANTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_IMPIANTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_ABBATTIMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ABBATTIMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_ULTIMO_AGGIORNAMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ULTIMO_AGGIORNAMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_PRIMA_RILEVAZIONE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_PRIMA_RILEVAZIONE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NOTE_TECNICHE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_TECNICHE = "BBBBBBBBBB";

    private static final String DEFAULT_POSIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_POSIZIONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private AlberoRepository alberoRepository;


    @Autowired
    private AlberoMapper alberoMapper;
    

    @Autowired
    private AlberoService alberoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlberoMockMvc;

    private Albero albero;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlberoResource alberoResource = new AlberoResource(alberoService);
        this.restAlberoMockMvc = MockMvcBuilders.standaloneSetup(alberoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Albero createEntity(EntityManager em) {
        Albero albero = new Albero()
            .entityid(DEFAULT_ENTITYID)
            .idPianta(DEFAULT_ID_PIANTA)
            .codiceArea(DEFAULT_CODICE_AREA)
            .nomeComune(DEFAULT_NOME_COMUNE)
            .classeAltezza(DEFAULT_CLASSE_ALTEZZA)
            .altezza(DEFAULT_ALTEZZA)
            .diametroFusto(DEFAULT_DIAMETRO_FUSTO)
            .diametro(DEFAULT_DIAMETRO)
            .wkt(DEFAULT_WKT)
            .aggiornamento(DEFAULT_AGGIORNAMENTO)
            .nota(DEFAULT_NOTA)
            .tipoDiSuolo(DEFAULT_TIPO_DI_SUOLO)
            .dataImpianto(DEFAULT_DATA_IMPIANTO)
            .dataAbbattimento(DEFAULT_DATA_ABBATTIMENTO)
            .dataUltimoAggiornamento(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO)
            .dataPrimaRilevazione(DEFAULT_DATA_PRIMA_RILEVAZIONE)
            .noteTecniche(DEFAULT_NOTE_TECNICHE)
            .posizione(DEFAULT_POSIZIONE)
            .deleted(DEFAULT_DELETED);
        return albero;
    }

    @Before
    public void initTest() {
        albero = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbero() throws Exception {
        int databaseSizeBeforeCreate = alberoRepository.findAll().size();

        // Create the Albero
        AlberoDTO alberoDTO = alberoMapper.toDto(albero);
        restAlberoMockMvc.perform(post("/api/alberos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoDTO)))
            .andExpect(status().isCreated());

        // Validate the Albero in the database
        List<Albero> alberoList = alberoRepository.findAll();
        assertThat(alberoList).hasSize(databaseSizeBeforeCreate + 1);
        Albero testAlbero = alberoList.get(alberoList.size() - 1);
        assertThat(testAlbero.getEntityid()).isEqualTo(DEFAULT_ENTITYID);
        assertThat(testAlbero.getIdPianta()).isEqualTo(DEFAULT_ID_PIANTA);
        assertThat(testAlbero.getCodiceArea()).isEqualTo(DEFAULT_CODICE_AREA);
        assertThat(testAlbero.getNomeComune()).isEqualTo(DEFAULT_NOME_COMUNE);
        assertThat(testAlbero.getClasseAltezza()).isEqualTo(DEFAULT_CLASSE_ALTEZZA);
        assertThat(testAlbero.getAltezza()).isEqualTo(DEFAULT_ALTEZZA);
        assertThat(testAlbero.getDiametroFusto()).isEqualTo(DEFAULT_DIAMETRO_FUSTO);
        assertThat(testAlbero.getDiametro()).isEqualTo(DEFAULT_DIAMETRO);
        assertThat(testAlbero.getWkt()).isEqualTo(DEFAULT_WKT);
        assertThat(testAlbero.getAggiornamento()).isEqualTo(DEFAULT_AGGIORNAMENTO);
        assertThat(testAlbero.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testAlbero.getTipoDiSuolo()).isEqualTo(DEFAULT_TIPO_DI_SUOLO);
        assertThat(testAlbero.getDataImpianto()).isEqualTo(DEFAULT_DATA_IMPIANTO);
        assertThat(testAlbero.getDataAbbattimento()).isEqualTo(DEFAULT_DATA_ABBATTIMENTO);
        assertThat(testAlbero.getDataUltimoAggiornamento()).isEqualTo(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO);
        assertThat(testAlbero.getDataPrimaRilevazione()).isEqualTo(DEFAULT_DATA_PRIMA_RILEVAZIONE);
        assertThat(testAlbero.getNoteTecniche()).isEqualTo(DEFAULT_NOTE_TECNICHE);
        assertThat(testAlbero.getPosizione()).isEqualTo(DEFAULT_POSIZIONE);
        assertThat(testAlbero.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createAlberoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alberoRepository.findAll().size();

        // Create the Albero with an existing ID
        albero.setId(1L);
        AlberoDTO alberoDTO = alberoMapper.toDto(albero);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlberoMockMvc.perform(post("/api/alberos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Albero in the database
        List<Albero> alberoList = alberoRepository.findAll();
        assertThat(alberoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlberos() throws Exception {
        // Initialize the database
        alberoRepository.saveAndFlush(albero);

        // Get all the alberoList
        restAlberoMockMvc.perform(get("/api/alberos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(albero.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityid").value(hasItem(DEFAULT_ENTITYID.intValue())))
            .andExpect(jsonPath("$.[*].idPianta").value(hasItem(DEFAULT_ID_PIANTA)))
            .andExpect(jsonPath("$.[*].codiceArea").value(hasItem(DEFAULT_CODICE_AREA)))
            .andExpect(jsonPath("$.[*].nomeComune").value(hasItem(DEFAULT_NOME_COMUNE.toString())))
            .andExpect(jsonPath("$.[*].classeAltezza").value(hasItem(DEFAULT_CLASSE_ALTEZZA)))
            .andExpect(jsonPath("$.[*].altezza").value(hasItem(DEFAULT_ALTEZZA.doubleValue())))
            .andExpect(jsonPath("$.[*].diametroFusto").value(hasItem(DEFAULT_DIAMETRO_FUSTO)))
            .andExpect(jsonPath("$.[*].diametro").value(hasItem(DEFAULT_DIAMETRO.doubleValue())))
            .andExpect(jsonPath("$.[*].wkt").value(hasItem(DEFAULT_WKT.toString())))
            .andExpect(jsonPath("$.[*].aggiornamento").value(hasItem(DEFAULT_AGGIORNAMENTO.toString())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.toString())))
            .andExpect(jsonPath("$.[*].tipoDiSuolo").value(hasItem(DEFAULT_TIPO_DI_SUOLO.toString())))
            .andExpect(jsonPath("$.[*].dataImpianto").value(hasItem(sameInstant(DEFAULT_DATA_IMPIANTO))))
            .andExpect(jsonPath("$.[*].dataAbbattimento").value(hasItem(sameInstant(DEFAULT_DATA_ABBATTIMENTO))))
            .andExpect(jsonPath("$.[*].dataUltimoAggiornamento").value(hasItem(sameInstant(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO))))
            .andExpect(jsonPath("$.[*].dataPrimaRilevazione").value(hasItem(sameInstant(DEFAULT_DATA_PRIMA_RILEVAZIONE))))
            .andExpect(jsonPath("$.[*].noteTecniche").value(hasItem(DEFAULT_NOTE_TECNICHE.toString())))
            .andExpect(jsonPath("$.[*].posizione").value(hasItem(DEFAULT_POSIZIONE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getAlbero() throws Exception {
        // Initialize the database
        alberoRepository.saveAndFlush(albero);

        // Get the albero
        restAlberoMockMvc.perform(get("/api/alberos/{id}", albero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(albero.getId().intValue()))
            .andExpect(jsonPath("$.entityid").value(DEFAULT_ENTITYID.intValue()))
            .andExpect(jsonPath("$.idPianta").value(DEFAULT_ID_PIANTA))
            .andExpect(jsonPath("$.codiceArea").value(DEFAULT_CODICE_AREA))
            .andExpect(jsonPath("$.nomeComune").value(DEFAULT_NOME_COMUNE.toString()))
            .andExpect(jsonPath("$.classeAltezza").value(DEFAULT_CLASSE_ALTEZZA))
            .andExpect(jsonPath("$.altezza").value(DEFAULT_ALTEZZA.doubleValue()))
            .andExpect(jsonPath("$.diametroFusto").value(DEFAULT_DIAMETRO_FUSTO))
            .andExpect(jsonPath("$.diametro").value(DEFAULT_DIAMETRO.doubleValue()))
            .andExpect(jsonPath("$.wkt").value(DEFAULT_WKT.toString()))
            .andExpect(jsonPath("$.aggiornamento").value(DEFAULT_AGGIORNAMENTO.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.toString()))
            .andExpect(jsonPath("$.tipoDiSuolo").value(DEFAULT_TIPO_DI_SUOLO.toString()))
            .andExpect(jsonPath("$.dataImpianto").value(sameInstant(DEFAULT_DATA_IMPIANTO)))
            .andExpect(jsonPath("$.dataAbbattimento").value(sameInstant(DEFAULT_DATA_ABBATTIMENTO)))
            .andExpect(jsonPath("$.dataUltimoAggiornamento").value(sameInstant(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO)))
            .andExpect(jsonPath("$.dataPrimaRilevazione").value(sameInstant(DEFAULT_DATA_PRIMA_RILEVAZIONE)))
            .andExpect(jsonPath("$.noteTecniche").value(DEFAULT_NOTE_TECNICHE.toString()))
            .andExpect(jsonPath("$.posizione").value(DEFAULT_POSIZIONE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAlbero() throws Exception {
        // Get the albero
        restAlberoMockMvc.perform(get("/api/alberos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbero() throws Exception {
        // Initialize the database
        alberoRepository.saveAndFlush(albero);

        int databaseSizeBeforeUpdate = alberoRepository.findAll().size();

        // Update the albero
        Albero updatedAlbero = alberoRepository.findById(albero.getId()).get();
        // Disconnect from session so that the updates on updatedAlbero are not directly saved in db
        em.detach(updatedAlbero);
        updatedAlbero
            .entityid(UPDATED_ENTITYID)
            .idPianta(UPDATED_ID_PIANTA)
            .codiceArea(UPDATED_CODICE_AREA)
            .nomeComune(UPDATED_NOME_COMUNE)
            .classeAltezza(UPDATED_CLASSE_ALTEZZA)
            .altezza(UPDATED_ALTEZZA)
            .diametroFusto(UPDATED_DIAMETRO_FUSTO)
            .diametro(UPDATED_DIAMETRO)
            .wkt(UPDATED_WKT)
            .aggiornamento(UPDATED_AGGIORNAMENTO)
            .nota(UPDATED_NOTA)
            .tipoDiSuolo(UPDATED_TIPO_DI_SUOLO)
            .dataImpianto(UPDATED_DATA_IMPIANTO)
            .dataAbbattimento(UPDATED_DATA_ABBATTIMENTO)
            .dataUltimoAggiornamento(UPDATED_DATA_ULTIMO_AGGIORNAMENTO)
            .dataPrimaRilevazione(UPDATED_DATA_PRIMA_RILEVAZIONE)
            .noteTecniche(UPDATED_NOTE_TECNICHE)
            .posizione(UPDATED_POSIZIONE)
            .deleted(UPDATED_DELETED);
        AlberoDTO alberoDTO = alberoMapper.toDto(updatedAlbero);

        restAlberoMockMvc.perform(put("/api/alberos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoDTO)))
            .andExpect(status().isOk());

        // Validate the Albero in the database
        List<Albero> alberoList = alberoRepository.findAll();
        assertThat(alberoList).hasSize(databaseSizeBeforeUpdate);
        Albero testAlbero = alberoList.get(alberoList.size() - 1);
        assertThat(testAlbero.getEntityid()).isEqualTo(UPDATED_ENTITYID);
        assertThat(testAlbero.getIdPianta()).isEqualTo(UPDATED_ID_PIANTA);
        assertThat(testAlbero.getCodiceArea()).isEqualTo(UPDATED_CODICE_AREA);
        assertThat(testAlbero.getNomeComune()).isEqualTo(UPDATED_NOME_COMUNE);
        assertThat(testAlbero.getClasseAltezza()).isEqualTo(UPDATED_CLASSE_ALTEZZA);
        assertThat(testAlbero.getAltezza()).isEqualTo(UPDATED_ALTEZZA);
        assertThat(testAlbero.getDiametroFusto()).isEqualTo(UPDATED_DIAMETRO_FUSTO);
        assertThat(testAlbero.getDiametro()).isEqualTo(UPDATED_DIAMETRO);
        assertThat(testAlbero.getWkt()).isEqualTo(UPDATED_WKT);
        assertThat(testAlbero.getAggiornamento()).isEqualTo(UPDATED_AGGIORNAMENTO);
        assertThat(testAlbero.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testAlbero.getTipoDiSuolo()).isEqualTo(UPDATED_TIPO_DI_SUOLO);
        assertThat(testAlbero.getDataImpianto()).isEqualTo(UPDATED_DATA_IMPIANTO);
        assertThat(testAlbero.getDataAbbattimento()).isEqualTo(UPDATED_DATA_ABBATTIMENTO);
        assertThat(testAlbero.getDataUltimoAggiornamento()).isEqualTo(UPDATED_DATA_ULTIMO_AGGIORNAMENTO);
        assertThat(testAlbero.getDataPrimaRilevazione()).isEqualTo(UPDATED_DATA_PRIMA_RILEVAZIONE);
        assertThat(testAlbero.getNoteTecniche()).isEqualTo(UPDATED_NOTE_TECNICHE);
        assertThat(testAlbero.getPosizione()).isEqualTo(UPDATED_POSIZIONE);
        assertThat(testAlbero.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbero() throws Exception {
        int databaseSizeBeforeUpdate = alberoRepository.findAll().size();

        // Create the Albero
        AlberoDTO alberoDTO = alberoMapper.toDto(albero);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlberoMockMvc.perform(put("/api/alberos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Albero in the database
        List<Albero> alberoList = alberoRepository.findAll();
        assertThat(alberoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlbero() throws Exception {
        // Initialize the database
        alberoRepository.saveAndFlush(albero);

        int databaseSizeBeforeDelete = alberoRepository.findAll().size();

        // Get the albero
        restAlberoMockMvc.perform(delete("/api/alberos/{id}", albero.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Albero> alberoList = alberoRepository.findAll();
        assertThat(alberoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Albero.class);
        Albero albero1 = new Albero();
        albero1.setId(1L);
        Albero albero2 = new Albero();
        albero2.setId(albero1.getId());
        assertThat(albero1).isEqualTo(albero2);
        albero2.setId(2L);
        assertThat(albero1).isNotEqualTo(albero2);
        albero1.setId(null);
        assertThat(albero1).isNotEqualTo(albero2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlberoDTO.class);
        AlberoDTO alberoDTO1 = new AlberoDTO();
        alberoDTO1.setId(1L);
        AlberoDTO alberoDTO2 = new AlberoDTO();
        assertThat(alberoDTO1).isNotEqualTo(alberoDTO2);
        alberoDTO2.setId(alberoDTO1.getId());
        assertThat(alberoDTO1).isEqualTo(alberoDTO2);
        alberoDTO2.setId(2L);
        assertThat(alberoDTO1).isNotEqualTo(alberoDTO2);
        alberoDTO1.setId(null);
        assertThat(alberoDTO1).isNotEqualTo(alberoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alberoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alberoMapper.fromId(null)).isNull();
    }
}
