package com.ddn.levaramminhabike.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ddn.levaramminhabike.IntegrationTest;
import com.ddn.levaramminhabike.domain.Evento;
import com.ddn.levaramminhabike.domain.enumeration.FormaFixacao;
import com.ddn.levaramminhabike.domain.enumeration.MetodoCoercao;
import com.ddn.levaramminhabike.domain.enumeration.TipoEvento;
import com.ddn.levaramminhabike.domain.enumeration.TipoLocal;
import com.ddn.levaramminhabike.repository.EventoRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EventoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventoResourceIT {

    private static final Instant DEFAULT_DATA_HORA_EVENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_EVENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TipoEvento DEFAULT_TIPO_EVENTO = TipoEvento.FURTO;
    private static final TipoEvento UPDATED_TIPO_EVENTO = TipoEvento.ROUBO;

    private static final TipoLocal DEFAULT_TIPO_LOCAL = TipoLocal.RUA;
    private static final TipoLocal UPDATED_TIPO_LOCAL = TipoLocal.CALCADA;

    private static final String DEFAULT_COORDENADA_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_COORDENADA_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHES_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_DETALHES_LOCAL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_CRIACAO_REGISTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_CRIACAO_REGISTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRICAO_EVENTO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_EVENTO = "BBBBBBBBBB";

    private static final FormaFixacao DEFAULT_FORMA_FIXACAO = FormaFixacao.SOLTA;
    private static final FormaFixacao UPDATED_FORMA_FIXACAO = FormaFixacao.TRAVA_EM_U;

    private static final MetodoCoercao DEFAULT_METODO_COERCAO = MetodoCoercao.ARMA_BRANCA;
    private static final MetodoCoercao UPDATED_METODO_COERCAO = MetodoCoercao.ARMA_DE_FOGO;

    private static final Integer DEFAULT_NUM_ENVOLVIDOS_ASSALTANTES = 1;
    private static final Integer UPDATED_NUM_ENVOLVIDOS_ASSALTANTES = 2;

    private static final Integer DEFAULT_NUM_ENVOLVIDOS_VITIMAS = 1;
    private static final Integer UPDATED_NUM_ENVOLVIDOS_VITIMAS = 2;

    private static final String ENTITY_API_URL = "/api/eventos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventoMockMvc;

    private Evento evento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evento createEntity(EntityManager em) {
        Evento evento = new Evento()
            .dataHoraEvento(DEFAULT_DATA_HORA_EVENTO)
            .tipoEvento(DEFAULT_TIPO_EVENTO)
            .tipoLocal(DEFAULT_TIPO_LOCAL)
            .coordenadaLocal(DEFAULT_COORDENADA_LOCAL)
            .detalhesLocal(DEFAULT_DETALHES_LOCAL)
            .dataHoraCriacaoRegistro(DEFAULT_DATA_HORA_CRIACAO_REGISTRO)
            .descricaoEvento(DEFAULT_DESCRICAO_EVENTO)
            .formaFixacao(DEFAULT_FORMA_FIXACAO)
            .metodoCoercao(DEFAULT_METODO_COERCAO)
            .numEnvolvidosAssaltantes(DEFAULT_NUM_ENVOLVIDOS_ASSALTANTES)
            .numEnvolvidosVitimas(DEFAULT_NUM_ENVOLVIDOS_VITIMAS);
        return evento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evento createUpdatedEntity(EntityManager em) {
        Evento evento = new Evento()
            .dataHoraEvento(UPDATED_DATA_HORA_EVENTO)
            .tipoEvento(UPDATED_TIPO_EVENTO)
            .tipoLocal(UPDATED_TIPO_LOCAL)
            .coordenadaLocal(UPDATED_COORDENADA_LOCAL)
            .detalhesLocal(UPDATED_DETALHES_LOCAL)
            .dataHoraCriacaoRegistro(UPDATED_DATA_HORA_CRIACAO_REGISTRO)
            .descricaoEvento(UPDATED_DESCRICAO_EVENTO)
            .formaFixacao(UPDATED_FORMA_FIXACAO)
            .metodoCoercao(UPDATED_METODO_COERCAO)
            .numEnvolvidosAssaltantes(UPDATED_NUM_ENVOLVIDOS_ASSALTANTES)
            .numEnvolvidosVitimas(UPDATED_NUM_ENVOLVIDOS_VITIMAS);
        return evento;
    }

    @BeforeEach
    public void initTest() {
        evento = createEntity(em);
    }

    @Test
    @Transactional
    void createEvento() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();
        // Create the Evento
        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isCreated());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate + 1);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getDataHoraEvento()).isEqualTo(DEFAULT_DATA_HORA_EVENTO);
        assertThat(testEvento.getTipoEvento()).isEqualTo(DEFAULT_TIPO_EVENTO);
        assertThat(testEvento.getTipoLocal()).isEqualTo(DEFAULT_TIPO_LOCAL);
        assertThat(testEvento.getCoordenadaLocal()).isEqualTo(DEFAULT_COORDENADA_LOCAL);
        assertThat(testEvento.getDetalhesLocal()).isEqualTo(DEFAULT_DETALHES_LOCAL);
        assertThat(testEvento.getDataHoraCriacaoRegistro()).isEqualTo(DEFAULT_DATA_HORA_CRIACAO_REGISTRO);
        assertThat(testEvento.getDescricaoEvento()).isEqualTo(DEFAULT_DESCRICAO_EVENTO);
        assertThat(testEvento.getFormaFixacao()).isEqualTo(DEFAULT_FORMA_FIXACAO);
        assertThat(testEvento.getMetodoCoercao()).isEqualTo(DEFAULT_METODO_COERCAO);
        assertThat(testEvento.getNumEnvolvidosAssaltantes()).isEqualTo(DEFAULT_NUM_ENVOLVIDOS_ASSALTANTES);
        assertThat(testEvento.getNumEnvolvidosVitimas()).isEqualTo(DEFAULT_NUM_ENVOLVIDOS_VITIMAS);
    }

    @Test
    @Transactional
    void createEventoWithExistingId() throws Exception {
        // Create the Evento with an existing ID
        evento.setId(1L);

        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataHoraEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setDataHoraEvento(null);

        // Create the Evento, which fails.

        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoEventoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setTipoEvento(null);

        // Create the Evento, which fails.

        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoLocalIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setTipoLocal(null);

        // Create the Evento, which fails.

        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventos() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList
        restEventoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHoraEvento").value(hasItem(DEFAULT_DATA_HORA_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoEvento").value(hasItem(DEFAULT_TIPO_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].tipoLocal").value(hasItem(DEFAULT_TIPO_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].coordenadaLocal").value(hasItem(DEFAULT_COORDENADA_LOCAL)))
            .andExpect(jsonPath("$.[*].detalhesLocal").value(hasItem(DEFAULT_DETALHES_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].dataHoraCriacaoRegistro").value(hasItem(DEFAULT_DATA_HORA_CRIACAO_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].descricaoEvento").value(hasItem(DEFAULT_DESCRICAO_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].formaFixacao").value(hasItem(DEFAULT_FORMA_FIXACAO.toString())))
            .andExpect(jsonPath("$.[*].metodoCoercao").value(hasItem(DEFAULT_METODO_COERCAO.toString())))
            .andExpect(jsonPath("$.[*].numEnvolvidosAssaltantes").value(hasItem(DEFAULT_NUM_ENVOLVIDOS_ASSALTANTES)))
            .andExpect(jsonPath("$.[*].numEnvolvidosVitimas").value(hasItem(DEFAULT_NUM_ENVOLVIDOS_VITIMAS)));
    }

    @Test
    @Transactional
    void getEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get the evento
        restEventoMockMvc
            .perform(get(ENTITY_API_URL_ID, evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evento.getId().intValue()))
            .andExpect(jsonPath("$.dataHoraEvento").value(DEFAULT_DATA_HORA_EVENTO.toString()))
            .andExpect(jsonPath("$.tipoEvento").value(DEFAULT_TIPO_EVENTO.toString()))
            .andExpect(jsonPath("$.tipoLocal").value(DEFAULT_TIPO_LOCAL.toString()))
            .andExpect(jsonPath("$.coordenadaLocal").value(DEFAULT_COORDENADA_LOCAL))
            .andExpect(jsonPath("$.detalhesLocal").value(DEFAULT_DETALHES_LOCAL.toString()))
            .andExpect(jsonPath("$.dataHoraCriacaoRegistro").value(DEFAULT_DATA_HORA_CRIACAO_REGISTRO.toString()))
            .andExpect(jsonPath("$.descricaoEvento").value(DEFAULT_DESCRICAO_EVENTO.toString()))
            .andExpect(jsonPath("$.formaFixacao").value(DEFAULT_FORMA_FIXACAO.toString()))
            .andExpect(jsonPath("$.metodoCoercao").value(DEFAULT_METODO_COERCAO.toString()))
            .andExpect(jsonPath("$.numEnvolvidosAssaltantes").value(DEFAULT_NUM_ENVOLVIDOS_ASSALTANTES))
            .andExpect(jsonPath("$.numEnvolvidosVitimas").value(DEFAULT_NUM_ENVOLVIDOS_VITIMAS));
    }

    @Test
    @Transactional
    void getNonExistingEvento() throws Exception {
        // Get the evento
        restEventoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento
        Evento updatedEvento = eventoRepository.findById(evento.getId()).get();
        // Disconnect from session so that the updates on updatedEvento are not directly saved in db
        em.detach(updatedEvento);
        updatedEvento
            .dataHoraEvento(UPDATED_DATA_HORA_EVENTO)
            .tipoEvento(UPDATED_TIPO_EVENTO)
            .tipoLocal(UPDATED_TIPO_LOCAL)
            .coordenadaLocal(UPDATED_COORDENADA_LOCAL)
            .detalhesLocal(UPDATED_DETALHES_LOCAL)
            .dataHoraCriacaoRegistro(UPDATED_DATA_HORA_CRIACAO_REGISTRO)
            .descricaoEvento(UPDATED_DESCRICAO_EVENTO)
            .formaFixacao(UPDATED_FORMA_FIXACAO)
            .metodoCoercao(UPDATED_METODO_COERCAO)
            .numEnvolvidosAssaltantes(UPDATED_NUM_ENVOLVIDOS_ASSALTANTES)
            .numEnvolvidosVitimas(UPDATED_NUM_ENVOLVIDOS_VITIMAS);

        restEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEvento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEvento))
            )
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getDataHoraEvento()).isEqualTo(UPDATED_DATA_HORA_EVENTO);
        assertThat(testEvento.getTipoEvento()).isEqualTo(UPDATED_TIPO_EVENTO);
        assertThat(testEvento.getTipoLocal()).isEqualTo(UPDATED_TIPO_LOCAL);
        assertThat(testEvento.getCoordenadaLocal()).isEqualTo(UPDATED_COORDENADA_LOCAL);
        assertThat(testEvento.getDetalhesLocal()).isEqualTo(UPDATED_DETALHES_LOCAL);
        assertThat(testEvento.getDataHoraCriacaoRegistro()).isEqualTo(UPDATED_DATA_HORA_CRIACAO_REGISTRO);
        assertThat(testEvento.getDescricaoEvento()).isEqualTo(UPDATED_DESCRICAO_EVENTO);
        assertThat(testEvento.getFormaFixacao()).isEqualTo(UPDATED_FORMA_FIXACAO);
        assertThat(testEvento.getMetodoCoercao()).isEqualTo(UPDATED_METODO_COERCAO);
        assertThat(testEvento.getNumEnvolvidosAssaltantes()).isEqualTo(UPDATED_NUM_ENVOLVIDOS_ASSALTANTES);
        assertThat(testEvento.getNumEnvolvidosVitimas()).isEqualTo(UPDATED_NUM_ENVOLVIDOS_VITIMAS);
    }

    @Test
    @Transactional
    void putNonExistingEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventoWithPatch() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento using partial update
        Evento partialUpdatedEvento = new Evento();
        partialUpdatedEvento.setId(evento.getId());

        partialUpdatedEvento
            .dataHoraEvento(UPDATED_DATA_HORA_EVENTO)
            .tipoEvento(UPDATED_TIPO_EVENTO)
            .coordenadaLocal(UPDATED_COORDENADA_LOCAL)
            .dataHoraCriacaoRegistro(UPDATED_DATA_HORA_CRIACAO_REGISTRO)
            .descricaoEvento(UPDATED_DESCRICAO_EVENTO)
            .formaFixacao(UPDATED_FORMA_FIXACAO)
            .numEnvolvidosAssaltantes(UPDATED_NUM_ENVOLVIDOS_ASSALTANTES)
            .numEnvolvidosVitimas(UPDATED_NUM_ENVOLVIDOS_VITIMAS);

        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvento))
            )
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getDataHoraEvento()).isEqualTo(UPDATED_DATA_HORA_EVENTO);
        assertThat(testEvento.getTipoEvento()).isEqualTo(UPDATED_TIPO_EVENTO);
        assertThat(testEvento.getTipoLocal()).isEqualTo(DEFAULT_TIPO_LOCAL);
        assertThat(testEvento.getCoordenadaLocal()).isEqualTo(UPDATED_COORDENADA_LOCAL);
        assertThat(testEvento.getDetalhesLocal()).isEqualTo(DEFAULT_DETALHES_LOCAL);
        assertThat(testEvento.getDataHoraCriacaoRegistro()).isEqualTo(UPDATED_DATA_HORA_CRIACAO_REGISTRO);
        assertThat(testEvento.getDescricaoEvento()).isEqualTo(UPDATED_DESCRICAO_EVENTO);
        assertThat(testEvento.getFormaFixacao()).isEqualTo(UPDATED_FORMA_FIXACAO);
        assertThat(testEvento.getMetodoCoercao()).isEqualTo(DEFAULT_METODO_COERCAO);
        assertThat(testEvento.getNumEnvolvidosAssaltantes()).isEqualTo(UPDATED_NUM_ENVOLVIDOS_ASSALTANTES);
        assertThat(testEvento.getNumEnvolvidosVitimas()).isEqualTo(UPDATED_NUM_ENVOLVIDOS_VITIMAS);
    }

    @Test
    @Transactional
    void fullUpdateEventoWithPatch() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento using partial update
        Evento partialUpdatedEvento = new Evento();
        partialUpdatedEvento.setId(evento.getId());

        partialUpdatedEvento
            .dataHoraEvento(UPDATED_DATA_HORA_EVENTO)
            .tipoEvento(UPDATED_TIPO_EVENTO)
            .tipoLocal(UPDATED_TIPO_LOCAL)
            .coordenadaLocal(UPDATED_COORDENADA_LOCAL)
            .detalhesLocal(UPDATED_DETALHES_LOCAL)
            .dataHoraCriacaoRegistro(UPDATED_DATA_HORA_CRIACAO_REGISTRO)
            .descricaoEvento(UPDATED_DESCRICAO_EVENTO)
            .formaFixacao(UPDATED_FORMA_FIXACAO)
            .metodoCoercao(UPDATED_METODO_COERCAO)
            .numEnvolvidosAssaltantes(UPDATED_NUM_ENVOLVIDOS_ASSALTANTES)
            .numEnvolvidosVitimas(UPDATED_NUM_ENVOLVIDOS_VITIMAS);

        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvento))
            )
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getDataHoraEvento()).isEqualTo(UPDATED_DATA_HORA_EVENTO);
        assertThat(testEvento.getTipoEvento()).isEqualTo(UPDATED_TIPO_EVENTO);
        assertThat(testEvento.getTipoLocal()).isEqualTo(UPDATED_TIPO_LOCAL);
        assertThat(testEvento.getCoordenadaLocal()).isEqualTo(UPDATED_COORDENADA_LOCAL);
        assertThat(testEvento.getDetalhesLocal()).isEqualTo(UPDATED_DETALHES_LOCAL);
        assertThat(testEvento.getDataHoraCriacaoRegistro()).isEqualTo(UPDATED_DATA_HORA_CRIACAO_REGISTRO);
        assertThat(testEvento.getDescricaoEvento()).isEqualTo(UPDATED_DESCRICAO_EVENTO);
        assertThat(testEvento.getFormaFixacao()).isEqualTo(UPDATED_FORMA_FIXACAO);
        assertThat(testEvento.getMetodoCoercao()).isEqualTo(UPDATED_METODO_COERCAO);
        assertThat(testEvento.getNumEnvolvidosAssaltantes()).isEqualTo(UPDATED_NUM_ENVOLVIDOS_ASSALTANTES);
        assertThat(testEvento.getNumEnvolvidosVitimas()).isEqualTo(UPDATED_NUM_ENVOLVIDOS_VITIMAS);
    }

    @Test
    @Transactional
    void patchNonExistingEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeDelete = eventoRepository.findAll().size();

        // Delete the evento
        restEventoMockMvc
            .perform(delete(ENTITY_API_URL_ID, evento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
