package cloud.nino.nino.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AlberoVisit entity.
 */
public class AlberoVisitDTO implements Serializable {

    private Long id;

    private ZonedDateTime visitTime;

    private Long userId;

    private Long alberoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(ZonedDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAlberoId() {
        return alberoId;
    }

    public void setAlberoId(Long alberoId) {
        this.alberoId = alberoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlberoVisitDTO alberoVisitDTO = (AlberoVisitDTO) o;
        if (alberoVisitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alberoVisitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlberoVisitDTO{" +
            "id=" + getId() +
            ", visitTime='" + getVisitTime() + "'" +
            ", user=" + getUserId() +
            ", albero=" + getAlberoId() +
            "}";
    }
}
