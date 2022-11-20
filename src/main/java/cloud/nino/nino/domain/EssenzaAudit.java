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
 * A EssenzaAudit.
 */
@Entity
@Table(name = "essenza_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EssenzaAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_comune", nullable = false)
    private Long idComune;

    @Column(name = "tipo_verde")
    private String tipoVerde;

    @Column(name = "genere_e_specie")
    private String genereESpecie;

    @Column(name = "nome_comune")
    private String nomeComune;

    @Column(name = "valore_sicurezza")
    private String valoreSicurezza;

    @Column(name = "valore_bio_ambientale")
    private String valoreBioAmbientale;

    @Column(name = "provenienza")
    private String provenienza;

    @Size(max = 20000)
    @Column(name = "descrizione", length = 20000)
    private String descrizione;

    @Size(max = 20000)
    @Column(name = "usie_curiosita", length = 20000)
    private String usieCuriosita;

    @Column(name = "genere")
    private String genere;

    @Column(name = "specie")
    private String specie;

    @Column(name = "data_ultimo_aggiornamento")
    private ZonedDateTime dataUltimoAggiornamento;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Essenza essenza;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User modificatoDa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdComune() {
        return idComune;
    }

    public EssenzaAudit idComune(Long idComune) {
        this.idComune = idComune;
        return this;
    }

    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    public String getTipoVerde() {
        return tipoVerde;
    }

    public EssenzaAudit tipoVerde(String tipoVerde) {
        this.tipoVerde = tipoVerde;
        return this;
    }

    public void setTipoVerde(String tipoVerde) {
        this.tipoVerde = tipoVerde;
    }

    public String getGenereESpecie() {
        return genereESpecie;
    }

    public EssenzaAudit genereESpecie(String genereESpecie) {
        this.genereESpecie = genereESpecie;
        return this;
    }

    public void setGenereESpecie(String genereESpecie) {
        this.genereESpecie = genereESpecie;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public EssenzaAudit nomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
        return this;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public String getValoreSicurezza() {
        return valoreSicurezza;
    }

    public EssenzaAudit valoreSicurezza(String valoreSicurezza) {
        this.valoreSicurezza = valoreSicurezza;
        return this;
    }

    public void setValoreSicurezza(String valoreSicurezza) {
        this.valoreSicurezza = valoreSicurezza;
    }

    public String getValoreBioAmbientale() {
        return valoreBioAmbientale;
    }

    public EssenzaAudit valoreBioAmbientale(String valoreBioAmbientale) {
        this.valoreBioAmbientale = valoreBioAmbientale;
        return this;
    }

    public void setValoreBioAmbientale(String valoreBioAmbientale) {
        this.valoreBioAmbientale = valoreBioAmbientale;
    }

    public String getProvenienza() {
        return provenienza;
    }

    public EssenzaAudit provenienza(String provenienza) {
        this.provenienza = provenienza;
        return this;
    }

    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public EssenzaAudit descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUsieCuriosita() {
        return usieCuriosita;
    }

    public EssenzaAudit usieCuriosita(String usieCuriosita) {
        this.usieCuriosita = usieCuriosita;
        return this;
    }

    public void setUsieCuriosita(String usieCuriosita) {
        this.usieCuriosita = usieCuriosita;
    }

    public String getGenere() {
        return genere;
    }

    public EssenzaAudit genere(String genere) {
        this.genere = genere;
        return this;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getSpecie() {
        return specie;
    }

    public EssenzaAudit specie(String specie) {
        this.specie = specie;
        return this;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public ZonedDateTime getDataUltimoAggiornamento() {
        return dataUltimoAggiornamento;
    }

    public EssenzaAudit dataUltimoAggiornamento(ZonedDateTime dataUltimoAggiornamento) {
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
        return this;
    }

    public void setDataUltimoAggiornamento(ZonedDateTime dataUltimoAggiornamento) {
        this.dataUltimoAggiornamento = dataUltimoAggiornamento;
    }

    public Essenza getEssenza() {
        return essenza;
    }

    public EssenzaAudit essenza(Essenza essenza) {
        this.essenza = essenza;
        return this;
    }

    public void setEssenza(Essenza essenza) {
        this.essenza = essenza;
    }

    public User getModificatoDa() {
        return modificatoDa;
    }

    public EssenzaAudit modificatoDa(User user) {
        this.modificatoDa = user;
        return this;
    }

    public void setModificatoDa(User user) {
        this.modificatoDa = user;
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
        EssenzaAudit essenzaAudit = (EssenzaAudit) o;
        if (essenzaAudit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), essenzaAudit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EssenzaAudit{" +
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
            "}";
    }
}
