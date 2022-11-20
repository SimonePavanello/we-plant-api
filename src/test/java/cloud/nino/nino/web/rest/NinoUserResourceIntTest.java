package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.NinoUser;
import cloud.nino.nino.repository.NinoUserRepository;
import cloud.nino.nino.service.NinoUserService;
import cloud.nino.nino.service.dto.NinoUserDTO;
import cloud.nino.nino.service.mapper.NinoUserMapper;
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
 * Test class for the NinoUserResource REST controller.
 *
 * @see NinoUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class NinoUserResourceIntTest {

    @Autowired
    private NinoUserRepository ninoUserRepository;


    @Autowired
    private NinoUserMapper ninoUserMapper;
    

    @Autowired
    private NinoUserService ninoUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNinoUserMockMvc;

    private NinoUser ninoUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NinoUserResource ninoUserResource = new NinoUserResource(ninoUserService);
        this.restNinoUserMockMvc = MockMvcBuilders.standaloneSetup(ninoUserResource)
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
    public static NinoUser createEntity(EntityManager em) {
        NinoUser ninoUser = new NinoUser();
        return ninoUser;
    }

    @Before
    public void initTest() {
        ninoUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createNinoUser() throws Exception {
        int databaseSizeBeforeCreate = ninoUserRepository.findAll().size();

        // Create the NinoUser
        NinoUserDTO ninoUserDTO = ninoUserMapper.toDto(ninoUser);
        restNinoUserMockMvc.perform(post("/api/nino-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ninoUserDTO)))
            .andExpect(status().isCreated());

        // Validate the NinoUser in the database
        List<NinoUser> ninoUserList = ninoUserRepository.findAll();
        assertThat(ninoUserList).hasSize(databaseSizeBeforeCreate + 1);
        NinoUser testNinoUser = ninoUserList.get(ninoUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createNinoUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ninoUserRepository.findAll().size();

        // Create the NinoUser with an existing ID
        ninoUser.setId(1L);
        NinoUserDTO ninoUserDTO = ninoUserMapper.toDto(ninoUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNinoUserMockMvc.perform(post("/api/nino-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ninoUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NinoUser in the database
        List<NinoUser> ninoUserList = ninoUserRepository.findAll();
        assertThat(ninoUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNinoUsers() throws Exception {
        // Initialize the database
        ninoUserRepository.saveAndFlush(ninoUser);

        // Get all the ninoUserList
        restNinoUserMockMvc.perform(get("/api/nino-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ninoUser.getId().intValue())));
    }
    

    @Test
    @Transactional
    public void getNinoUser() throws Exception {
        // Initialize the database
        ninoUserRepository.saveAndFlush(ninoUser);

        // Get the ninoUser
        restNinoUserMockMvc.perform(get("/api/nino-users/{id}", ninoUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ninoUser.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingNinoUser() throws Exception {
        // Get the ninoUser
        restNinoUserMockMvc.perform(get("/api/nino-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNinoUser() throws Exception {
        // Initialize the database
        ninoUserRepository.saveAndFlush(ninoUser);

        int databaseSizeBeforeUpdate = ninoUserRepository.findAll().size();

        // Update the ninoUser
        NinoUser updatedNinoUser = ninoUserRepository.findById(ninoUser.getId()).get();
        // Disconnect from session so that the updates on updatedNinoUser are not directly saved in db
        em.detach(updatedNinoUser);
        NinoUserDTO ninoUserDTO = ninoUserMapper.toDto(updatedNinoUser);

        restNinoUserMockMvc.perform(put("/api/nino-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ninoUserDTO)))
            .andExpect(status().isOk());

        // Validate the NinoUser in the database
        List<NinoUser> ninoUserList = ninoUserRepository.findAll();
        assertThat(ninoUserList).hasSize(databaseSizeBeforeUpdate);
        NinoUser testNinoUser = ninoUserList.get(ninoUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingNinoUser() throws Exception {
        int databaseSizeBeforeUpdate = ninoUserRepository.findAll().size();

        // Create the NinoUser
        NinoUserDTO ninoUserDTO = ninoUserMapper.toDto(ninoUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNinoUserMockMvc.perform(put("/api/nino-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ninoUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NinoUser in the database
        List<NinoUser> ninoUserList = ninoUserRepository.findAll();
        assertThat(ninoUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNinoUser() throws Exception {
        // Initialize the database
        ninoUserRepository.saveAndFlush(ninoUser);

        int databaseSizeBeforeDelete = ninoUserRepository.findAll().size();

        // Get the ninoUser
        restNinoUserMockMvc.perform(delete("/api/nino-users/{id}", ninoUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NinoUser> ninoUserList = ninoUserRepository.findAll();
        assertThat(ninoUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NinoUser.class);
        NinoUser ninoUser1 = new NinoUser();
        ninoUser1.setId(1L);
        NinoUser ninoUser2 = new NinoUser();
        ninoUser2.setId(ninoUser1.getId());
        assertThat(ninoUser1).isEqualTo(ninoUser2);
        ninoUser2.setId(2L);
        assertThat(ninoUser1).isNotEqualTo(ninoUser2);
        ninoUser1.setId(null);
        assertThat(ninoUser1).isNotEqualTo(ninoUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NinoUserDTO.class);
        NinoUserDTO ninoUserDTO1 = new NinoUserDTO();
        ninoUserDTO1.setId(1L);
        NinoUserDTO ninoUserDTO2 = new NinoUserDTO();
        assertThat(ninoUserDTO1).isNotEqualTo(ninoUserDTO2);
        ninoUserDTO2.setId(ninoUserDTO1.getId());
        assertThat(ninoUserDTO1).isEqualTo(ninoUserDTO2);
        ninoUserDTO2.setId(2L);
        assertThat(ninoUserDTO1).isNotEqualTo(ninoUserDTO2);
        ninoUserDTO1.setId(null);
        assertThat(ninoUserDTO1).isNotEqualTo(ninoUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ninoUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ninoUserMapper.fromId(null)).isNull();
    }
}
