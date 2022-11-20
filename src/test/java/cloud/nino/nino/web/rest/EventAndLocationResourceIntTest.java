package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.EventAndLocation;
import cloud.nino.nino.repository.EventAndLocationRepository;
import cloud.nino.nino.service.EventAndLocationService;
import cloud.nino.nino.service.dto.EventAndLocationDTO;
import cloud.nino.nino.service.mapper.EventAndLocationMapper;
import cloud.nino.nino.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static cloud.nino.nino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EventAndLocationResource REST controller.
 *
 * @see EventAndLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class EventAndLocationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EventAndLocationRepository eventAndLocationRepository;
    @Mock
    private EventAndLocationRepository eventAndLocationRepositoryMock;

    @Autowired
    private EventAndLocationMapper eventAndLocationMapper;
    
    @Mock
    private EventAndLocationService eventAndLocationServiceMock;

    @Autowired
    private EventAndLocationService eventAndLocationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventAndLocationMockMvc;

    private EventAndLocation eventAndLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventAndLocationResource eventAndLocationResource = new EventAndLocationResource(eventAndLocationService);
        this.restEventAndLocationMockMvc = MockMvcBuilders.standaloneSetup(eventAndLocationResource)
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
    public static EventAndLocation createEntity(EntityManager em) {
        EventAndLocation eventAndLocation = new EventAndLocation()
            .name(DEFAULT_NAME);
        return eventAndLocation;
    }

    @Before
    public void initTest() {
        eventAndLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventAndLocation() throws Exception {
        int databaseSizeBeforeCreate = eventAndLocationRepository.findAll().size();

        // Create the EventAndLocation
        EventAndLocationDTO eventAndLocationDTO = eventAndLocationMapper.toDto(eventAndLocation);
        restEventAndLocationMockMvc.perform(post("/api/event-and-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventAndLocationDTO)))
            .andExpect(status().isCreated());

        // Validate the EventAndLocation in the database
        List<EventAndLocation> eventAndLocationList = eventAndLocationRepository.findAll();
        assertThat(eventAndLocationList).hasSize(databaseSizeBeforeCreate + 1);
        EventAndLocation testEventAndLocation = eventAndLocationList.get(eventAndLocationList.size() - 1);
        assertThat(testEventAndLocation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEventAndLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventAndLocationRepository.findAll().size();

        // Create the EventAndLocation with an existing ID
        eventAndLocation.setId(1L);
        EventAndLocationDTO eventAndLocationDTO = eventAndLocationMapper.toDto(eventAndLocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventAndLocationMockMvc.perform(post("/api/event-and-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventAndLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventAndLocation in the database
        List<EventAndLocation> eventAndLocationList = eventAndLocationRepository.findAll();
        assertThat(eventAndLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEventAndLocations() throws Exception {
        // Initialize the database
        eventAndLocationRepository.saveAndFlush(eventAndLocation);

        // Get all the eventAndLocationList
        restEventAndLocationMockMvc.perform(get("/api/event-and-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventAndLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    public void getAllEventAndLocationsWithEagerRelationshipsIsEnabled() throws Exception {
        EventAndLocationResource eventAndLocationResource = new EventAndLocationResource(eventAndLocationServiceMock);
        when(eventAndLocationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEventAndLocationMockMvc = MockMvcBuilders.standaloneSetup(eventAndLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventAndLocationMockMvc.perform(get("/api/event-and-locations?eagerload=true"))
        .andExpect(status().isOk());

        verify(eventAndLocationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllEventAndLocationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EventAndLocationResource eventAndLocationResource = new EventAndLocationResource(eventAndLocationServiceMock);
            when(eventAndLocationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEventAndLocationMockMvc = MockMvcBuilders.standaloneSetup(eventAndLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventAndLocationMockMvc.perform(get("/api/event-and-locations?eagerload=true"))
        .andExpect(status().isOk());

            verify(eventAndLocationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEventAndLocation() throws Exception {
        // Initialize the database
        eventAndLocationRepository.saveAndFlush(eventAndLocation);

        // Get the eventAndLocation
        restEventAndLocationMockMvc.perform(get("/api/event-and-locations/{id}", eventAndLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventAndLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEventAndLocation() throws Exception {
        // Get the eventAndLocation
        restEventAndLocationMockMvc.perform(get("/api/event-and-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventAndLocation() throws Exception {
        // Initialize the database
        eventAndLocationRepository.saveAndFlush(eventAndLocation);

        int databaseSizeBeforeUpdate = eventAndLocationRepository.findAll().size();

        // Update the eventAndLocation
        EventAndLocation updatedEventAndLocation = eventAndLocationRepository.findById(eventAndLocation.getId()).get();
        // Disconnect from session so that the updates on updatedEventAndLocation are not directly saved in db
        em.detach(updatedEventAndLocation);
        updatedEventAndLocation
            .name(UPDATED_NAME);
        EventAndLocationDTO eventAndLocationDTO = eventAndLocationMapper.toDto(updatedEventAndLocation);

        restEventAndLocationMockMvc.perform(put("/api/event-and-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventAndLocationDTO)))
            .andExpect(status().isOk());

        // Validate the EventAndLocation in the database
        List<EventAndLocation> eventAndLocationList = eventAndLocationRepository.findAll();
        assertThat(eventAndLocationList).hasSize(databaseSizeBeforeUpdate);
        EventAndLocation testEventAndLocation = eventAndLocationList.get(eventAndLocationList.size() - 1);
        assertThat(testEventAndLocation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEventAndLocation() throws Exception {
        int databaseSizeBeforeUpdate = eventAndLocationRepository.findAll().size();

        // Create the EventAndLocation
        EventAndLocationDTO eventAndLocationDTO = eventAndLocationMapper.toDto(eventAndLocation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventAndLocationMockMvc.perform(put("/api/event-and-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventAndLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventAndLocation in the database
        List<EventAndLocation> eventAndLocationList = eventAndLocationRepository.findAll();
        assertThat(eventAndLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventAndLocation() throws Exception {
        // Initialize the database
        eventAndLocationRepository.saveAndFlush(eventAndLocation);

        int databaseSizeBeforeDelete = eventAndLocationRepository.findAll().size();

        // Get the eventAndLocation
        restEventAndLocationMockMvc.perform(delete("/api/event-and-locations/{id}", eventAndLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EventAndLocation> eventAndLocationList = eventAndLocationRepository.findAll();
        assertThat(eventAndLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventAndLocation.class);
        EventAndLocation eventAndLocation1 = new EventAndLocation();
        eventAndLocation1.setId(1L);
        EventAndLocation eventAndLocation2 = new EventAndLocation();
        eventAndLocation2.setId(eventAndLocation1.getId());
        assertThat(eventAndLocation1).isEqualTo(eventAndLocation2);
        eventAndLocation2.setId(2L);
        assertThat(eventAndLocation1).isNotEqualTo(eventAndLocation2);
        eventAndLocation1.setId(null);
        assertThat(eventAndLocation1).isNotEqualTo(eventAndLocation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventAndLocationDTO.class);
        EventAndLocationDTO eventAndLocationDTO1 = new EventAndLocationDTO();
        eventAndLocationDTO1.setId(1L);
        EventAndLocationDTO eventAndLocationDTO2 = new EventAndLocationDTO();
        assertThat(eventAndLocationDTO1).isNotEqualTo(eventAndLocationDTO2);
        eventAndLocationDTO2.setId(eventAndLocationDTO1.getId());
        assertThat(eventAndLocationDTO1).isEqualTo(eventAndLocationDTO2);
        eventAndLocationDTO2.setId(2L);
        assertThat(eventAndLocationDTO1).isNotEqualTo(eventAndLocationDTO2);
        eventAndLocationDTO1.setId(null);
        assertThat(eventAndLocationDTO1).isNotEqualTo(eventAndLocationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventAndLocationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventAndLocationMapper.fromId(null)).isNull();
    }
}
