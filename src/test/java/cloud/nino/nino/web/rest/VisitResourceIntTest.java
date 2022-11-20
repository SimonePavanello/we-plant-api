package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.Visit;
import cloud.nino.nino.repository.VisitRepository;
import cloud.nino.nino.service.VisitService;
import cloud.nino.nino.service.dto.VisitDTO;
import cloud.nino.nino.service.mapper.VisitMapper;
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
import java.math.BigDecimal;
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

import cloud.nino.nino.domain.enumeration.VisitDifficulty;
/**
 * Test class for the VisitResource REST controller.
 *
 * @see VisitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class VisitResourceIntTest {

    private static final BigDecimal DEFAULT_LAST_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LAST_LON = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_LON = new BigDecimal(2);

    private static final String DEFAULT_EXIT_POI_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXIT_POI_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_MAX_VISIT_TIME = 1L;
    private static final Long UPDATED_MAX_VISIT_TIME = 2L;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_MAX_VISIT_LENGTH_METERS = 1D;
    private static final Double UPDATED_MAX_VISIT_LENGTH_METERS = 2D;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_IN_PROGRESS = false;
    private static final Boolean UPDATED_IN_PROGRESS = true;

    private static final VisitDifficulty DEFAULT_DIFFICULTY = VisitDifficulty.LOW;
    private static final VisitDifficulty UPDATED_DIFFICULTY = VisitDifficulty.MEDIUM;

    @Autowired
    private VisitRepository visitRepository;


    @Autowired
    private VisitMapper visitMapper;
    

    @Autowired
    private VisitService visitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitMockMvc;

    private Visit visit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitResource visitResource = new VisitResource(visitService);
        this.restVisitMockMvc = MockMvcBuilders.standaloneSetup(visitResource)
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
    public static Visit createEntity(EntityManager em) {
        Visit visit = new Visit()
            .lastLat(DEFAULT_LAST_LAT)
            .lastLon(DEFAULT_LAST_LON)
            .exitPoiId(DEFAULT_EXIT_POI_ID)
            .maxVisitTime(DEFAULT_MAX_VISIT_TIME)
            .startTime(DEFAULT_START_TIME)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .maxVisitLengthMeters(DEFAULT_MAX_VISIT_LENGTH_METERS)
            .active(DEFAULT_ACTIVE)
            .inProgress(DEFAULT_IN_PROGRESS)
            .difficulty(DEFAULT_DIFFICULTY);
        return visit;
    }

    @Before
    public void initTest() {
        visit = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisit() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isCreated());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate + 1);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getLastLat()).isEqualTo(DEFAULT_LAST_LAT);
        assertThat(testVisit.getLastLon()).isEqualTo(DEFAULT_LAST_LON);
        assertThat(testVisit.getExitPoiId()).isEqualTo(DEFAULT_EXIT_POI_ID);
        assertThat(testVisit.getMaxVisitTime()).isEqualTo(DEFAULT_MAX_VISIT_TIME);
        assertThat(testVisit.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testVisit.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVisit.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testVisit.getMaxVisitLengthMeters()).isEqualTo(DEFAULT_MAX_VISIT_LENGTH_METERS);
        assertThat(testVisit.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVisit.isInProgress()).isEqualTo(DEFAULT_IN_PROGRESS);
        assertThat(testVisit.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
    }

    @Test
    @Transactional
    public void createVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // Create the Visit with an existing ID
        visit.setId(1L);
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVisits() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastLat").value(hasItem(DEFAULT_LAST_LAT.intValue())))
            .andExpect(jsonPath("$.[*].lastLon").value(hasItem(DEFAULT_LAST_LON.intValue())))
            .andExpect(jsonPath("$.[*].exitPoiId").value(hasItem(DEFAULT_EXIT_POI_ID.toString())))
            .andExpect(jsonPath("$.[*].maxVisitTime").value(hasItem(DEFAULT_MAX_VISIT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))))
            .andExpect(jsonPath("$.[*].maxVisitLengthMeters").value(hasItem(DEFAULT_MAX_VISIT_LENGTH_METERS.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].inProgress").value(hasItem(DEFAULT_IN_PROGRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())));
    }
    

    @Test
    @Transactional
    public void getVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", visit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visit.getId().intValue()))
            .andExpect(jsonPath("$.lastLat").value(DEFAULT_LAST_LAT.intValue()))
            .andExpect(jsonPath("$.lastLon").value(DEFAULT_LAST_LON.intValue()))
            .andExpect(jsonPath("$.exitPoiId").value(DEFAULT_EXIT_POI_ID.toString()))
            .andExpect(jsonPath("$.maxVisitTime").value(DEFAULT_MAX_VISIT_TIME.intValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)))
            .andExpect(jsonPath("$.maxVisitLengthMeters").value(DEFAULT_MAX_VISIT_LENGTH_METERS.doubleValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.inProgress").value(DEFAULT_IN_PROGRESS.booleanValue()))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVisit() throws Exception {
        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit
        Visit updatedVisit = visitRepository.findById(visit.getId()).get();
        // Disconnect from session so that the updates on updatedVisit are not directly saved in db
        em.detach(updatedVisit);
        updatedVisit
            .lastLat(UPDATED_LAST_LAT)
            .lastLon(UPDATED_LAST_LON)
            .exitPoiId(UPDATED_EXIT_POI_ID)
            .maxVisitTime(UPDATED_MAX_VISIT_TIME)
            .startTime(UPDATED_START_TIME)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .maxVisitLengthMeters(UPDATED_MAX_VISIT_LENGTH_METERS)
            .active(UPDATED_ACTIVE)
            .inProgress(UPDATED_IN_PROGRESS)
            .difficulty(UPDATED_DIFFICULTY);
        VisitDTO visitDTO = visitMapper.toDto(updatedVisit);

        restVisitMockMvc.perform(put("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getLastLat()).isEqualTo(UPDATED_LAST_LAT);
        assertThat(testVisit.getLastLon()).isEqualTo(UPDATED_LAST_LON);
        assertThat(testVisit.getExitPoiId()).isEqualTo(UPDATED_EXIT_POI_ID);
        assertThat(testVisit.getMaxVisitTime()).isEqualTo(UPDATED_MAX_VISIT_TIME);
        assertThat(testVisit.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testVisit.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVisit.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testVisit.getMaxVisitLengthMeters()).isEqualTo(UPDATED_MAX_VISIT_LENGTH_METERS);
        assertThat(testVisit.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVisit.isInProgress()).isEqualTo(UPDATED_IN_PROGRESS);
        assertThat(testVisit.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    @Transactional
    public void updateNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitMockMvc.perform(put("/api/visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeDelete = visitRepository.findAll().size();

        // Get the visit
        restVisitMockMvc.perform(delete("/api/visits/{id}", visit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visit.class);
        Visit visit1 = new Visit();
        visit1.setId(1L);
        Visit visit2 = new Visit();
        visit2.setId(visit1.getId());
        assertThat(visit1).isEqualTo(visit2);
        visit2.setId(2L);
        assertThat(visit1).isNotEqualTo(visit2);
        visit1.setId(null);
        assertThat(visit1).isNotEqualTo(visit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitDTO.class);
        VisitDTO visitDTO1 = new VisitDTO();
        visitDTO1.setId(1L);
        VisitDTO visitDTO2 = new VisitDTO();
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
        visitDTO2.setId(visitDTO1.getId());
        assertThat(visitDTO1).isEqualTo(visitDTO2);
        visitDTO2.setId(2L);
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
        visitDTO1.setId(null);
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(visitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(visitMapper.fromId(null)).isNull();
    }
}
