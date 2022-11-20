package cloud.nino.nino.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import cloud.nino.nino.domain.enumeration.VisitDifficulty;

/**
 * A DTO for the Visit entity.
 */
public class VisitDTO implements Serializable {

    private Long id;

    private BigDecimal lastLat;

    private BigDecimal lastLon;

    private String exitPoiId;

    private Long maxVisitTime;

    private ZonedDateTime startTime;

    private ZonedDateTime createdDate;

    private ZonedDateTime modifiedDate;

    private Double maxVisitLengthMeters;

    private Boolean active;

    private Boolean inProgress;

    private VisitDifficulty difficulty;

    private Long startPointId;

    private Long endPointId;

    private Long userId;

    private Long eventAndlocationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLastLat() {
        return lastLat;
    }

    public void setLastLat(BigDecimal lastLat) {
        this.lastLat = lastLat;
    }

    public BigDecimal getLastLon() {
        return lastLon;
    }

    public void setLastLon(BigDecimal lastLon) {
        this.lastLon = lastLon;
    }

    public String getExitPoiId() {
        return exitPoiId;
    }

    public void setExitPoiId(String exitPoiId) {
        this.exitPoiId = exitPoiId;
    }

    public Long getMaxVisitTime() {
        return maxVisitTime;
    }

    public void setMaxVisitTime(Long maxVisitTime) {
        this.maxVisitTime = maxVisitTime;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Double getMaxVisitLengthMeters() {
        return maxVisitLengthMeters;
    }

    public void setMaxVisitLengthMeters(Double maxVisitLengthMeters) {
        this.maxVisitLengthMeters = maxVisitLengthMeters;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public VisitDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(VisitDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Long getStartPointId() {
        return startPointId;
    }

    public void setStartPointId(Long stopId) {
        this.startPointId = stopId;
    }

    public Long getEndPointId() {
        return endPointId;
    }

    public void setEndPointId(Long stopId) {
        this.endPointId = stopId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventAndlocationId() {
        return eventAndlocationId;
    }

    public void setEventAndlocationId(Long eventAndLocationId) {
        this.eventAndlocationId = eventAndLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VisitDTO visitDTO = (VisitDTO) o;
        if (visitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VisitDTO{" +
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
            ", startPoint=" + getStartPointId() +
            ", endPoint=" + getEndPointId() +
            ", user=" + getUserId() +
            ", eventAndlocation=" + getEventAndlocationId() +
            "}";
    }
}
