package cloud.nino.nino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import cloud.nino.nino.domain.enumeration.EmailStatus;

/**
 * A Email.
 */
@Entity
@Table(name = "email")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_from")
    private String from;

    @Column(name = "jhi_to")
    private String to;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "ses_message_id")
    private String sesMessageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmailStatus status;

    @Size(max = 20000)
    @Column(name = "html_body", length = 20000)
    private String htmlBody;

    @Size(max = 20000)
    @Column(name = "text_body", length = 20000)
    private String textBody;

    @Column(name = "subject")
    private String subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public Email from(String from) {
        this.from = from;
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public Email to(String to) {
        this.to = to;
        return this;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Email date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getSesMessageId() {
        return sesMessageId;
    }

    public Email sesMessageId(String sesMessageId) {
        this.sesMessageId = sesMessageId;
        return this;
    }

    public void setSesMessageId(String sesMessageId) {
        this.sesMessageId = sesMessageId;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public Email status(EmailStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public Email htmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
        return this;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }

    public String getTextBody() {
        return textBody;
    }

    public Email textBody(String textBody) {
        this.textBody = textBody;
        return this;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getSubject() {
        return subject;
    }

    public Email subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        if (email.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), email.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Email{" +
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
