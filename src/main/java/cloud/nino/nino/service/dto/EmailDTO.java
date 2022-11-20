package cloud.nino.nino.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import cloud.nino.nino.domain.enumeration.EmailStatus;

/**
 * A DTO for the Email entity.
 */
public class EmailDTO implements Serializable {

    private Long id;

    private String from;

    private String to;

    private ZonedDateTime date;

    private String sesMessageId;

    private EmailStatus status;

    @Size(max = 20000)
    private String htmlBody;

    @Size(max = 20000)
    private String textBody;

    private String subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getSesMessageId() {
        return sesMessageId;
    }

    public void setSesMessageId(String sesMessageId) {
        this.sesMessageId = sesMessageId;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailDTO emailDTO = (EmailDTO) o;
        if (emailDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", date='" + getDate() + "'" +
            ", sesMessageId='" + getSesMessageId() + "'" +
            ", status='" + getStatus() + "'" +
            ", htmlBody='" + getHtmlBody() + "'" +
            ", textBody='" + getTextBody() + "'" +
            ", subject='" + getSubject() + "'" +
            "}";
    }
}
