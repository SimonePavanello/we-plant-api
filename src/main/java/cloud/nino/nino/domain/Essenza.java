package cloud.nino.nino.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Essenza.
 */
@Entity
@Table(name = "essenza")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Essenza implements Serializable {

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

    public Essenza idComune(Long idComune) {
        this.idComune = idComune;
        return this;
    }

    public void setIdComune(Long idComune) {
        this.idComune = idComune;
    }

    public String getTipoVerde() {
        return tipoVerde;
    }

    public Essenza tipoVerde(String tipoVerde) {
        this.tipoVerde = tipoVerde;
        return this;
    }

    public void setTipoVerde(String tipoVerde) {
        this.tipoVerde = tipoVerde;
    }

    public String getGenereESpecie() {
        return genereESpecie;
    }

    public Essenza genereESpecie(String genereESpecie) {
        this.genereESpecie = genereESpecie;
        return this;
    }

    public void setGenereESpecie(String genereESpecie) {
        this.genereESpecie = genereESpecie;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public Essenza nomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
        return this;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public String getValoreSicurezza() {
        return valoreSicurezza;
    }

    public Essenza valoreSicurezza(String valoreSicurezza) {
        this.valoreSicurezza = valoreSicurezza;
        return this;
    }

    public void setValoreSicurezza(String valoreSicurezza) {
        this.valoreSicurezza = valoreSicurezza;
    }

    public String getValoreBioAmbientale() {
        return valoreBioAmbientale;
    }

    public Essenza valoreBioAmbientale(String valoreBioAmbientale) {
        this.valoreBioAmbientale = valoreBioAmbientale;
        return this;
    }

    public void setValoreBioAmbientale(String valoreBioAmbientale) {
        this.valoreBioAmbientale = valoreBioAmbientale;
    }

    public String getProvenienza() {
        return provenienza;
    }

    public Essenza provenienza(String provenienza) {
        this.provenienza = provenienza;
        return this;
    }

    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Essenza descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUsieCuriosita() {
        return usieCuriosita;
    }

    public Essenza usieCuriosita(String usieCuriosita) {
        this.usieCuriosita = usieCuriosita;
        return this;
    }

    public void setUsieCuriosita(String usieCuriosita) {
        this.usieCuriosita = usieCuriosita;
    }

    public String getGenere() {
        return genere;
    }

    public Essenza genere(String genere) {
        this.genere = genere;
        return this;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getSpecie() {
        return specie;
    }

    public Essenza specie(String specie) {
        this.specie = specie;
        return this;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
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
        Essenza essenza = (Essenza) o;
        if (essenza.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), essenza.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Essenza{" +
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
            "}";
    }
}
