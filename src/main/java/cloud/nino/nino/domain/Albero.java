package cloud.nino.nino.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import cloud.nino.nino.domain.enumeration.TipoDiSuolo;

/**
 * A Albero.
 */
@Entity
@Table(name = "albero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Albero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "entityid")
    private Long entityid;

    @Column(name = "id_pianta")
    private Integer idPianta;

    @Column(name = "codice_area")
    private Integer codiceArea;

    @Column(name = "nome_comune")
    private String nomeComune;

    @Column(name = "classe_altezza")
    private Integer classeAltezza;

    @Column(name = "altezza")
    private Double altezza;

    @Column(name = "diametro_fusto")
    private Integer diametroFusto;

    @Column(name = "diametro")
    private Double diametro;

    @Column(name = "wkt")
    private String wkt;

    @Column(name = "aggiornamento")
    private String aggiornamento;

    @Column(name = "nota")
    private String nota;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_di_suolo")
    private TipoDiSuolo tipoDiSuolo;

    @Column(name = "data_impianto")
    private ZonedDateTime dataImpianto;

    @Column(name = "data_abbattimento")
    private ZonedDateTime dataAbbattimento;

    @Column(name = "data_ultimo_aggiornamento")
    private ZonedDateTime dataUltimoAggiornamento;

    @Column(name = "data_prima_rilevazione")
    private ZonedDateTime dataPrimaRilevazione;

    @Column(name = "note_tecniche")
    private String noteTecniche;

    @Column(name = "posizione")
    private String posizione;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Essenza essenza;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User modificatoDa;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Albero main;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityid() {
        return entityid;
    }

    public Albero entityid(Long entityid) {
        this.entityid = entityid;
        return this;
    }

    public void setEntityid(Long entityid) {
        this.entityid = entityid;
    }

    public Integer getIdPianta() {
        return idPianta;
    }

    public Albero idPianta(Integer idPianta) {
        this.idPianta = idPianta;
        return this;
    }

    public void setIdPianta(Integer idPianta) {
        this.idPianta = idPianta;
    }

    public Integer getCodiceArea() {
        return codiceArea;
    }

    public Albero codiceArea(Integer codiceArea) {
        this.codiceArea = codiceArea;
        return this;
    }

    public void setCodiceArea(Integer codiceArea) {
        this.codiceArea = codiceArea;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public Albero nomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
        return this;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public Integer getClasseAltezza() {
        return classeAltezza;
    }

    public Albero classeAltezza(Integer classeAltezza) {
        this.classeAltezza = classeAltezza;
        return this;
    }

    public void setClasseAltezza(Integer classeAltezza) {
        this.classeAltezza = classeAltezza;
    }

    public Double getAltezza() {
        return altezza;
    }

    public Albero altezza(Double altezza) {
        this.altezza = altezza;
        return this;
    }

    public void setAltezza(Double altezza) {
        this.altezza = altezza;
    }

    public Integer getDiametroFusto() {
        return diametroFusto;
    }

    public Albero diametroFusto(Integer diametroFusto) {
        this.diametroFusto = diametroFusto;
        return this;
    }

    public void setDiametroFusto(Integer diametroFusto) {
        this.diametroFusto = diametroFusto;
    }

    public Double getDiametro() {
        return diametro;
    }

    public Albero diametro(Double diametro) {
        this.diametro = diametro;
        return this;
    }

    public void setDiametro(Double diametro) {
        this.diametro = diametro;
    }

    public String getWkt() {
        return wkt;
    }

    public Albero wkt(String wkt) {
        this.wkt = wkt;
        return this;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getAggiornamento() {
        return aggiornamento;
    }

    public Albero aggiornamento(String aggiornamento) {
        this.aggiornamento = aggiornamento;
        return this;
    }

    public void setAggiornamento(String aggiornamento) {
        this.aggiornamento = aggiornamento;
    }

    public String getNota() {
        return nota;
    }

    public Albero nota(String nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public TipoDiSuolo getTipoDiSuolo() {
        return tipoDiSuolo;
    }

    public Albero tipoDiSuolo(TipoDiSuolo tipoDiSuolo) {
        this.tipoDiSuolo = tipoDiSuolo;
        return this;
    }

    public void setTipoDiSuolo(TipoDiSuolo tipoDiSuolo) {
        this.tipoDiSuolo = tipoDiSuolo;
    }

    public ZonedDateTime getDataImpianto() {
        return dataImpianto;
    }

    public Albero dataImpianto(ZonedDateTime dataImpianto) {
        this.dataImpianto = dataImpianto;
        return this;
    }

    public void setDataImpianto(ZonedDateTime dataImpianto) {
        this.dataImpianto = dataImpianto;
    }

    public ZonedDateTime getDataAbbattimento() {
        return dataAbbattimento;
    }

    public Albero dataAbbattimento(ZonedDateTime dataAbbattimento) {
        this.dataAbbattimento = dataAbbattimento;
        return this;
    }

    public void setDataAbbattimento(ZonedDateTime dataAbbattimento) {
        this.dataAbbattimento = dataAbbattimento;
    }

    public ZonedDateTime getDataUltimoAggiornamento() {
        return dataUltimoAggiornamento;
    }

    public Albero dataUltimoAggiornamento(ZonedDateTime dataUltimoAggiornamento) {
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
        return this;
    }

    public void setDataUltimoAggiornamento(ZonedDateTime dataUltimoAggiornamento) {
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
    }

    public ZonedDateTime getDataPrimaRilevazione() {
        return dataPrimaRilevazione;
    }

    public Albero dataPrimaRilevazione(ZonedDateTime dataPrimaRilevazione) {
        this.dataPrimaRilevazione = dataPrimaRilevazione;
        return this;
    }

    public void setDataPrimaRilevazione(ZonedDateTime dataPrimaRilevazione) {
        this.dataPrimaRilevazione = dataPrimaRilevazione;
    }

    public String getNoteTecniche() {
        return noteTecniche;
    }

    public Albero noteTecniche(String noteTecniche) {
        this.noteTecniche = noteTecniche;
        return this;
    }

    public void setNoteTecniche(String noteTecniche) {
        this.noteTecniche = noteTecniche;
    }

    public String getPosizione() {
        return posizione;
    }

    public Albero posizione(String posizione) {
        this.posizione = posizione;
        return this;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Albero deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Essenza getEssenza() {
        return essenza;
    }

    public Albero essenza(Essenza essenza) {
        this.essenza = essenza;
        return this;
    }

    public void setEssenza(Essenza essenza) {
        this.essenza = essenza;
    }

    public User getModificatoDa() {
        return modificatoDa;
    }

    public Albero modificatoDa(User user) {
        this.modificatoDa = user;
        return this;
    }

    public void setModificatoDa(User user) {
        this.modificatoDa = user;
    }

    public Albero getMain() {
        return main;
    }

    public Albero main(Albero albero) {
        this.main = albero;
        return this;
    }

    public void setMain(Albero albero) {
        this.main = albero;
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
        Albero albero = (Albero) o;
        if (albero.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), albero.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Albero{" +
            "id=" + getId() +
            ", entityid=" + getEntityid() +
            ", idPianta=" + getIdPianta() +
            ", codiceArea=" + getCodiceArea() +
            ", nomeComune='" + getNomeComune() + "'" +
            ", classeAltezza=" + getClasseAltezza() +
            ", altezza=" + getAltezza() +
            ", diametroFusto=" + getDiametroFusto() +
            ", diametro=" + getDiametro() +
            ", wkt='" + getWkt() + "'" +
            ", aggiornamento='" + getAggiornamento() + "'" +
            ", nota='" + getNota() + "'" +
            ", tipoDiSuolo='" + getTipoDiSuolo() + "'" +
            ", dataImpianto='" + getDataImpianto() + "'" +
            ", dataAbbattimento='" + getDataAbbattimento() + "'" +
            ", dataUltimoAggiornamento='" + getDataUltimoAggiornamento() + "'" +
            ", dataPrimaRilevazione='" + getDataPrimaRilevazione() + "'" +
            ", noteTecniche='" + getNoteTecniche() + "'" +
            ", posizione='" + getPosizione() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
