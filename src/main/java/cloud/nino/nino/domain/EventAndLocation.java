package cloud.nino.nino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EventAndLocation.
 */
@Entity
@Table(name = "event_and_location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventAndLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_and_location_admin_users",
               joinColumns = @JoinColumn(name = "event_and_locations_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "admin_users_id", referencedColumnName = "id"))
    private Set<NinoUser> adminUsers = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_and_location_pois",
               joinColumns = @JoinColumn(name = "event_and_locations_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "pois_id", referencedColumnName = "id"))
    private Set<Poi> pois = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EventAndLocation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NinoUser> getAdminUsers() {
        return adminUsers;
    }

    public EventAndLocation adminUsers(Set<NinoUser> ninoUsers) {
        this.adminUsers = ninoUsers;
        return this;
    }

    public EventAndLocation addAdminUsers(NinoUser ninoUser) {
        this.adminUsers.add(ninoUser);
        ninoUser.getAdministeredEventsAndLocations().add(this);
        return this;
    }

    public EventAndLocation removeAdminUsers(NinoUser ninoUser) {
        this.adminUsers.remove(ninoUser);
        ninoUser.getAdministeredEventsAndLocations().remove(this);
        return this;
    }

    public void setAdminUsers(Set<NinoUser> ninoUsers) {
        this.adminUsers = ninoUsers;
    }

    public Set<Poi> getPois() {
        return pois;
    }

    public EventAndLocation pois(Set<Poi> pois) {
        this.pois = pois;
        return this;
    }

    public EventAndLocation addPois(Poi poi) {
        this.pois.add(poi);
        poi.getEventsAndlocations().add(this);
        return this;
    }

    public EventAndLocation removePois(Poi poi) {
        this.pois.remove(poi);
        poi.getEventsAndlocations().remove(this);
        return this;
    }

    public void setPois(Set<Poi> pois) {
        this.pois = pois;
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
        EventAndLocation eventAndLocation = (EventAndLocation) o;
        if (eventAndLocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventAndLocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventAndLocation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
