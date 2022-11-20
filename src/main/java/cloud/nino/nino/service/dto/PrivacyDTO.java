package cloud.nino.nino.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Privacy entity.
 */
public class PrivacyDTO implements Serializable {

    private Long id;

    private ZonedDateTime time;

    private Boolean privacy;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrivacyDTO privacyDTO = (PrivacyDTO) o;
        if (privacyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), privacyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrivacyDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", privacy='" + isPrivacy() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
