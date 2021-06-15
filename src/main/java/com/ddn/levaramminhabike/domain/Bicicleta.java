package com.ddn.levaramminhabike.domain;

import com.ddn.levaramminhabike.domain.enumeration.StatusBicicleta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Bicicleta.
 */
@Entity
@Table(name = "bicicleta")
public class Bicicleta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusBicicleta status;

    @Size(max = 100)
    @Column(name = "numero_quadro", length = 100)
    private String numeroQuadro;

    @Size(max = 100)
    @Column(name = "numero_bike_registrada", length = 100)
    private String numeroBikeRegistrada;

    @OneToMany(mappedBy = "bicicleta")
    @JsonIgnoreProperties(value = { "bicicleta" }, allowSetters = true)
    private Set<BicicletaFoto> bicicletaFotos = new HashSet<>();

    @OneToMany(mappedBy = "bicicleta")
    @JsonIgnoreProperties(value = { "bicicleta" }, allowSetters = true)
    private Set<BicicletaComentario> bicicletaComentarios = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "bicicletas" }, allowSetters = true)
    private Evento evento;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bicicleta id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Bicicleta descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusBicicleta getStatus() {
        return this.status;
    }

    public Bicicleta status(StatusBicicleta status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusBicicleta status) {
        this.status = status;
    }

    public String getNumeroQuadro() {
        return this.numeroQuadro;
    }

    public Bicicleta numeroQuadro(String numeroQuadro) {
        this.numeroQuadro = numeroQuadro;
        return this;
    }

    public void setNumeroQuadro(String numeroQuadro) {
        this.numeroQuadro = numeroQuadro;
    }

    public String getNumeroBikeRegistrada() {
        return this.numeroBikeRegistrada;
    }

    public Bicicleta numeroBikeRegistrada(String numeroBikeRegistrada) {
        this.numeroBikeRegistrada = numeroBikeRegistrada;
        return this;
    }

    public void setNumeroBikeRegistrada(String numeroBikeRegistrada) {
        this.numeroBikeRegistrada = numeroBikeRegistrada;
    }

    public Set<BicicletaFoto> getBicicletaFotos() {
        return this.bicicletaFotos;
    }

    public Bicicleta bicicletaFotos(Set<BicicletaFoto> bicicletaFotos) {
        this.setBicicletaFotos(bicicletaFotos);
        return this;
    }

    public Bicicleta addBicicletaFoto(BicicletaFoto bicicletaFoto) {
        this.bicicletaFotos.add(bicicletaFoto);
        bicicletaFoto.setBicicleta(this);
        return this;
    }

    public Bicicleta removeBicicletaFoto(BicicletaFoto bicicletaFoto) {
        this.bicicletaFotos.remove(bicicletaFoto);
        bicicletaFoto.setBicicleta(null);
        return this;
    }

    public void setBicicletaFotos(Set<BicicletaFoto> bicicletaFotos) {
        if (this.bicicletaFotos != null) {
            this.bicicletaFotos.forEach(i -> i.setBicicleta(null));
        }
        if (bicicletaFotos != null) {
            bicicletaFotos.forEach(i -> i.setBicicleta(this));
        }
        this.bicicletaFotos = bicicletaFotos;
    }

    public Set<BicicletaComentario> getBicicletaComentarios() {
        return this.bicicletaComentarios;
    }

    public Bicicleta bicicletaComentarios(Set<BicicletaComentario> bicicletaComentarios) {
        this.setBicicletaComentarios(bicicletaComentarios);
        return this;
    }

    public Bicicleta addBicicletaComentario(BicicletaComentario bicicletaComentario) {
        this.bicicletaComentarios.add(bicicletaComentario);
        bicicletaComentario.setBicicleta(this);
        return this;
    }

    public Bicicleta removeBicicletaComentario(BicicletaComentario bicicletaComentario) {
        this.bicicletaComentarios.remove(bicicletaComentario);
        bicicletaComentario.setBicicleta(null);
        return this;
    }

    public void setBicicletaComentarios(Set<BicicletaComentario> bicicletaComentarios) {
        if (this.bicicletaComentarios != null) {
            this.bicicletaComentarios.forEach(i -> i.setBicicleta(null));
        }
        if (bicicletaComentarios != null) {
            bicicletaComentarios.forEach(i -> i.setBicicleta(this));
        }
        this.bicicletaComentarios = bicicletaComentarios;
    }

    public Evento getEvento() {
        return this.evento;
    }

    public Bicicleta evento(Evento evento) {
        this.setEvento(evento);
        return this;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bicicleta)) {
            return false;
        }
        return id != null && id.equals(((Bicicleta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bicicleta{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", status='" + getStatus() + "'" +
            ", numeroQuadro='" + getNumeroQuadro() + "'" +
            ", numeroBikeRegistrada='" + getNumeroBikeRegistrada() + "'" +
            "}";
    }
}
