package com.ddn.levaramminhabike.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BicicletaFoto.
 */
@Entity
@Table(name = "bicicleta_foto")
public class BicicletaFoto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 256)
    @Column(name = "url_imagem", length = 256)
    private String urlImagem;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bicicletaFotos", "bicicletaComentarios", "evento" }, allowSetters = true)
    private Bicicleta bicicleta;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BicicletaFoto id(Long id) {
        this.id = id;
        return this;
    }

    public String getUrlImagem() {
        return this.urlImagem;
    }

    public BicicletaFoto urlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
        return this;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Bicicleta getBicicleta() {
        return this.bicicleta;
    }

    public BicicletaFoto bicicleta(Bicicleta bicicleta) {
        this.setBicicleta(bicicleta);
        return this;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BicicletaFoto)) {
            return false;
        }
        return id != null && id.equals(((BicicletaFoto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BicicletaFoto{" +
            "id=" + getId() +
            ", urlImagem='" + getUrlImagem() + "'" +
            "}";
    }
}
