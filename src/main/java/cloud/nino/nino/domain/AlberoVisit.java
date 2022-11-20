package cloud.nino.nino.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AlberoVisit.
 */
@Entity
@Table(name = "albero_visit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlberoVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "visit_time")
    private ZonedDateTime visitTime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Albero albero;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVisitTime() {
        return visitTime;
    }

    public AlberoVisit visitTime(ZonedDateTime visitTime) {
        this.visitTime = visitTime;
        return this;
    }

    public void setVisitTime(ZonedDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public User getUser() {
        return user;
    }

    public AlberoVisit user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Albero getAlbero() {
        return albero;
    }

    public AlberoVisit albero(Albero albero) {
        this.albero = albero;
        return this;
    }

    public void setAlbero(Albero albero) {
        this.albero = albero;
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
        AlberoVisit alberoVisit = (AlberoVisit) o;
        if (alberoVisit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alberoVisit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlberoVisit{" +
            "id=" + getId() +
            ", visitTime='" + getVisitTime() + "'" +
            "}";
    }
}
