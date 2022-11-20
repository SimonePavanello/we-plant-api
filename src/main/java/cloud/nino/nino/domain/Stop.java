package cloud.nino.nino.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import cloud.nino.nino.domain.enumeration.StopType;

/**
 * A Stop.
 */
@Entity
@Table(name = "stop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "poi_id")
    private String poiId;

    @NotNull
    @Column(name = "reached", nullable = false)
    private Boolean reached;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "stop_type", nullable = false)
    private StopType stopType;

    @Column(name = "lat", precision = 10, scale = 2)
    private BigDecimal lat;

    @Column(name = "lon", precision = 10, scale = 2)
    private BigDecimal lon;

    @ManyToOne
    @JsonIgnoreProperties("stops")
    private Visit visit;

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

    public Stop poiId(String poiId) {
        this.poiId = poiId;
        return this;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public Boolean isReached() {
        return reached;
    }

    public Stop reached(Boolean reached) {
        this.reached = reached;
        return this;
    }

    public void setReached(Boolean reached) {
        this.reached = reached;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public Stop startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Stop endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public StopType getStopType() {
        return stopType;
    }

    public Stop stopType(StopType stopType) {
        this.stopType = stopType;
        return this;
    }

    public void setStopType(StopType stopType) {
        this.stopType = stopType;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public Stop lat(BigDecimal lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public Stop lon(BigDecimal lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public Visit getVisit() {
        return visit;
    }

    public Stop visit(Visit visit) {
        this.visit = visit;
        return this;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
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
        Stop stop = (Stop) o;
        if (stop.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stop.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stop{" +
            "id=" + getId() +
            ", poiId='" + getPoiId() + "'" +
            ", reached='" + isReached() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", stopType='" + getStopType() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            "}";
    }
}
