package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.AlberoVisit;
import cloud.nino.nino.repository.AlberoVisitRepository;
import cloud.nino.nino.service.AlberoVisitService;
import cloud.nino.nino.service.dto.AlberoVisitDTO;
import cloud.nino.nino.service.mapper.AlberoVisitMapper;
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
 * Test class for the AlberoVisitResource REST controller.
 *
 * @see AlberoVisitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class AlberoVisitResourceIntTest {

    private static final ZonedDateTime DEFAULT_VISIT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_VISIT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AlberoVisitRepository alberoVisitRepository;


    @Autowired
    private AlberoVisitMapper alberoVisitMapper;
    

    @Autowired
    private AlberoVisitService alberoVisitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlberoVisitMockMvc;

    private AlberoVisit alberoVisit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlberoVisitResource alberoVisitResource = new AlberoVisitResource(alberoVisitService);
        this.restAlberoVisitMockMvc = MockMvcBuilders.standaloneSetup(alberoVisitResource)
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
    public static AlberoVisit createEntity(EntityManager em) {
        AlberoVisit alberoVisit = new AlberoVisit()
            .visitTime(DEFAULT_VISIT_TIME);
        return alberoVisit;
    }

    @Before
    public void initTest() {
        alberoVisit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlberoVisit() throws Exception {
        int databaseSizeBeforeCreate = alberoVisitRepository.findAll().size();

        // Create the AlberoVisit
        AlberoVisitDTO alberoVisitDTO = alberoVisitMapper.toDto(alberoVisit);
        restAlberoVisitMockMvc.perform(post("/api/albero-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the AlberoVisit in the database
        List<AlberoVisit> alberoVisitList = alberoVisitRepository.findAll();
        assertThat(alberoVisitList).hasSize(databaseSizeBeforeCreate + 1);
        AlberoVisit testAlberoVisit = alberoVisitList.get(alberoVisitList.size() - 1);
        assertThat(testAlberoVisit.getVisitTime()).isEqualTo(DEFAULT_VISIT_TIME);
    }

    @Test
    @Transactional
    public void createAlberoVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alberoVisitRepository.findAll().size();

        // Create the AlberoVisit with an existing ID
        alberoVisit.setId(1L);
        AlberoVisitDTO alberoVisitDTO = alberoVisitMapper.toDto(alberoVisit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlberoVisitMockMvc.perform(post("/api/albero-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlberoVisit in the database
        List<AlberoVisit> alberoVisitList = alberoVisitRepository.findAll();
        assertThat(alberoVisitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlberoVisits() throws Exception {
        // Initialize the database
        alberoVisitRepository.saveAndFlush(alberoVisit);

        // Get all the alberoVisitList
        restAlberoVisitMockMvc.perform(get("/api/albero-visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alberoVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].visitTime").value(hasItem(sameInstant(DEFAULT_VISIT_TIME))));
    }
    

    @Test
    @Transactional
    public void getAlberoVisit() throws Exception {
        // Initialize the database
        alberoVisitRepository.saveAndFlush(alberoVisit);

        // Get the alberoVisit
        restAlberoVisitMockMvc.perform(get("/api/albero-visits/{id}", alberoVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alberoVisit.getId().intValue()))
            .andExpect(jsonPath("$.visitTime").value(sameInstant(DEFAULT_VISIT_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingAlberoVisit() throws Exception {
        // Get the alberoVisit
        restAlberoVisitMockMvc.perform(get("/api/albero-visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlberoVisit() throws Exception {
        // Initialize the database
        alberoVisitRepository.saveAndFlush(alberoVisit);

        int databaseSizeBeforeUpdate = alberoVisitRepository.findAll().size();

        // Update the alberoVisit
        AlberoVisit updatedAlberoVisit = alberoVisitRepository.findById(alberoVisit.getId()).get();
        // Disconnect from session so that the updates on updatedAlberoVisit are not directly saved in db
        em.detach(updatedAlberoVisit);
        updatedAlberoVisit
            .visitTime(UPDATED_VISIT_TIME);
        AlberoVisitDTO alberoVisitDTO = alberoVisitMapper.toDto(updatedAlberoVisit);

        restAlberoVisitMockMvc.perform(put("/api/albero-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoVisitDTO)))
            .andExpect(status().isOk());

        // Validate the AlberoVisit in the database
        List<AlberoVisit> alberoVisitList = alberoVisitRepository.findAll();
        assertThat(alberoVisitList).hasSize(databaseSizeBeforeUpdate);
        AlberoVisit testAlberoVisit = alberoVisitList.get(alberoVisitList.size() - 1);
        assertThat(testAlberoVisit.getVisitTime()).isEqualTo(UPDATED_VISIT_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingAlberoVisit() throws Exception {
        int databaseSizeBeforeUpdate = alberoVisitRepository.findAll().size();

        // Create the AlberoVisit
        AlberoVisitDTO alberoVisitDTO = alberoVisitMapper.toDto(alberoVisit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlberoVisitMockMvc.perform(put("/api/albero-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alberoVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlberoVisit in the database
        List<AlberoVisit> alberoVisitList = alberoVisitRepository.findAll();
        assertThat(alberoVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlberoVisit() throws Exception {
        // Initialize the database
        alberoVisitRepository.saveAndFlush(alberoVisit);

        int databaseSizeBeforeDelete = alberoVisitRepository.findAll().size();

        // Get the alberoVisit
        restAlberoVisitMockMvc.perform(delete("/api/albero-visits/{id}", alberoVisit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlberoVisit> alberoVisitList = alberoVisitRepository.findAll();
        assertThat(alberoVisitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlberoVisit.class);
        AlberoVisit alberoVisit1 = new AlberoVisit();
        alberoVisit1.setId(1L);
        AlberoVisit alberoVisit2 = new AlberoVisit();
        alberoVisit2.setId(alberoVisit1.getId());
        assertThat(alberoVisit1).isEqualTo(alberoVisit2);
        alberoVisit2.setId(2L);
        assertThat(alberoVisit1).isNotEqualTo(alberoVisit2);
        alberoVisit1.setId(null);
        assertThat(alberoVisit1).isNotEqualTo(alberoVisit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlberoVisitDTO.class);
        AlberoVisitDTO alberoVisitDTO1 = new AlberoVisitDTO();
        alberoVisitDTO1.setId(1L);
        AlberoVisitDTO alberoVisitDTO2 = new AlberoVisitDTO();
        assertThat(alberoVisitDTO1).isNotEqualTo(alberoVisitDTO2);
        alberoVisitDTO2.setId(alberoVisitDTO1.getId());
        assertThat(alberoVisitDTO1).isEqualTo(alberoVisitDTO2);
        alberoVisitDTO2.setId(2L);
        assertThat(alberoVisitDTO1).isNotEqualTo(alberoVisitDTO2);
        alberoVisitDTO1.setId(null);
        assertThat(alberoVisitDTO1).isNotEqualTo(alberoVisitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alberoVisitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alberoVisitMapper.fromId(null)).isNull();
    }
}
