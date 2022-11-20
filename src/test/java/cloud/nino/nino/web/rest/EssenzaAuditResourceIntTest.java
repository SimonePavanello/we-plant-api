package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.EssenzaAudit;
import cloud.nino.nino.repository.EssenzaAuditRepository;
import cloud.nino.nino.service.EssenzaAuditService;
import cloud.nino.nino.service.dto.EssenzaAuditDTO;
import cloud.nino.nino.service.mapper.EssenzaAuditMapper;
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

/**
 * Test class for the EssenzaAuditResource REST controller.
 *
 * @see EssenzaAuditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class EssenzaAuditResourceIntTest {

    private static final Long DEFAULT_ID_COMUNE = 1L;
    private static final Long UPDATED_ID_COMUNE = 2L;

    private static final String DEFAULT_TIPO_VERDE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_VERDE = "BBBBBBBBBB";

    private static final String DEFAULT_GENERE_E_SPECIE = "AAAAAAAAAA";
    private static final String UPDATED_GENERE_E_SPECIE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_COMUNE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_VALORE_SICUREZZA = "AAAAAAAAAA";
    private static final String UPDATED_VALORE_SICUREZZA = "BBBBBBBBBB";

    private static final String DEFAULT_VALORE_BIO_AMBIENTALE = "AAAAAAAAAA";
    private static final String UPDATED_VALORE_BIO_AMBIENTALE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVENIENZA = "AAAAAAAAAA";
    private static final String UPDATED_PROVENIENZA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final String DEFAULT_USIE_CURIOSITA = "AAAAAAAAAA";
    private static final String UPDATED_USIE_CURIOSITA = "BBBBBBBBBB";

    private static final String DEFAULT_GENERE = "AAAAAAAAAA";
    private static final String UPDATED_GENERE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_ULTIMO_AGGIORNAMENTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ULTIMO_AGGIORNAMENTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private EssenzaAuditRepository essenzaAuditRepository;


    @Autowired
    private EssenzaAuditMapper essenzaAuditMapper;
    

    @Autowired
    private EssenzaAuditService essenzaAuditService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEssenzaAuditMockMvc;

    private EssenzaAudit essenzaAudit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EssenzaAuditResource essenzaAuditResource = new EssenzaAuditResource(essenzaAuditService);
        this.restEssenzaAuditMockMvc = MockMvcBuilders.standaloneSetup(essenzaAuditResource)
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
    public static EssenzaAudit createEntity(EntityManager em) {
        EssenzaAudit essenzaAudit = new EssenzaAudit()
            .idComune(DEFAULT_ID_COMUNE)
            .tipoVerde(DEFAULT_TIPO_VERDE)
            .genereESpecie(DEFAULT_GENERE_E_SPECIE)
            .nomeComune(DEFAULT_NOME_COMUNE)
            .valoreSicurezza(DEFAULT_VALORE_SICUREZZA)
            .valoreBioAmbientale(DEFAULT_VALORE_BIO_AMBIENTALE)
            .provenienza(DEFAULT_PROVENIENZA)
            .descrizione(DEFAULT_DESCRIZIONE)
            .usieCuriosita(DEFAULT_USIE_CURIOSITA)
            .genere(DEFAULT_GENERE)
            .specie(DEFAULT_SPECIE)
            .dataUltimoAggiornamento(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO);
        return essenzaAudit;
    }

    @Before
    public void initTest() {
        essenzaAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createEssenzaAudit() throws Exception {
        int databaseSizeBeforeCreate = essenzaAuditRepository.findAll().size();

        // Create the EssenzaAudit
        EssenzaAuditDTO essenzaAuditDTO = essenzaAuditMapper.toDto(essenzaAudit);
        restEssenzaAuditMockMvc.perform(post("/api/essenza-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaAuditDTO)))
            .andExpect(status().isCreated());

        // Validate the EssenzaAudit in the database
        List<EssenzaAudit> essenzaAuditList = essenzaAuditRepository.findAll();
        assertThat(essenzaAuditList).hasSize(databaseSizeBeforeCreate + 1);
        EssenzaAudit testEssenzaAudit = essenzaAuditList.get(essenzaAuditList.size() - 1);
        assertThat(testEssenzaAudit.getIdComune()).isEqualTo(DEFAULT_ID_COMUNE);
        assertThat(testEssenzaAudit.getTipoVerde()).isEqualTo(DEFAULT_TIPO_VERDE);
        assertThat(testEssenzaAudit.getGenereESpecie()).isEqualTo(DEFAULT_GENERE_E_SPECIE);
        assertThat(testEssenzaAudit.getNomeComune()).isEqualTo(DEFAULT_NOME_COMUNE);
        assertThat(testEssenzaAudit.getValoreSicurezza()).isEqualTo(DEFAULT_VALORE_SICUREZZA);
        assertThat(testEssenzaAudit.getValoreBioAmbientale()).isEqualTo(DEFAULT_VALORE_BIO_AMBIENTALE);
        assertThat(testEssenzaAudit.getProvenienza()).isEqualTo(DEFAULT_PROVENIENZA);
        assertThat(testEssenzaAudit.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testEssenzaAudit.getUsieCuriosita()).isEqualTo(DEFAULT_USIE_CURIOSITA);
        assertThat(testEssenzaAudit.getGenere()).isEqualTo(DEFAULT_GENERE);
        assertThat(testEssenzaAudit.getSpecie()).isEqualTo(DEFAULT_SPECIE);
        assertThat(testEssenzaAudit.getDataUltimoAggiornamento()).isEqualTo(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO);
    }

    @Test
    @Transactional
    public void createEssenzaAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = essenzaAuditRepository.findAll().size();

        // Create the EssenzaAudit with an existing ID
        essenzaAudit.setId(1L);
        EssenzaAuditDTO essenzaAuditDTO = essenzaAuditMapper.toDto(essenzaAudit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEssenzaAuditMockMvc.perform(post("/api/essenza-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EssenzaAudit in the database
        List<EssenzaAudit> essenzaAuditList = essenzaAuditRepository.findAll();
        assertThat(essenzaAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdComuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = essenzaAuditRepository.findAll().size();
        // set the field null
        essenzaAudit.setIdComune(null);

        // Create the EssenzaAudit, which fails.
        EssenzaAuditDTO essenzaAuditDTO = essenzaAuditMapper.toDto(essenzaAudit);

        restEssenzaAuditMockMvc.perform(post("/api/essenza-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaAuditDTO)))
            .andExpect(status().isBadRequest());

        List<EssenzaAudit> essenzaAuditList = essenzaAuditRepository.findAll();
        assertThat(essenzaAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEssenzaAudits() throws Exception {
        // Initialize the database
        essenzaAuditRepository.saveAndFlush(essenzaAudit);

        // Get all the essenzaAuditList
        restEssenzaAuditMockMvc.perform(get("/api/essenza-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(essenzaAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].idComune").value(hasItem(DEFAULT_ID_COMUNE.intValue())))
            .andExpect(jsonPath("$.[*].tipoVerde").value(hasItem(DEFAULT_TIPO_VERDE.toString())))
            .andExpect(jsonPath("$.[*].genereESpecie").value(hasItem(DEFAULT_GENERE_E_SPECIE.toString())))
            .andExpect(jsonPath("$.[*].nomeComune").value(hasItem(DEFAULT_NOME_COMUNE.toString())))
            .andExpect(jsonPath("$.[*].valoreSicurezza").value(hasItem(DEFAULT_VALORE_SICUREZZA.toString())))
            .andExpect(jsonPath("$.[*].valoreBioAmbientale").value(hasItem(DEFAULT_VALORE_BIO_AMBIENTALE.toString())))
            .andExpect(jsonPath("$.[*].provenienza").value(hasItem(DEFAULT_PROVENIENZA.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())))
            .andExpect(jsonPath("$.[*].usieCuriosita").value(hasItem(DEFAULT_USIE_CURIOSITA.toString())))
            .andExpect(jsonPath("$.[*].genere").value(hasItem(DEFAULT_GENERE.toString())))
            .andExpect(jsonPath("$.[*].specie").value(hasItem(DEFAULT_SPECIE.toString())))
            .andExpect(jsonPath("$.[*].dataUltimoAggiornamento").value(hasItem(sameInstant(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO))));
    }
    

    @Test
    @Transactional
    public void getEssenzaAudit() throws Exception {
        // Initialize the database
        essenzaAuditRepository.saveAndFlush(essenzaAudit);

        // Get the essenzaAudit
        restEssenzaAuditMockMvc.perform(get("/api/essenza-audits/{id}", essenzaAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(essenzaAudit.getId().intValue()))
            .andExpect(jsonPath("$.idComune").value(DEFAULT_ID_COMUNE.intValue()))
            .andExpect(jsonPath("$.tipoVerde").value(DEFAULT_TIPO_VERDE.toString()))
            .andExpect(jsonPath("$.genereESpecie").value(DEFAULT_GENERE_E_SPECIE.toString()))
            .andExpect(jsonPath("$.nomeComune").value(DEFAULT_NOME_COMUNE.toString()))
            .andExpect(jsonPath("$.valoreSicurezza").value(DEFAULT_VALORE_SICUREZZA.toString()))
            .andExpect(jsonPath("$.valoreBioAmbientale").value(DEFAULT_VALORE_BIO_AMBIENTALE.toString()))
            .andExpect(jsonPath("$.provenienza").value(DEFAULT_PROVENIENZA.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()))
            .andExpect(jsonPath("$.usieCuriosita").value(DEFAULT_USIE_CURIOSITA.toString()))
            .andExpect(jsonPath("$.genere").value(DEFAULT_GENERE.toString()))
            .andExpect(jsonPath("$.specie").value(DEFAULT_SPECIE.toString()))
            .andExpect(jsonPath("$.dataUltimoAggiornamento").value(sameInstant(DEFAULT_DATA_ULTIMO_AGGIORNAMENTO)));
    }
    @Test
    @Transactional
    public void getNonExistingEssenzaAudit() throws Exception {
        // Get the essenzaAudit
        restEssenzaAuditMockMvc.perform(get("/api/essenza-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEssenzaAudit() throws Exception {
        // Initialize the database
        essenzaAuditRepository.saveAndFlush(essenzaAudit);

        int databaseSizeBeforeUpdate = essenzaAuditRepository.findAll().size();

        // Update the essenzaAudit
        EssenzaAudit updatedEssenzaAudit = essenzaAuditRepository.findById(essenzaAudit.getId()).get();
        // Disconnect from session so that the updates on updatedEssenzaAudit are not directly saved in db
        em.detach(updatedEssenzaAudit);
        updatedEssenzaAudit
            .idComune(UPDATED_ID_COMUNE)
            .tipoVerde(UPDATED_TIPO_VERDE)
            .genereESpecie(UPDATED_GENERE_E_SPECIE)
            .nomeComune(UPDATED_NOME_COMUNE)
            .valoreSicurezza(UPDATED_VALORE_SICUREZZA)
            .valoreBioAmbientale(UPDATED_VALORE_BIO_AMBIENTALE)
            .provenienza(UPDATED_PROVENIENZA)
            .descrizione(UPDATED_DESCRIZIONE)
            .usieCuriosita(UPDATED_USIE_CURIOSITA)
            .genere(UPDATED_GENERE)
            .specie(UPDATED_SPECIE)
            .dataUltimoAggiornamento(UPDATED_DATA_ULTIMO_AGGIORNAMENTO);
        EssenzaAuditDTO essenzaAuditDTO = essenzaAuditMapper.toDto(updatedEssenzaAudit);

        restEssenzaAuditMockMvc.perform(put("/api/essenza-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaAuditDTO)))
            .andExpect(status().isOk());

        // Validate the EssenzaAudit in the database
        List<EssenzaAudit> essenzaAuditList = essenzaAuditRepository.findAll();
        assertThat(essenzaAuditList).hasSize(databaseSizeBeforeUpdate);
        EssenzaAudit testEssenzaAudit = essenzaAuditList.get(essenzaAuditList.size() - 1);
        assertThat(testEssenzaAudit.getIdComune()).isEqualTo(UPDATED_ID_COMUNE);
        assertThat(testEssenzaAudit.getTipoVerde()).isEqualTo(UPDATED_TIPO_VERDE);
        assertThat(testEssenzaAudit.getGenereESpecie()).isEqualTo(UPDATED_GENERE_E_SPECIE);
        assertThat(testEssenzaAudit.getNomeComune()).isEqualTo(UPDATED_NOME_COMUNE);
        assertThat(testEssenzaAudit.getValoreSicurezza()).isEqualTo(UPDATED_VALORE_SICUREZZA);
        assertThat(testEssenzaAudit.getValoreBioAmbientale()).isEqualTo(UPDATED_VALORE_BIO_AMBIENTALE);
        assertThat(testEssenzaAudit.getProvenienza()).isEqualTo(UPDATED_PROVENIENZA);
        assertThat(testEssenzaAudit.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testEssenzaAudit.getUsieCuriosita()).isEqualTo(UPDATED_USIE_CURIOSITA);
        assertThat(testEssenzaAudit.getGenere()).isEqualTo(UPDATED_GENERE);
        assertThat(testEssenzaAudit.getSpecie()).isEqualTo(UPDATED_SPECIE);
        assertThat(testEssenzaAudit.getDataUltimoAggiornamento()).isEqualTo(UPDATED_DATA_ULTIMO_AGGIORNAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingEssenzaAudit() throws Exception {
        int databaseSizeBeforeUpdate = essenzaAuditRepository.findAll().size();

        // Create the EssenzaAudit
        EssenzaAuditDTO essenzaAuditDTO = essenzaAuditMapper.toDto(essenzaAudit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEssenzaAuditMockMvc.perform(put("/api/essenza-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaAuditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EssenzaAudit in the database
        List<EssenzaAudit> essenzaAuditList = essenzaAuditRepository.findAll();
        assertThat(essenzaAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEssenzaAudit() throws Exception {
        // Initialize the database
        essenzaAuditRepository.saveAndFlush(essenzaAudit);

        int databaseSizeBeforeDelete = essenzaAuditRepository.findAll().size();

        // Get the essenzaAudit
        restEssenzaAuditMockMvc.perform(delete("/api/essenza-audits/{id}", essenzaAudit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EssenzaAudit> essenzaAuditList = essenzaAuditRepository.findAll();
        assertThat(essenzaAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EssenzaAudit.class);
        EssenzaAudit essenzaAudit1 = new EssenzaAudit();
        essenzaAudit1.setId(1L);
        EssenzaAudit essenzaAudit2 = new EssenzaAudit();
        essenzaAudit2.setId(essenzaAudit1.getId());
        assertThat(essenzaAudit1).isEqualTo(essenzaAudit2);
        essenzaAudit2.setId(2L);
        assertThat(essenzaAudit1).isNotEqualTo(essenzaAudit2);
        essenzaAudit1.setId(null);
        assertThat(essenzaAudit1).isNotEqualTo(essenzaAudit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EssenzaAuditDTO.class);
        EssenzaAuditDTO essenzaAuditDTO1 = new EssenzaAuditDTO();
        essenzaAuditDTO1.setId(1L);
        EssenzaAuditDTO essenzaAuditDTO2 = new EssenzaAuditDTO();
        assertThat(essenzaAuditDTO1).isNotEqualTo(essenzaAuditDTO2);
        essenzaAuditDTO2.setId(essenzaAuditDTO1.getId());
        assertThat(essenzaAuditDTO1).isEqualTo(essenzaAuditDTO2);
        essenzaAuditDTO2.setId(2L);
        assertThat(essenzaAuditDTO1).isNotEqualTo(essenzaAuditDTO2);
        essenzaAuditDTO1.setId(null);
        assertThat(essenzaAuditDTO1).isNotEqualTo(essenzaAuditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(essenzaAuditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(essenzaAuditMapper.fromId(null)).isNull();
    }
}
