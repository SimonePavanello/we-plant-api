package cloud.nino.nino.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @Column(name = "name")
    private String name;

    @Column(name = "format")
    private String format;

    @Column(name = "location")
    private String location;

    @Size(max = 20000)
    @Column(name = "image_path", length = 20000)
    private String imagePath;

    @Size(max = 20000)
    @Column(name = "thumbnail_path", length = 20000)
    private String thumbnailPath;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Albero albero;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Poi poi;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User cratedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Image createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public Image modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public Image name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public Image format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLocation() {
        return location;
    }

    public Image location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Image imagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public Image thumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
        return this;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Albero getAlbero() {
        return albero;
    }

    public Image albero(Albero albero) {
        this.albero = albero;
        return this;
    }

    public void setAlbero(Albero albero) {
        this.albero = albero;
    }

    public Poi getPoi() {
        return poi;
    }

    public Image poi(Poi poi) {
        this.poi = poi;
        return this;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }

    public User getCratedBy() {
        return cratedBy;
    }

    public Image cratedBy(User user) {
        this.cratedBy = user;
        return this;
    }

    public void setCratedBy(User user) {
        this.cratedBy = user;
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
        Image image = (Image) o;
        if (image.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), image.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", createDate='" + getCreateDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", name='" + getName() + "'" +
            ", format='" + getFormat() + "'" +
            ", location='" + getLocation() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", thumbnailPath='" + getThumbnailPath() + "'" +
            "}";
    }
}
