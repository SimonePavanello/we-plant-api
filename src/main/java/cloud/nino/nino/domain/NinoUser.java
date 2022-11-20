package cloud.nino.nino.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A NinoUser.
 */
@Entity
@Table(name = "nino_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NinoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany(mappedBy = "adminUsers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EventAndLocation> administeredEventsAndLocations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public NinoUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<EventAndLocation> getAdministeredEventsAndLocations() {
        return administeredEventsAndLocations;
    }

    public NinoUser administeredEventsAndLocations(Set<EventAndLocation> eventAndLocations) {
        this.administeredEventsAndLocations = eventAndLocations;
        return this;
    }

    public NinoUser addAdministeredEventsAndLocations(EventAndLocation eventAndLocation) {
        this.administeredEventsAndLocations.add(eventAndLocation);
        eventAndLocation.getAdminUsers().add(this);
        return this;
    }

    public NinoUser removeAdministeredEventsAndLocations(EventAndLocation eventAndLocation) {
        this.administeredEventsAndLocations.remove(eventAndLocation);
        eventAndLocation.getAdminUsers().remove(this);
        return this;
    }

    public void setAdministeredEventsAndLocations(Set<EventAndLocation> eventAndLocations) {
        this.administeredEventsAndLocations = eventAndLocations;
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
        NinoUser ninoUser = (NinoUser) o;
        if (ninoUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ninoUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NinoUser{" +
            "id=" + getId() +
            "}";
    }
}
