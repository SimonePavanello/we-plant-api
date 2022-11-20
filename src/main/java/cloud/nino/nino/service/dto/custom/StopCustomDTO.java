package cloud.nino.nino.service.dto.custom;

import cloud.nino.nino.domain.elasticsearch.Tags;
import cloud.nino.nino.service.dto.StopDTO;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the Stop entity.
 */
public class StopCustomDTO extends StopDTO implements Serializable {
    private BigDecimal lat;
    private BigDecimal lon;
    private Tags tags;

    public StopCustomDTO(StopDTO stopDTO) {
        super();
        BeanUtils.copyProperties(stopDTO, this);
        this.setReached(stopDTO.isReached());
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

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
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
            ", visit=" + getVisitId() +
            "}";
    }
}
