package com.ddn.levaramminhabike.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A BicicletaComentario.
 */
@Entity
@Table(name = "bicicleta_comentario")
public class BicicletaComentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "observacao")
    private String observacao;

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

    public BicicletaComentario id(Long id) {
        this.id = id;
        return this;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public BicicletaComentario observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Bicicleta getBicicleta() {
        return this.bicicleta;
    }

    public BicicletaComentario bicicleta(Bicicleta bicicleta) {
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
        if (!(o instanceof BicicletaComentario)) {
            return false;
        }
        return id != null && id.equals(((BicicletaComentario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BicicletaComentario{" +
            "id=" + getId() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
