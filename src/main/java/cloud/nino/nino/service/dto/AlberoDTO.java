package cloud.nino.nino.service.dto;

import cloud.nino.nino.domain.enumeration.TipoDiSuolo;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the Albero entity.
 */
public class AlberoDTO implements Serializable {

    private Long id;

    private Long entityid;

    private Integer idPianta;

    private Integer codiceArea;

    private String nomeComune;

    private Integer classeAltezza;

    private Double altezza;

    private Integer diametroFusto;

    private Double diametro;

    private String wkt;

    private String aggiornamento;

    private String nota;

    private TipoDiSuolo tipoDiSuolo;

    private ZonedDateTime dataImpianto;

    private ZonedDateTime dataAbbattimento;

    private ZonedDateTime dataUltimoAggiornamento;

    private ZonedDateTime dataPrimaRilevazione;

    private String noteTecniche;

    private String posizione;

    private Boolean deleted;

    private Long essenzaId;

    private String essenzaNomeComune;

    private Long modificatoDaId;

    private String modificatoDaEmail;

    private Long mainId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityid() {
        return entityid;
    }

    public void setEntityid(Long entityid) {
        this.entityid = entityid;
    }

    public Integer getIdPianta() {
        return idPianta;
    }

    public void setIdPianta(Integer idPianta) {
        this.idPianta = idPianta;
    }

    public Integer getCodiceArea() {
        return codiceArea;
    }

    public void setCodiceArea(Integer codiceArea) {
        this.codiceArea = codiceArea;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public Integer getClasseAltezza() {
        return classeAltezza;
    }

    public void setClasseAltezza(Integer classeAltezza) {
        this.classeAltezza = classeAltezza;
    }

    public Double getAltezza() {
        return altezza;
    }

    public void setAltezza(Double altezza) {
        this.altezza = altezza;
    }

    public Integer getDiametroFusto() {
        return diametroFusto;
    }

    public void setDiametroFusto(Integer diametroFusto) {
        this.diametroFusto = diametroFusto;
    }

    public Double getDiametro() {
        return diametro;
    }

    public void setDiametro(Double diametro) {
        this.diametro = diametro;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getAggiornamento() {
        return aggiornamento;
    }

    public void setAggiornamento(String aggiornamento) {
        this.aggiornamento = aggiornamento;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public TipoDiSuolo getTipoDiSuolo() {
        return tipoDiSuolo;
    }

    public void setTipoDiSuolo(TipoDiSuolo tipoDiSuolo) {
        this.tipoDiSuolo = tipoDiSuolo;
    }

    public ZonedDateTime getDataImpianto() {
        return dataImpianto;
    }

    public void setDataImpianto(ZonedDateTime dataImpianto) {
        this.dataImpianto = dataImpianto;
    }

    public ZonedDateTime getDataAbbattimento() {
        return dataAbbattimento;
    }

    public void setDataAbbattimento(ZonedDateTime dataAbbattimento) {
        this.dataAbbattimento = dataAbbattimento;
    }

    public ZonedDateTime getDataUltimoAggiornamento() {
        return dataUltimoAggiornamento;
    }

    public void setDataUltimoAggiornamento(ZonedDateTime dataUltimoAggiornamento) {
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
    }

    public ZonedDateTime getDataPrimaRilevazione() {
        return dataPrimaRilevazione;
    }

    public void setDataPrimaRilevazione(ZonedDateTime dataPrimaRilevazione) {
        this.dataPrimaRilevazione = dataPrimaRilevazione;
    }

    public String getNoteTecniche() {
        return noteTecniche;
    }

    public void setNoteTecniche(String noteTecniche) {
        this.noteTecniche = noteTecniche;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getEssenzaId() {
        return essenzaId;
    }

    public void setEssenzaId(Long essenzaId) {
        this.essenzaId = essenzaId;
    }

    public String getEssenzaNomeComune() {
        return essenzaNomeComune;
    }

    public void setEssenzaNomeComune(String essenzaNomeComune) {
        this.essenzaNomeComune = essenzaNomeComune;
    }

    public Long getModificatoDaId() {
        return modificatoDaId;
    }

    public void setModificatoDaId(Long userId) {
        this.modificatoDaId = userId;
    }

    public String getModificatoDaEmail() {
        return modificatoDaEmail;
    }

    public void setModificatoDaEmail(String userEmail) {
        this.modificatoDaEmail = userEmail;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long alberoId) {
        this.mainId = alberoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlberoDTO alberoDTO = (AlberoDTO) o;
        if (alberoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alberoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlberoDTO{" +
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
            ", essenza=" + getEssenzaId() +
            ", essenza='" + getEssenzaNomeComune() + "'" +
            ", modificatoDa=" + getModificatoDaId() +
            ", modificatoDa='" + getModificatoDaEmail() + "'" +
            ", main=" + getMainId() +
            "}";
    }
}
