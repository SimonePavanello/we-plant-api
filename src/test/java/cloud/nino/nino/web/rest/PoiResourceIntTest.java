package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.Poi;
import cloud.nino.nino.repository.PoiRepository;
import cloud.nino.nino.service.PoiService;
import cloud.nino.nino.service.dto.PoiDTO;
import cloud.nino.nino.service.mapper.PoiMapper;
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
 * Test class for the PoiResource REST controller.
 *
 * @see PoiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class PoiResourceIntTest {

    private static final String DEFAULT_POI_ID = "AAAAAAAAAA";
    private static final String UPDATED_POI_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PoiRepository poiRepository;


    @Autowired
    private PoiMapper poiMapper;
    

    @Autowired
    private PoiService poiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoiMockMvc;

    private Poi poi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoiResource poiResource = new PoiResource(poiService);
        this.restPoiMockMvc = MockMvcBuilders.standaloneSetup(poiResource)
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
    public static Poi createEntity(EntityManager em) {
        Poi poi = new Poi()
            .poiId(DEFAULT_POI_ID)
            .name(DEFAULT_NAME);
        return poi;
    }

    @Before
    public void initTest() {
        poi = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoi() throws Exception {
        int databaseSizeBeforeCreate = poiRepository.findAll().size();

        // Create the Poi
        PoiDTO poiDTO = poiMapper.toDto(poi);
        restPoiMockMvc.perform(post("/api/pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poiDTO)))
            .andExpect(status().isCreated());

        // Validate the Poi in the database
        List<Poi> poiList = poiRepository.findAll();
        assertThat(poiList).hasSize(databaseSizeBeforeCreate + 1);
        Poi testPoi = poiList.get(poiList.size() - 1);
        assertThat(testPoi.getPoiId()).isEqualTo(DEFAULT_POI_ID);
        assertThat(testPoi.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPoiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poiRepository.findAll().size();

        // Create the Poi with an existing ID
        poi.setId(1L);
        PoiDTO poiDTO = poiMapper.toDto(poi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoiMockMvc.perform(post("/api/pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poi in the database
        List<Poi> poiList = poiRepository.findAll();
        assertThat(poiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPois() throws Exception {
        // Initialize the database
        poiRepository.saveAndFlush(poi);

        // Get all the poiList
        restPoiMockMvc.perform(get("/api/pois?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poi.getId().intValue())))
            .andExpect(jsonPath("$.[*].poiId").value(hasItem(DEFAULT_POI_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getPoi() throws Exception {
        // Initialize the database
        poiRepository.saveAndFlush(poi);

        // Get the poi
        restPoiMockMvc.perform(get("/api/pois/{id}", poi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poi.getId().intValue()))
            .andExpect(jsonPath("$.poiId").value(DEFAULT_POI_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPoi() throws Exception {
        // Get the poi
        restPoiMockMvc.perform(get("/api/pois/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoi() throws Exception {
        // Initialize the database
        poiRepository.saveAndFlush(poi);

        int databaseSizeBeforeUpdate = poiRepository.findAll().size();

        // Update the poi
        Poi updatedPoi = poiRepository.findById(poi.getId()).get();
        // Disconnect from session so that the updates on updatedPoi are not directly saved in db
        em.detach(updatedPoi);
        updatedPoi
            .poiId(UPDATED_POI_ID)
            .name(UPDATED_NAME);
        PoiDTO poiDTO = poiMapper.toDto(updatedPoi);

        restPoiMockMvc.perform(put("/api/pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poiDTO)))
            .andExpect(status().isOk());

        // Validate the Poi in the database
        List<Poi> poiList = poiRepository.findAll();
        assertThat(poiList).hasSize(databaseSizeBeforeUpdate);
        Poi testPoi = poiList.get(poiList.size() - 1);
        assertThat(testPoi.getPoiId()).isEqualTo(UPDATED_POI_ID);
        assertThat(testPoi.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPoi() throws Exception {
        int databaseSizeBeforeUpdate = poiRepository.findAll().size();

        // Create the Poi
        PoiDTO poiDTO = poiMapper.toDto(poi);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPoiMockMvc.perform(put("/api/pois")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poi in the database
        List<Poi> poiList = poiRepository.findAll();
        assertThat(poiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoi() throws Exception {
        // Initialize the database
        poiRepository.saveAndFlush(poi);

        int databaseSizeBeforeDelete = poiRepository.findAll().size();

        // Get the poi
        restPoiMockMvc.perform(delete("/api/pois/{id}", poi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Poi> poiList = poiRepository.findAll();
        assertThat(poiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poi.class);
        Poi poi1 = new Poi();
        poi1.setId(1L);
        Poi poi2 = new Poi();
        poi2.setId(poi1.getId());
        assertThat(poi1).isEqualTo(poi2);
        poi2.setId(2L);
        assertThat(poi1).isNotEqualTo(poi2);
        poi1.setId(null);
        assertThat(poi1).isNotEqualTo(poi2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoiDTO.class);
        PoiDTO poiDTO1 = new PoiDTO();
        poiDTO1.setId(1L);
        PoiDTO poiDTO2 = new PoiDTO();
        assertThat(poiDTO1).isNotEqualTo(poiDTO2);
        poiDTO2.setId(poiDTO1.getId());
        assertThat(poiDTO1).isEqualTo(poiDTO2);
        poiDTO2.setId(2L);
        assertThat(poiDTO1).isNotEqualTo(poiDTO2);
        poiDTO1.setId(null);
        assertThat(poiDTO1).isNotEqualTo(poiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(poiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(poiMapper.fromId(null)).isNull();
    }
}
