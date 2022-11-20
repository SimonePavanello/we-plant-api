package cloud.nino.nino.web.rest;

import cloud.nino.nino.NinoApp;

import cloud.nino.nino.domain.Email;
import cloud.nino.nino.repository.EmailRepository;
import cloud.nino.nino.service.EmailService;
import cloud.nino.nino.service.dto.EmailDTO;
import cloud.nino.nino.service.mapper.EmailMapper;
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

import cloud.nino.nino.domain.enumeration.EmailStatus;
/**
 * Test class for the EmailResource REST controller.
 *
 * @see EmailResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NinoApp.class)
public class EmailResourceIntTest {

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TO = "AAAAAAAAAA";
    private static final String UPDATED_TO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SES_MESSAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SES_MESSAGE_ID = "BBBBBBBBBB";

    private static final EmailStatus DEFAULT_STATUS = EmailStatus.SUCCESS;
    private static final EmailStatus UPDATED_STATUS = EmailStatus.FAILED;

    private static final String DEFAULT_HTML_BODY = "AAAAAAAAAA";
    private static final String UPDATED_HTML_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    @Autowired
    private EmailRepository emailRepository;


    @Autowired
    private EmailMapper emailMapper;
    

    @Autowired
    private EmailService emailService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailMockMvc;

    private Email email;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailResource emailResource = new EmailResource(emailService);
        this.restEmailMockMvc = MockMvcBuilders.standaloneSetup(emailResource)
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
    public static Email createEntity(EntityManager em) {
        Email email = new Email()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .date(DEFAULT_DATE)
            .sesMessageId(DEFAULT_SES_MESSAGE_ID)
            .status(DEFAULT_STATUS)
            .htmlBody(DEFAULT_HTML_BODY)
            .textBody(DEFAULT_TEXT_BODY)
            .subject(DEFAULT_SUBJECT);
        return email;
    }

    @Before
    public void initTest() {
        email = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmail() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);
        restEmailMockMvc.perform(post("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isCreated());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate + 1);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testEmail.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testEmail.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEmail.getSesMessageId()).isEqualTo(DEFAULT_SES_MESSAGE_ID);
        assertThat(testEmail.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmail.getHtmlBody()).isEqualTo(DEFAULT_HTML_BODY);
        assertThat(testEmail.getTextBody()).isEqualTo(DEFAULT_TEXT_BODY);
        assertThat(testEmail.getSubject()).isEqualTo(DEFAULT_SUBJECT);
    }

    @Test
    @Transactional
    public void createEmailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailRepository.findAll().size();

        // Create the Email with an existing ID
        email.setId(1L);
        EmailDTO emailDTO = emailMapper.toDto(email);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailMockMvc.perform(post("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmails() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get all the emailList
        restEmailMockMvc.perform(get("/api/emails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(email.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].sesMessageId").value(hasItem(DEFAULT_SES_MESSAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].htmlBody").value(hasItem(DEFAULT_HTML_BODY.toString())))
            .andExpect(jsonPath("$.[*].textBody").value(hasItem(DEFAULT_TEXT_BODY.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())));
    }
    

    @Test
    @Transactional
    public void getEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", email.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(email.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.sesMessageId").value(DEFAULT_SES_MESSAGE_ID.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.htmlBody").value(DEFAULT_HTML_BODY.toString()))
            .andExpect(jsonPath("$.textBody").value(DEFAULT_TEXT_BODY.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmail() throws Exception {
        // Get the email
        restEmailMockMvc.perform(get("/api/emails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Update the email
        Email updatedEmail = emailRepository.findById(email.getId()).get();
        // Disconnect from session so that the updates on updatedEmail are not directly saved in db
        em.detach(updatedEmail);
        updatedEmail
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .date(UPDATED_DATE)
            .sesMessageId(UPDATED_SES_MESSAGE_ID)
            .status(UPDATED_STATUS)
            .htmlBody(UPDATED_HTML_BODY)
            .textBody(UPDATED_TEXT_BODY)
            .subject(UPDATED_SUBJECT);
        EmailDTO emailDTO = emailMapper.toDto(updatedEmail);

        restEmailMockMvc.perform(put("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isOk());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
        Email testEmail = emailList.get(emailList.size() - 1);
        assertThat(testEmail.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testEmail.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testEmail.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEmail.getSesMessageId()).isEqualTo(UPDATED_SES_MESSAGE_ID);
        assertThat(testEmail.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmail.getHtmlBody()).isEqualTo(UPDATED_HTML_BODY);
        assertThat(testEmail.getTextBody()).isEqualTo(UPDATED_TEXT_BODY);
        assertThat(testEmail.getSubject()).isEqualTo(UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmail() throws Exception {
        int databaseSizeBeforeUpdate = emailRepository.findAll().size();

        // Create the Email
        EmailDTO emailDTO = emailMapper.toDto(email);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmailMockMvc.perform(put("/api/emails")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Email in the database
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmail() throws Exception {
        // Initialize the database
        emailRepository.saveAndFlush(email);

        int databaseSizeBeforeDelete = emailRepository.findAll().size();

        // Get the email
        restEmailMockMvc.perform(delete("/api/emails/{id}", email.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Email> emailList = emailRepository.findAll();
        assertThat(emailList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Email.class);
        Email email1 = new Email();
        email1.setId(1L);
        Email email2 = new Email();
        email2.setId(email1.getId());
        assertThat(email1).isEqualTo(email2);
        email2.setId(2L);
        assertThat(email1).isNotEqualTo(email2);
        email1.setId(null);
        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailDTO.class);
        EmailDTO emailDTO1 = new EmailDTO();
        emailDTO1.setId(1L);
        EmailDTO emailDTO2 = new EmailDTO();
        assertThat(emailDTO1).isNotEqualTo(emailDTO2);
        emailDTO2.setId(emailDTO1.getId());
        assertThat(emailDTO1).isEqualTo(emailDTO2);
        emailDTO2.setId(2L);
        assertThat(emailDTO1).isNotEqualTo(emailDTO2);
        emailDTO1.setId(null);
        assertThat(emailDTO1).isNotEqualTo(emailDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailMapper.fromId(null)).isNull();
    }
}
