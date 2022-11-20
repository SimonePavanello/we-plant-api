package cloud.nino.nino.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Poi entity.
 */
public class PoiDTO implements Serializable {

    private Long id;

    private String poiId;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PoiDTO poiDTO = (PoiDTO) o;
        if (poiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoiDTO{" +
            "id=" + getId() +
            ", poiId='" + getPoiId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
