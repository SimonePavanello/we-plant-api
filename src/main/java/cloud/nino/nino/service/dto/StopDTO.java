package cloud.nino.nino.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import cloud.nino.nino.domain.enumeration.StopType;

/**
 * A DTO for the Stop entity.
 */
public class StopDTO implements Serializable {

    private Long id;

    private String poiId;

    @NotNull
    private Boolean reached;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    @NotNull
    private StopType stopType;

    private BigDecimal lat;

    private BigDecimal lon;

    private Long visitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public Boolean isReached() {
        return reached;
    }

    public void setReached(Boolean reached) {
        this.reached = reached;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public StopType getStopType() {
        return stopType;
    }

    public void setStopType(StopType stopType) {
        this.stopType = stopType;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
    }

    public Long getVisitId() {
        return visitId;
    }

    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StopDTO stopDTO = (StopDTO) o;
        if (stopDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stopDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StopDTO{" +
            "id=" + getId() +
            ", poiId='" + getPoiId() + "'" +
            ", reached='" + isReached() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", stopType='" + getStopType() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", visit=" + getVisitId() +
            "}";
    }
}
