package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.Stop;
import cloud.nino.nino.repository.StopRepository;
import cloud.nino.nino.service.StopService;
import cloud.nino.nino.service.dto.StopDTO;
import cloud.nino.nino.service.mapper.StopMapper;
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

import cloud.nino.nino.domain.enumeration.StopType;
/**
 * Test class for the StopResource REST controller.
 *
 * @see StopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class StopResourceIntTest {

    private static final String DEFAULT_POI_ID = "AAAAAAAAAA";
    private static final String UPDATED_POI_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REACHED = false;
    private static final Boolean UPDATED_REACHED = true;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final StopType DEFAULT_STOP_TYPE = StopType.REGULAR;
    private static final StopType UPDATED_STOP_TYPE = StopType.URGENT;

    private static final BigDecimal DEFAULT_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LON = new BigDecimal(1);
    private static final BigDecimal UPDATED_LON = new BigDecimal(2);

    @Autowired
    private StopRepository stopRepository;


    @Autowired
    private StopMapper stopMapper;
    

    @Autowired
    private StopService stopService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStopMockMvc;

    private Stop stop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StopResource stopResource = new StopResource(stopService);
        this.restStopMockMvc = MockMvcBuilders.standaloneSetup(stopResource)
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
    public static Stop createEntity(EntityManager em) {
        Stop stop = new Stop()
            .poiId(DEFAULT_POI_ID)
            .reached(DEFAULT_REACHED)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .stopType(DEFAULT_STOP_TYPE)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON);
        return stop;
    }

    @Before
    public void initTest() {
        stop = createEntity(em);
    }

    @Test
    @Transactional
    public void createStop() throws Exception {
        int databaseSizeBeforeCreate = stopRepository.findAll().size();

        // Create the Stop
        StopDTO stopDTO = stopMapper.toDto(stop);
        restStopMockMvc.perform(post("/api/stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stopDTO)))
            .andExpect(status().isCreated());

        // Validate the Stop in the database
        List<Stop> stopList = stopRepository.findAll();
        assertThat(stopList).hasSize(databaseSizeBeforeCreate + 1);
        Stop testStop = stopList.get(stopList.size() - 1);
        assertThat(testStop.getPoiId()).isEqualTo(DEFAULT_POI_ID);
        assertThat(testStop.isReached()).isEqualTo(DEFAULT_REACHED);
        assertThat(testStop.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testStop.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testStop.getStopType()).isEqualTo(DEFAULT_STOP_TYPE);
        assertThat(testStop.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testStop.getLon()).isEqualTo(DEFAULT_LON);
    }

    @Test
    @Transactional
    public void createStopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stopRepository.findAll().size();

        // Create the Stop with an existing ID
        stop.setId(1L);
        StopDTO stopDTO = stopMapper.toDto(stop);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStopMockMvc.perform(post("/api/stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stop in the database
        List<Stop> stopList = stopRepository.findAll();
        assertThat(stopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReachedIsRequired() throws Exception {
        int databaseSizeBeforeTest = stopRepository.findAll().size();
        // set the field null
        stop.setReached(null);

        // Create the Stop, which fails.
        StopDTO stopDTO = stopMapper.toDto(stop);

        restStopMockMvc.perform(post("/api/stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stopDTO)))
            .andExpect(status().isBadRequest());

        List<Stop> stopList = stopRepository.findAll();
        assertThat(stopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStopTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stopRepository.findAll().size();
        // set the field null
        stop.setStopType(null);

        // Create the Stop, which fails.
        StopDTO stopDTO = stopMapper.toDto(stop);

        restStopMockMvc.perform(post("/api/stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stopDTO)))
            .andExpect(status().isBadRequest());

        List<Stop> stopList = stopRepository.findAll();
        assertThat(stopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStops() throws Exception {
        // Initialize the database
        stopRepository.saveAndFlush(stop);

        // Get all the stopList
        restStopMockMvc.perform(get("/api/stops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stop.getId().intValue())))
            .andExpect(jsonPath("$.[*].poiId").value(hasItem(DEFAULT_POI_ID.toString())))
            .andExpect(jsonPath("$.[*].reached").value(hasItem(DEFAULT_REACHED.booleanValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].stopType").value(hasItem(DEFAULT_STOP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.intValue())));
    }
    

    @Test
    @Transactional
    public void getStop() throws Exception {
        // Initialize the database
        stopRepository.saveAndFlush(stop);

        // Get the stop
        restStopMockMvc.perform(get("/api/stops/{id}", stop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stop.getId().intValue()))
            .andExpect(jsonPath("$.poiId").value(DEFAULT_POI_ID.toString()))
            .andExpect(jsonPath("$.reached").value(DEFAULT_REACHED.booleanValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.stopType").value(DEFAULT_STOP_TYPE.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.intValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStop() throws Exception {
        // Get the stop
        restStopMockMvc.perform(get("/api/stops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStop() throws Exception {
        // Initialize the database
        stopRepository.saveAndFlush(stop);

        int databaseSizeBeforeUpdate = stopRepository.findAll().size();

        // Update the stop
        Stop updatedStop = stopRepository.findById(stop.getId()).get();
        // Disconnect from session so that the updates on updatedStop are not directly saved in db
        em.detach(updatedStop);
        updatedStop
            .poiId(UPDATED_POI_ID)
            .reached(UPDATED_REACHED)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .stopType(UPDATED_STOP_TYPE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON);
        StopDTO stopDTO = stopMapper.toDto(updatedStop);

        restStopMockMvc.perform(put("/api/stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stopDTO)))
            .andExpect(status().isOk());

        // Validate the Stop in the database
        List<Stop> stopList = stopRepository.findAll();
        assertThat(stopList).hasSize(databaseSizeBeforeUpdate);
        Stop testStop = stopList.get(stopList.size() - 1);
        assertThat(testStop.getPoiId()).isEqualTo(UPDATED_POI_ID);
        assertThat(testStop.isReached()).isEqualTo(UPDATED_REACHED);
        assertThat(testStop.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testStop.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testStop.getStopType()).isEqualTo(UPDATED_STOP_TYPE);
        assertThat(testStop.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testStop.getLon()).isEqualTo(UPDATED_LON);
    }

    @Test
    @Transactional
    public void updateNonExistingStop() throws Exception {
        int databaseSizeBeforeUpdate = stopRepository.findAll().size();

        // Create the Stop
        StopDTO stopDTO = stopMapper.toDto(stop);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStopMockMvc.perform(put("/api/stops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stopDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stop in the database
        List<Stop> stopList = stopRepository.findAll();
        assertThat(stopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStop() throws Exception {
        // Initialize the database
        stopRepository.saveAndFlush(stop);

        int databaseSizeBeforeDelete = stopRepository.findAll().size();

        // Get the stop
        restStopMockMvc.perform(delete("/api/stops/{id}", stop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stop> stopList = stopRepository.findAll();
        assertThat(stopList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stop.class);
        Stop stop1 = new Stop();
        stop1.setId(1L);
        Stop stop2 = new Stop();
        stop2.setId(stop1.getId());
        assertThat(stop1).isEqualTo(stop2);
        stop2.setId(2L);
        assertThat(stop1).isNotEqualTo(stop2);
        stop1.setId(null);
        assertThat(stop1).isNotEqualTo(stop2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StopDTO.class);
        StopDTO stopDTO1 = new StopDTO();
        stopDTO1.setId(1L);
        StopDTO stopDTO2 = new StopDTO();
        assertThat(stopDTO1).isNotEqualTo(stopDTO2);
        stopDTO2.setId(stopDTO1.getId());
        assertThat(stopDTO1).isEqualTo(stopDTO2);
        stopDTO2.setId(2L);
        assertThat(stopDTO1).isNotEqualTo(stopDTO2);
        stopDTO1.setId(null);
        assertThat(stopDTO1).isNotEqualTo(stopDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stopMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stopMapper.fromId(null)).isNull();
    }
}
