package cloud.nino.nino.service.custom;

import cloud.nino.nino.domain.Email;
import cloud.nino.nino.domain.enumeration.EmailStatus;
import cloud.nino.nino.repository.EmailRepository;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * Service Implementation for managing {@link Email}.
 */
@Service
public class CustomEmailService {
    private final Logger log = LoggerFactory.getLogger(CustomEmailService.class);

    final EmailRepository emailRepository;
    final AmazonSimpleEmailService amazonSimpleEmailService;
    public CustomEmailService(EmailRepository emailRepository, AmazonSimpleEmailService amazonSimpleEmailService) {
        this.emailRepository = emailRepository;
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }


    /**
     * Save a email.
     *
     * @param email the entity to save.
     * @return the persisted entity.
     */
    public Email send(Email email) {
        log.debug("Request to save Email : {}", email);
        email.setDate(ZonedDateTime.now());
        email.setHtmlBody(email.getHtmlBody() == null ? StringUtils.EMPTY : email.getHtmlBody());
        email.setSubject(email.getSubject() == null ? StringUtils.EMPTY : email.getSubject());
        email.setTextBody(email.getTextBody() == null ? StringUtils.EMPTY : email.getTextBody());
        try {
            SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                    new Destination().withToAddresses(email.getTo()))
                .withMessage(new Message()
                    .withBody(new Body()
                        .withHtml(new Content()
                            .withCharset("UTF-8").withData(email.getHtmlBody()))
                        .withText(new Content()
                            .withCharset("UTF-8").withData(email.getTextBody())))
                    .withSubject(new Content()
                        .withCharset("UTF-8").withData(email.getSubject())))
                .withSource(email.getFrom());
            // Comment or remove the next line if you are not using a
            // configuration set
            //.withConfigurationSetName(CONFIGSET);
            SendEmailResult sendEmailResult = this.amazonSimpleEmailService.sendEmail(request);
            email.setStatus(EmailStatus.SUCCESS);
            email.setSesMessageId(sendEmailResult.getMessageId());
            email = emailRepository.save(email);
            return email;
        } catch (Exception ex) {
            log.error("The email was not sent. Error message: ", ex);
            System.out.println("The email was not sent. Error message: "
                + ex.getMessage());
            email.setStatus(EmailStatus.FAILED);
            email = emailRepository.save(email);
            return email;
        }

    }
}
