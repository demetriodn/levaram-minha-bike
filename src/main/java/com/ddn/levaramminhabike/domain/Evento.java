package com.ddn.levaramminhabike.domain;

import com.ddn.levaramminhabike.domain.enumeration.FormaFixacao;
import com.ddn.levaramminhabike.domain.enumeration.MetodoCoercao;
import com.ddn.levaramminhabike.domain.enumeration.TipoEvento;
import com.ddn.levaramminhabike.domain.enumeration.TipoLocal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Type;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_hora_evento", nullable = false)
    private Instant dataHoraEvento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento", nullable = false)
    private TipoEvento tipoEvento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_local", nullable = false)
    private TipoLocal tipoLocal;

    @Size(max = 200)
    @Column(name = "coordenada_local", length = 200)
    private String coordenadaLocal;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detalhes_local")
    private String detalhesLocal;

    @Column(name = "data_hora_criacao_registro")
    private Instant dataHoraCriacaoRegistro;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descricao_evento")
    private String descricaoEvento;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_fixacao")
    private FormaFixacao formaFixacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_coercao")
    private MetodoCoercao metodoCoercao;

    @Min(value = 1)
    @Column(name = "num_envolvidos_assaltantes")
    private Integer numEnvolvidosAssaltantes;

    @Min(value = 1)
    @Column(name = "num_envolvidos_vitimas")
    private Integer numEnvolvidosVitimas;

    @OneToMany(mappedBy = "evento")
    @JsonIgnoreProperties(value = { "bicicletaFotos", "bicicletaComentarios", "evento" }, allowSetters = true)
    private Set<Bicicleta> bicicletas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDataHoraEvento() {
        return this.dataHoraEvento;
    }

    public Evento dataHoraEvento(Instant dataHoraEvento) {
        this.dataHoraEvento = dataHoraEvento;
        return this;
    }

    public void setDataHoraEvento(Instant dataHoraEvento) {
        this.dataHoraEvento = dataHoraEvento;
    }

    public TipoEvento getTipoEvento() {
        return this.tipoEvento;
    }

    public Evento tipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
        return this;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public TipoLocal getTipoLocal() {
        return this.tipoLocal;
    }

    public Evento tipoLocal(TipoLocal tipoLocal) {
        this.tipoLocal = tipoLocal;
        return this;
    }

    public void setTipoLocal(TipoLocal tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public String getCoordenadaLocal() {
        return this.coordenadaLocal;
    }

    public Evento coordenadaLocal(String coordenadaLocal) {
        this.coordenadaLocal = coordenadaLocal;
        return this;
    }

    public void setCoordenadaLocal(String coordenadaLocal) {
        this.coordenadaLocal = coordenadaLocal;
    }

    public String getDetalhesLocal() {
        return this.detalhesLocal;
    }

    public Evento detalhesLocal(String detalhesLocal) {
        this.detalhesLocal = detalhesLocal;
        return this;
    }

    public void setDetalhesLocal(String detalhesLocal) {
        this.detalhesLocal = detalhesLocal;
    }

    public Instant getDataHoraCriacaoRegistro() {
        return this.dataHoraCriacaoRegistro;
    }

    public Evento dataHoraCriacaoRegistro(Instant dataHoraCriacaoRegistro) {
        this.dataHoraCriacaoRegistro = dataHoraCriacaoRegistro;
        return this;
    }

    public void setDataHoraCriacaoRegistro(Instant dataHoraCriacaoRegistro) {
        this.dataHoraCriacaoRegistro = dataHoraCriacaoRegistro;
    }

    public String getDescricaoEvento() {
        return this.descricaoEvento;
    }

    public Evento descricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
        return this;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public FormaFixacao getFormaFixacao() {
        return this.formaFixacao;
    }

    public Evento formaFixacao(FormaFixacao formaFixacao) {
        this.formaFixacao = formaFixacao;
        return this;
    }

    public void setFormaFixacao(FormaFixacao formaFixacao) {
        this.formaFixacao = formaFixacao;
    }

    public MetodoCoercao getMetodoCoercao() {
        return this.metodoCoercao;
    }

    public Evento metodoCoercao(MetodoCoercao metodoCoercao) {
        this.metodoCoercao = metodoCoercao;
        return this;
    }

    public void setMetodoCoercao(MetodoCoercao metodoCoercao) {
        this.metodoCoercao = metodoCoercao;
    }

    public Integer getNumEnvolvidosAssaltantes() {
        return this.numEnvolvidosAssaltantes;
    }

    public Evento numEnvolvidosAssaltantes(Integer numEnvolvidosAssaltantes) {
        this.numEnvolvidosAssaltantes = numEnvolvidosAssaltantes;
        return this;
    }

    public void setNumEnvolvidosAssaltantes(Integer numEnvolvidosAssaltantes) {
        this.numEnvolvidosAssaltantes = numEnvolvidosAssaltantes;
    }

    public Integer getNumEnvolvidosVitimas() {
        return this.numEnvolvidosVitimas;
    }

    public Evento numEnvolvidosVitimas(Integer numEnvolvidosVitimas) {
        this.numEnvolvidosVitimas = numEnvolvidosVitimas;
        return this;
    }

    public void setNumEnvolvidosVitimas(Integer numEnvolvidosVitimas) {
        this.numEnvolvidosVitimas = numEnvolvidosVitimas;
    }

    public Set<Bicicleta> getBicicletas() {
        return this.bicicletas;
    }

    public Evento bicicletas(Set<Bicicleta> bicicletas) {
        this.setBicicletas(bicicletas);
        return this;
    }

    public Evento addBicicleta(Bicicleta bicicleta) {
        this.bicicletas.add(bicicleta);
        bicicleta.setEvento(this);
        return this;
    }

    public Evento removeBicicleta(Bicicleta bicicleta) {
        this.bicicletas.remove(bicicleta);
        bicicleta.setEvento(null);
        return this;
    }

    public void setBicicletas(Set<Bicicleta> bicicletas) {
        if (this.bicicletas != null) {
            this.bicicletas.forEach(i -> i.setEvento(null));
        }
        if (bicicletas != null) {
            bicicletas.forEach(i -> i.setEvento(this));
        }
        this.bicicletas = bicicletas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evento)) {
            return false;
        }
        return id != null && id.equals(((Evento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", dataHoraEvento='" + getDataHoraEvento() + "'" +
            ", tipoEvento='" + getTipoEvento() + "'" +
            ", tipoLocal='" + getTipoLocal() + "'" +
            ", coordenadaLocal='" + getCoordenadaLocal() + "'" +
            ", detalhesLocal='" + getDetalhesLocal() + "'" +
            ", dataHoraCriacaoRegistro='" + getDataHoraCriacaoRegistro() + "'" +
            ", descricaoEvento='" + getDescricaoEvento() + "'" +
            ", formaFixacao='" + getFormaFixacao() + "'" +
            ", metodoCoercao='" + getMetodoCoercao() + "'" +
            ", numEnvolvidosAssaltantes=" + getNumEnvolvidosAssaltantes() +
            ", numEnvolvidosVitimas=" + getNumEnvolvidosVitimas() +
            "}";
    }
}
