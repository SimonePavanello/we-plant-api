package cloud.nino.nino.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EventAndLocation entity.
 */
public class EventAndLocationDTO implements Serializable {

    private Long id;

    private String name;

    private Set<NinoUserDTO> adminUsers = new HashSet<>();

    private Set<PoiDTO> pois = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NinoUserDTO> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(Set<NinoUserDTO> ninoUsers) {
        this.adminUsers = ninoUsers;
    }

    public Set<PoiDTO> getPois() {
        return pois;
    }

    public void setPois(Set<PoiDTO> pois) {
        this.pois = pois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventAndLocationDTO eventAndLocationDTO = (EventAndLocationDTO) o;
        if (eventAndLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventAndLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventAndLocationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
