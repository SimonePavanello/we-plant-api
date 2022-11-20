package cloud.nino.nino.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Image entity.
 */
public class ImageDTO implements Serializable {

    private Long id;

    private ZonedDateTime createDate;

    private ZonedDateTime modifiedDate;

    private String name;

    private String format;

    private String location;

    @Size(max = 20000)
    private String imagePath;

    @Size(max = 20000)
    private String thumbnailPath;

    private Long alberoId;

    private String alberoNomeComune;

    private Long poiId;

    private String poiName;

    private Long cratedById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Long getAlberoId() {
        return alberoId;
    }

    public void setAlberoId(Long alberoId) {
        this.alberoId = alberoId;
    }

    public String getAlberoNomeComune() {
        return alberoNomeComune;
    }

    public void setAlberoNomeComune(String alberoNomeComune) {
        this.alberoNomeComune = alberoNomeComune;
    }

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public Long getCratedById() {
        return cratedById;
    }

    public void setCratedById(Long userId) {
        this.cratedById = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageDTO imageDTO = (ImageDTO) o;
        if (imageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", name='" + getName() + "'" +
            ", format='" + getFormat() + "'" +
            ", location='" + getLocation() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", thumbnailPath='" + getThumbnailPath() + "'" +
            ", albero=" + getAlberoId() +
            ", albero='" + getAlberoNomeComune() + "'" +
            ", poi=" + getPoiId() +
            ", poi='" + getPoiName() + "'" +
            ", cratedBy=" + getCratedById() +
            "}";
    }
}
