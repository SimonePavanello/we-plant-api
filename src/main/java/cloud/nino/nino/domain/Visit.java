package cloud.nino.nino.domain;

import cloud.nino.nino.domain.enumeration.VisitDifficulty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Visit.
 */
@Entity
@Table(name = "visit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "last_lat", precision = 10, scale = 2)
    private BigDecimal lastLat;

    @Column(name = "last_lon", precision = 10, scale = 2)
    private BigDecimal lastLon;

    @Column(name = "exit_poi_id")
    private String exitPoiId;

    @Column(name = "max_visit_time")
    private Long maxVisitTime;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @Column(name = "max_visit_length_meters")
    private Double maxVisitLengthMeters;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "in_progress")
    private Boolean inProgress;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private VisitDifficulty difficulty;

    @OneToOne
    @JoinColumn(unique = true)
    private Stop startPoint;

    @OneToOne
    @JoinColumn(unique = true)
    private Stop endPoint;

    @OneToMany(mappedBy = "visit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stop> stops = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("")
    private EventAndLocation eventAndlocation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLastLat() {
        return lastLat;
    }

    public Visit lastLat(BigDecimal lastLat) {
        this.lastLat = lastLat;
        return this;
    }

    public void setLastLat(BigDecimal lastLat) {
        this.lastLat = lastLat;
    }

    public BigDecimal getLastLon() {
        return lastLon;
    }

    public Visit lastLon(BigDecimal lastLon) {
        this.lastLon = lastLon;
        return this;
    }

    public void setLastLon(BigDecimal lastLon) {
        this.lastLon = lastLon;
    }

    public String getExitPoiId() {
        return exitPoiId;
    }

    public Visit exitPoiId(String exitPoiId) {
        this.exitPoiId = exitPoiId;
        return this;
    }

    public void setExitPoiId(String exitPoiId) {
        this.exitPoiId = exitPoiId;
    }

    public Long getMaxVisitTime() {
        return maxVisitTime;
    }

    public Visit maxVisitTime(Long maxVisitTime) {
        this.maxVisitTime = maxVisitTime;
        return this;
    }

    public void setMaxVisitTime(Long maxVisitTime) {
        this.maxVisitTime = maxVisitTime;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Visit startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Visit createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Visit modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Double getMaxVisitLengthMeters() {
        return maxVisitLengthMeters;
    }

    public Visit maxVisitLengthMeters(Double maxVisitLengthMeters) {
        this.maxVisitLengthMeters = maxVisitLengthMeters;
        return this;
    }

    public void setMaxVisitLengthMeters(Double maxVisitLengthMeters) {
        this.maxVisitLengthMeters = maxVisitLengthMeters;
    }

    public Boolean isActive() {
        return active;
    }

    public Visit active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isInProgress() {
        return inProgress;
    }

    public Visit inProgress(Boolean inProgress) {
        this.inProgress = inProgress;
        return this;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public VisitDifficulty getDifficulty() {
        return difficulty;
    }

    public Visit difficulty(VisitDifficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(VisitDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Stop getStartPoint() {
        return startPoint;
    }

    public Visit startPoint(Stop stop) {
        this.startPoint = stop;
        return this;
    }

    public void setStartPoint(Stop stop) {
        this.startPoint = stop;
    }

    public Stop getEndPoint() {
        return endPoint;
    }

    public Visit endPoint(Stop stop) {
        this.endPoint = stop;
        return this;
    }

    public void setEndPoint(Stop stop) {
        this.endPoint = stop;
    }

    public Set<Stop> getStops() {
        return stops;
    }

    public Visit stops(Set<Stop> stops) {
        this.stops = stops;
        return this;
    }

    public Visit addStops(Stop stop) {
        this.stops.add(stop);
        stop.setVisit(this);
        return this;
    }

    public Visit removeStops(Stop stop) {
        this.stops.remove(stop);
        stop.setVisit(null);
        return this;
    }

    public void setStops(Set<Stop> stops) {
        this.stops = stops;
    }

    public User getUser() {
        return user;
    }

    public Visit user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EventAndLocation getEventAndlocation() {
        return eventAndlocation;
    }

    public Visit eventAndlocation(EventAndLocation eventAndLocation) {
        this.eventAndlocation = eventAndLocation;
        return this;
    }

    public void setEventAndlocation(EventAndLocation eventAndLocation) {
        this.eventAndlocation = eventAndLocation;
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
        Visit visit = (Visit) o;
        if (visit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Visit{" +
            "id=" + getId() +
            ", lastLat=" + getLastLat() +
            ", lastLon=" + getLastLon() +
            ", exitPoiId='" + getExitPoiId() + "'" +
            ", maxVisitTime=" + getMaxVisitTime() +
            ", startTime='" + getStartTime() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", maxVisitLengthMeters=" + getMaxVisitLengthMeters() +
            ", active='" + isActive() + "'" +
            ", inProgress='" + isInProgress() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
