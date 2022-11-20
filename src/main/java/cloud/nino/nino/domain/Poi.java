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
 * A Poi.
 */
@Entity
@Table(name = "poi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Poi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "poi_id")
    private String poiId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "pois")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EventAndLocation> eventsAndlocations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoiId() {
        return poiId;
    }

    public Poi poiId(String poiId) {
        this.poiId = poiId;
        return this;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getName() {
        return name;
    }

    public Poi name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EventAndLocation> getEventsAndlocations() {
        return eventsAndlocations;
    }

    public Poi eventsAndlocations(Set<EventAndLocation> eventAndLocations) {
        this.eventsAndlocations = eventAndLocations;
        return this;
    }

    public Poi addEventsAndlocations(EventAndLocation eventAndLocation) {
        this.eventsAndlocations.add(eventAndLocation);
        eventAndLocation.getPois().add(this);
        return this;
    }

    public Poi removeEventsAndlocations(EventAndLocation eventAndLocation) {
        this.eventsAndlocations.remove(eventAndLocation);
        eventAndLocation.getPois().remove(this);
        return this;
    }

    public void setEventsAndlocations(Set<EventAndLocation> eventAndLocations) {
        this.eventsAndlocations = eventAndLocations;
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
        Poi poi = (Poi) o;
        if (poi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Poi{" +
            "id=" + getId() +
            ", poiId='" + getPoiId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
