package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.Privacy;
import cloud.nino.nino.repository.PrivacyRepository;
import cloud.nino.nino.service.PrivacyService;
import cloud.nino.nino.service.dto.PrivacyDTO;
import cloud.nino.nino.service.mapper.PrivacyMapper;
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
 * Test class for the PrivacyResource REST controller.
 *
 * @see PrivacyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class PrivacyResourceIntTest {

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_PRIVACY = false;
    private static final Boolean UPDATED_PRIVACY = true;

    @Autowired
    private PrivacyRepository privacyRepository;


    @Autowired
    private PrivacyMapper privacyMapper;
    

    @Autowired
    private PrivacyService privacyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrivacyMockMvc;

    private Privacy privacy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrivacyResource privacyResource = new PrivacyResource(privacyService);
        this.restPrivacyMockMvc = MockMvcBuilders.standaloneSetup(privacyResource)
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
    public static Privacy createEntity(EntityManager em) {
        Privacy privacy = new Privacy()
            .time(DEFAULT_TIME)
            .privacy(DEFAULT_PRIVACY);
        return privacy;
    }

    @Before
    public void initTest() {
        privacy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrivacy() throws Exception {
        int databaseSizeBeforeCreate = privacyRepository.findAll().size();

        // Create the Privacy
        PrivacyDTO privacyDTO = privacyMapper.toDto(privacy);
        restPrivacyMockMvc.perform(post("/api/privacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privacyDTO)))
            .andExpect(status().isCreated());

        // Validate the Privacy in the database
        List<Privacy> privacyList = privacyRepository.findAll();
        assertThat(privacyList).hasSize(databaseSizeBeforeCreate + 1);
        Privacy testPrivacy = privacyList.get(privacyList.size() - 1);
        assertThat(testPrivacy.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testPrivacy.isPrivacy()).isEqualTo(DEFAULT_PRIVACY);
    }

    @Test
    @Transactional
    public void createPrivacyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = privacyRepository.findAll().size();

        // Create the Privacy with an existing ID
        privacy.setId(1L);
        PrivacyDTO privacyDTO = privacyMapper.toDto(privacy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivacyMockMvc.perform(post("/api/privacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privacyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Privacy in the database
        List<Privacy> privacyList = privacyRepository.findAll();
        assertThat(privacyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrivacies() throws Exception {
        // Initialize the database
        privacyRepository.saveAndFlush(privacy);

        // Get all the privacyList
        restPrivacyMockMvc.perform(get("/api/privacies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privacy.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].privacy").value(hasItem(DEFAULT_PRIVACY.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getPrivacy() throws Exception {
        // Initialize the database
        privacyRepository.saveAndFlush(privacy);

        // Get the privacy
        restPrivacyMockMvc.perform(get("/api/privacies/{id}", privacy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(privacy.getId().intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.privacy").value(DEFAULT_PRIVACY.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPrivacy() throws Exception {
        // Get the privacy
        restPrivacyMockMvc.perform(get("/api/privacies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrivacy() throws Exception {
        // Initialize the database
        privacyRepository.saveAndFlush(privacy);

        int databaseSizeBeforeUpdate = privacyRepository.findAll().size();

        // Update the privacy
        Privacy updatedPrivacy = privacyRepository.findById(privacy.getId()).get();
        // Disconnect from session so that the updates on updatedPrivacy are not directly saved in db
        em.detach(updatedPrivacy);
        updatedPrivacy
            .time(UPDATED_TIME)
            .privacy(UPDATED_PRIVACY);
        PrivacyDTO privacyDTO = privacyMapper.toDto(updatedPrivacy);

        restPrivacyMockMvc.perform(put("/api/privacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privacyDTO)))
            .andExpect(status().isOk());

        // Validate the Privacy in the database
        List<Privacy> privacyList = privacyRepository.findAll();
        assertThat(privacyList).hasSize(databaseSizeBeforeUpdate);
        Privacy testPrivacy = privacyList.get(privacyList.size() - 1);
        assertThat(testPrivacy.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testPrivacy.isPrivacy()).isEqualTo(UPDATED_PRIVACY);
    }

    @Test
    @Transactional
    public void updateNonExistingPrivacy() throws Exception {
        int databaseSizeBeforeUpdate = privacyRepository.findAll().size();

        // Create the Privacy
        PrivacyDTO privacyDTO = privacyMapper.toDto(privacy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrivacyMockMvc.perform(put("/api/privacies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privacyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Privacy in the database
        List<Privacy> privacyList = privacyRepository.findAll();
        assertThat(privacyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrivacy() throws Exception {
        // Initialize the database
        privacyRepository.saveAndFlush(privacy);

        int databaseSizeBeforeDelete = privacyRepository.findAll().size();

        // Get the privacy
        restPrivacyMockMvc.perform(delete("/api/privacies/{id}", privacy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Privacy> privacyList = privacyRepository.findAll();
        assertThat(privacyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Privacy.class);
        Privacy privacy1 = new Privacy();
        privacy1.setId(1L);
        Privacy privacy2 = new Privacy();
        privacy2.setId(privacy1.getId());
        assertThat(privacy1).isEqualTo(privacy2);
        privacy2.setId(2L);
        assertThat(privacy1).isNotEqualTo(privacy2);
        privacy1.setId(null);
        assertThat(privacy1).isNotEqualTo(privacy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrivacyDTO.class);
        PrivacyDTO privacyDTO1 = new PrivacyDTO();
        privacyDTO1.setId(1L);
        PrivacyDTO privacyDTO2 = new PrivacyDTO();
        assertThat(privacyDTO1).isNotEqualTo(privacyDTO2);
        privacyDTO2.setId(privacyDTO1.getId());
        assertThat(privacyDTO1).isEqualTo(privacyDTO2);
        privacyDTO2.setId(2L);
        assertThat(privacyDTO1).isNotEqualTo(privacyDTO2);
        privacyDTO1.setId(null);
        assertThat(privacyDTO1).isNotEqualTo(privacyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(privacyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(privacyMapper.fromId(null)).isNull();
    }
}
