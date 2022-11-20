package cloud.nino.nino.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Privacy.
 */
@Entity
@Table(name = "privacy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Privacy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "privacy")
    private Boolean privacy;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Privacy time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Boolean isPrivacy() {
        return privacy;
    }

    public Privacy privacy(Boolean privacy) {
        this.privacy = privacy;
        return this;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    public User getUser() {
        return user;
    }

    public Privacy user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Privacy privacy = (Privacy) o;
        if (privacy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), privacy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Privacy{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", privacy='" + isPrivacy() + "'" +
            "}";
    }
}
