package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.Essenza;
import cloud.nino.nino.repository.EssenzaRepository;
import cloud.nino.nino.service.EssenzaService;
import cloud.nino.nino.service.dto.EssenzaDTO;
import cloud.nino.nino.service.mapper.EssenzaMapper;
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
import java.util.List;


import static cloud.nino.nino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EssenzaResource REST controller.
 *
 * @see EssenzaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class EssenzaResourceIntTest {

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

    @Autowired
    private EssenzaRepository essenzaRepository;


    @Autowired
    private EssenzaMapper essenzaMapper;
    

    @Autowired
    private EssenzaService essenzaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEssenzaMockMvc;

    private Essenza essenza;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EssenzaResource essenzaResource = new EssenzaResource(essenzaService);
        this.restEssenzaMockMvc = MockMvcBuilders.standaloneSetup(essenzaResource)
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
    public static Essenza createEntity(EntityManager em) {
        Essenza essenza = new Essenza()
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
            .specie(DEFAULT_SPECIE);
        return essenza;
    }

    @Before
    public void initTest() {
        essenza = createEntity(em);
    }

    @Test
    @Transactional
    public void createEssenza() throws Exception {
        int databaseSizeBeforeCreate = essenzaRepository.findAll().size();

        // Create the Essenza
        EssenzaDTO essenzaDTO = essenzaMapper.toDto(essenza);
        restEssenzaMockMvc.perform(post("/api/essenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaDTO)))
            .andExpect(status().isCreated());

        // Validate the Essenza in the database
        List<Essenza> essenzaList = essenzaRepository.findAll();
        assertThat(essenzaList).hasSize(databaseSizeBeforeCreate + 1);
        Essenza testEssenza = essenzaList.get(essenzaList.size() - 1);
        assertThat(testEssenza.getIdComune()).isEqualTo(DEFAULT_ID_COMUNE);
        assertThat(testEssenza.getTipoVerde()).isEqualTo(DEFAULT_TIPO_VERDE);
        assertThat(testEssenza.getGenereESpecie()).isEqualTo(DEFAULT_GENERE_E_SPECIE);
        assertThat(testEssenza.getNomeComune()).isEqualTo(DEFAULT_NOME_COMUNE);
        assertThat(testEssenza.getValoreSicurezza()).isEqualTo(DEFAULT_VALORE_SICUREZZA);
        assertThat(testEssenza.getValoreBioAmbientale()).isEqualTo(DEFAULT_VALORE_BIO_AMBIENTALE);
        assertThat(testEssenza.getProvenienza()).isEqualTo(DEFAULT_PROVENIENZA);
        assertThat(testEssenza.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testEssenza.getUsieCuriosita()).isEqualTo(DEFAULT_USIE_CURIOSITA);
        assertThat(testEssenza.getGenere()).isEqualTo(DEFAULT_GENERE);
        assertThat(testEssenza.getSpecie()).isEqualTo(DEFAULT_SPECIE);
    }

    @Test
    @Transactional
    public void createEssenzaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = essenzaRepository.findAll().size();

        // Create the Essenza with an existing ID
        essenza.setId(1L);
        EssenzaDTO essenzaDTO = essenzaMapper.toDto(essenza);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEssenzaMockMvc.perform(post("/api/essenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Essenza in the database
        List<Essenza> essenzaList = essenzaRepository.findAll();
        assertThat(essenzaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdComuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = essenzaRepository.findAll().size();
        // set the field null
        essenza.setIdComune(null);

        // Create the Essenza, which fails.
        EssenzaDTO essenzaDTO = essenzaMapper.toDto(essenza);

        restEssenzaMockMvc.perform(post("/api/essenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaDTO)))
            .andExpect(status().isBadRequest());

        List<Essenza> essenzaList = essenzaRepository.findAll();
        assertThat(essenzaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEssenzas() throws Exception {
        // Initialize the database
        essenzaRepository.saveAndFlush(essenza);

        // Get all the essenzaList
        restEssenzaMockMvc.perform(get("/api/essenzas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(essenza.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].specie").value(hasItem(DEFAULT_SPECIE.toString())));
    }
    

    @Test
    @Transactional
    public void getEssenza() throws Exception {
        // Initialize the database
        essenzaRepository.saveAndFlush(essenza);

        // Get the essenza
        restEssenzaMockMvc.perform(get("/api/essenzas/{id}", essenza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(essenza.getId().intValue()))
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
            .andExpect(jsonPath("$.specie").value(DEFAULT_SPECIE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEssenza() throws Exception {
        // Get the essenza
        restEssenzaMockMvc.perform(get("/api/essenzas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEssenza() throws Exception {
        // Initialize the database
        essenzaRepository.saveAndFlush(essenza);

        int databaseSizeBeforeUpdate = essenzaRepository.findAll().size();

        // Update the essenza
        Essenza updatedEssenza = essenzaRepository.findById(essenza.getId()).get();
        // Disconnect from session so that the updates on updatedEssenza are not directly saved in db
        em.detach(updatedEssenza);
        updatedEssenza
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
            .specie(UPDATED_SPECIE);
        EssenzaDTO essenzaDTO = essenzaMapper.toDto(updatedEssenza);

        restEssenzaMockMvc.perform(put("/api/essenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaDTO)))
            .andExpect(status().isOk());

        // Validate the Essenza in the database
        List<Essenza> essenzaList = essenzaRepository.findAll();
        assertThat(essenzaList).hasSize(databaseSizeBeforeUpdate);
        Essenza testEssenza = essenzaList.get(essenzaList.size() - 1);
        assertThat(testEssenza.getIdComune()).isEqualTo(UPDATED_ID_COMUNE);
        assertThat(testEssenza.getTipoVerde()).isEqualTo(UPDATED_TIPO_VERDE);
        assertThat(testEssenza.getGenereESpecie()).isEqualTo(UPDATED_GENERE_E_SPECIE);
        assertThat(testEssenza.getNomeComune()).isEqualTo(UPDATED_NOME_COMUNE);
        assertThat(testEssenza.getValoreSicurezza()).isEqualTo(UPDATED_VALORE_SICUREZZA);
        assertThat(testEssenza.getValoreBioAmbientale()).isEqualTo(UPDATED_VALORE_BIO_AMBIENTALE);
        assertThat(testEssenza.getProvenienza()).isEqualTo(UPDATED_PROVENIENZA);
        assertThat(testEssenza.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testEssenza.getUsieCuriosita()).isEqualTo(UPDATED_USIE_CURIOSITA);
        assertThat(testEssenza.getGenere()).isEqualTo(UPDATED_GENERE);
        assertThat(testEssenza.getSpecie()).isEqualTo(UPDATED_SPECIE);
    }

    @Test
    @Transactional
    public void updateNonExistingEssenza() throws Exception {
        int databaseSizeBeforeUpdate = essenzaRepository.findAll().size();

        // Create the Essenza
        EssenzaDTO essenzaDTO = essenzaMapper.toDto(essenza);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEssenzaMockMvc.perform(put("/api/essenzas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(essenzaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Essenza in the database
        List<Essenza> essenzaList = essenzaRepository.findAll();
        assertThat(essenzaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEssenza() throws Exception {
        // Initialize the database
        essenzaRepository.saveAndFlush(essenza);

        int databaseSizeBeforeDelete = essenzaRepository.findAll().size();

        // Get the essenza
        restEssenzaMockMvc.perform(delete("/api/essenzas/{id}", essenza.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Essenza> essenzaList = essenzaRepository.findAll();
        assertThat(essenzaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Essenza.class);
        Essenza essenza1 = new Essenza();
        essenza1.setId(1L);
        Essenza essenza2 = new Essenza();
        essenza2.setId(essenza1.getId());
        assertThat(essenza1).isEqualTo(essenza2);
        essenza2.setId(2L);
        assertThat(essenza1).isNotEqualTo(essenza2);
        essenza1.setId(null);
        assertThat(essenza1).isNotEqualTo(essenza2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EssenzaDTO.class);
        EssenzaDTO essenzaDTO1 = new EssenzaDTO();
        essenzaDTO1.setId(1L);
        EssenzaDTO essenzaDTO2 = new EssenzaDTO();
        assertThat(essenzaDTO1).isNotEqualTo(essenzaDTO2);
        essenzaDTO2.setId(essenzaDTO1.getId());
        assertThat(essenzaDTO1).isEqualTo(essenzaDTO2);
        essenzaDTO2.setId(2L);
        assertThat(essenzaDTO1).isNotEqualTo(essenzaDTO2);
        essenzaDTO1.setId(null);
        assertThat(essenzaDTO1).isNotEqualTo(essenzaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(essenzaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(essenzaMapper.fromId(null)).isNull();
    }
}
