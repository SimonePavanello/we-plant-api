package cloud.nino.nino.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EssenzaAudit entity.
 */
public class EssenzaAuditDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idComune;

    private String tipoVerde;

    private String genereESpecie;

    private String nomeComune;

    private String valoreSicurezza;

    private String valoreBioAmbientale;

    private String provenienza;

    @Size(max = 20000)
    private String descrizione;

    @Size(max = 20000)
    private String usieCuriosita;

    private String genere;

    private String specie;

    private ZonedDateTime dataUltimoAggiornamento;

    private Long essenzaId;

    private Long modificatoDaId;

    private String modificatoDaEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdComune() {
        return idComune;
    }

    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    public String getTipoVerde() {
        return tipoVerde;
    }

    public void setTipoVerde(String tipoVerde) {
        this.tipoVerde = tipoVerde;
    }

    public String getGenereESpecie() {
        return genereESpecie;
    }

    public void setGenereESpecie(String genereESpecie) {
        this.genereESpecie = genereESpecie;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public String getValoreSicurezza() {
        return valoreSicurezza;
    }

    public void setValoreSicurezza(String valoreSicurezza) {
        this.valoreSicurezza = valoreSicurezza;
    }

    public String getValoreBioAmbientale() {
        return valoreBioAmbientale;
    }

    public void setValoreBioAmbientale(String valoreBioAmbientale) {
        this.valoreBioAmbientale = valoreBioAmbientale;
    }

    public String getProvenienza() {
        return provenienza;
    }

    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUsieCuriosita() {
        return usieCuriosita;
    }

    public void setUsieCuriosita(String usieCuriosita) {
        this.usieCuriosita = usieCuriosita;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public ZonedDateTime getDataUltimoAggiornamento() {
        return dataUltimoAggiornamento;
    }

    public void setDataUltimoAggiornamento(ZonedDateTime dataUltimoAggiornamento) {
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
    }

    public Long getEssenzaId() {
        return essenzaId;
    }

    public void setEssenzaId(Long essenzaId) {
        this.essenzaId = essenzaId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EssenzaAuditDTO essenzaAuditDTO = (EssenzaAuditDTO) o;
        if (essenzaAuditDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), essenzaAuditDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EssenzaAuditDTO{" +
            "id=" + getId() +
            ", idComune=" + getIdComune() +
            ", tipoVerde='" + getTipoVerde() + "'" +
            ", genereESpecie='" + getGenereESpecie() + "'" +
            ", nomeComune='" + getNomeComune() + "'" +
            ", valoreSicurezza='" + getValoreSicurezza() + "'" +
            ", valoreBioAmbientale='" + getValoreBioAmbientale() + "'" +
            ", provenienza='" + getProvenienza() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", usieCuriosita='" + getUsieCuriosita() + "'" +
            ", genere='" + getGenere() + "'" +
            ", specie='" + getSpecie() + "'" +
            ", dataUltimoAggiornamento='" + getDataUltimoAggiornamento() + "'" +
            ", essenza=" + getEssenzaId() +
            ", modificatoDa=" + getModificatoDaId() +
            ", modificatoDa='" + getModificatoDaEmail() + "'" +
            "}";
    }
}
