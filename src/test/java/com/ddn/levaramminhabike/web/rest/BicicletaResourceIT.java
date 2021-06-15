package com.ddn.levaramminhabike.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ddn.levaramminhabike.IntegrationTest;
import com.ddn.levaramminhabike.domain.Bicicleta;
import com.ddn.levaramminhabike.domain.enumeration.StatusBicicleta;
import com.ddn.levaramminhabike.repository.BicicletaRepository;
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
 * Integration tests for the {@link BicicletaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BicicletaResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final StatusBicicleta DEFAULT_STATUS = StatusBicicleta.PERDIDA;
    private static final StatusBicicleta UPDATED_STATUS = StatusBicicleta.ENCONTRADA;

    private static final String DEFAULT_NUMERO_QUADRO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_QUADRO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_BIKE_REGISTRADA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_BIKE_REGISTRADA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bicicletas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BicicletaRepository bicicletaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBicicletaMockMvc;

    private Bicicleta bicicleta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bicicleta createEntity(EntityManager em) {
        Bicicleta bicicleta = new Bicicleta()
            .descricao(DEFAULT_DESCRICAO)
            .status(DEFAULT_STATUS)
            .numeroQuadro(DEFAULT_NUMERO_QUADRO)
            .numeroBikeRegistrada(DEFAULT_NUMERO_BIKE_REGISTRADA);
        return bicicleta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bicicleta createUpdatedEntity(EntityManager em) {
        Bicicleta bicicleta = new Bicicleta()
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .numeroQuadro(UPDATED_NUMERO_QUADRO)
            .numeroBikeRegistrada(UPDATED_NUMERO_BIKE_REGISTRADA);
        return bicicleta;
    }

    @BeforeEach
    public void initTest() {
        bicicleta = createEntity(em);
    }

    @Test
    @Transactional
    void createBicicleta() throws Exception {
        int databaseSizeBeforeCreate = bicicletaRepository.findAll().size();
        // Create the Bicicleta
        restBicicletaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicleta)))
            .andExpect(status().isCreated());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeCreate + 1);
        Bicicleta testBicicleta = bicicletaList.get(bicicletaList.size() - 1);
        assertThat(testBicicleta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBicicleta.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBicicleta.getNumeroQuadro()).isEqualTo(DEFAULT_NUMERO_QUADRO);
        assertThat(testBicicleta.getNumeroBikeRegistrada()).isEqualTo(DEFAULT_NUMERO_BIKE_REGISTRADA);
    }

    @Test
    @Transactional
    void createBicicletaWithExistingId() throws Exception {
        // Create the Bicicleta with an existing ID
        bicicleta.setId(1L);

        int databaseSizeBeforeCreate = bicicletaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBicicletaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicleta)))
            .andExpect(status().isBadRequest());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = bicicletaRepository.findAll().size();
        // set the field null
        bicicleta.setStatus(null);

        // Create the Bicicleta, which fails.

        restBicicletaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicleta)))
            .andExpect(status().isBadRequest());

        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBicicletas() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        // Get all the bicicletaList
        restBicicletaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bicicleta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].numeroQuadro").value(hasItem(DEFAULT_NUMERO_QUADRO)))
            .andExpect(jsonPath("$.[*].numeroBikeRegistrada").value(hasItem(DEFAULT_NUMERO_BIKE_REGISTRADA)));
    }

    @Test
    @Transactional
    void getBicicleta() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        // Get the bicicleta
        restBicicletaMockMvc
            .perform(get(ENTITY_API_URL_ID, bicicleta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bicicleta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.numeroQuadro").value(DEFAULT_NUMERO_QUADRO))
            .andExpect(jsonPath("$.numeroBikeRegistrada").value(DEFAULT_NUMERO_BIKE_REGISTRADA));
    }

    @Test
    @Transactional
    void getNonExistingBicicleta() throws Exception {
        // Get the bicicleta
        restBicicletaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBicicleta() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();

        // Update the bicicleta
        Bicicleta updatedBicicleta = bicicletaRepository.findById(bicicleta.getId()).get();
        // Disconnect from session so that the updates on updatedBicicleta are not directly saved in db
        em.detach(updatedBicicleta);
        updatedBicicleta
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .numeroQuadro(UPDATED_NUMERO_QUADRO)
            .numeroBikeRegistrada(UPDATED_NUMERO_BIKE_REGISTRADA);

        restBicicletaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBicicleta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBicicleta))
            )
            .andExpect(status().isOk());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
        Bicicleta testBicicleta = bicicletaList.get(bicicletaList.size() - 1);
        assertThat(testBicicleta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBicicleta.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBicicleta.getNumeroQuadro()).isEqualTo(UPDATED_NUMERO_QUADRO);
        assertThat(testBicicleta.getNumeroBikeRegistrada()).isEqualTo(UPDATED_NUMERO_BIKE_REGISTRADA);
    }

    @Test
    @Transactional
    void putNonExistingBicicleta() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();
        bicicleta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBicicletaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bicicleta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bicicleta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBicicleta() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();
        bicicleta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bicicleta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBicicleta() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();
        bicicleta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicleta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBicicletaWithPatch() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();

        // Update the bicicleta using partial update
        Bicicleta partialUpdatedBicicleta = new Bicicleta();
        partialUpdatedBicicleta.setId(bicicleta.getId());

        partialUpdatedBicicleta.numeroBikeRegistrada(UPDATED_NUMERO_BIKE_REGISTRADA);

        restBicicletaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBicicleta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBicicleta))
            )
            .andExpect(status().isOk());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
        Bicicleta testBicicleta = bicicletaList.get(bicicletaList.size() - 1);
        assertThat(testBicicleta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testBicicleta.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBicicleta.getNumeroQuadro()).isEqualTo(DEFAULT_NUMERO_QUADRO);
        assertThat(testBicicleta.getNumeroBikeRegistrada()).isEqualTo(UPDATED_NUMERO_BIKE_REGISTRADA);
    }

    @Test
    @Transactional
    void fullUpdateBicicletaWithPatch() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();

        // Update the bicicleta using partial update
        Bicicleta partialUpdatedBicicleta = new Bicicleta();
        partialUpdatedBicicleta.setId(bicicleta.getId());

        partialUpdatedBicicleta
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .numeroQuadro(UPDATED_NUMERO_QUADRO)
            .numeroBikeRegistrada(UPDATED_NUMERO_BIKE_REGISTRADA);

        restBicicletaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBicicleta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBicicleta))
            )
            .andExpect(status().isOk());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
        Bicicleta testBicicleta = bicicletaList.get(bicicletaList.size() - 1);
        assertThat(testBicicleta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testBicicleta.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBicicleta.getNumeroQuadro()).isEqualTo(UPDATED_NUMERO_QUADRO);
        assertThat(testBicicleta.getNumeroBikeRegistrada()).isEqualTo(UPDATED_NUMERO_BIKE_REGISTRADA);
    }

    @Test
    @Transactional
    void patchNonExistingBicicleta() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();
        bicicleta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBicicletaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bicicleta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bicicleta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBicicleta() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();
        bicicleta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bicicleta))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBicicleta() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaRepository.findAll().size();
        bicicleta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bicicleta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bicicleta in the database
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBicicleta() throws Exception {
        // Initialize the database
        bicicletaRepository.saveAndFlush(bicicleta);

        int databaseSizeBeforeDelete = bicicletaRepository.findAll().size();

        // Delete the bicicleta
        restBicicletaMockMvc
            .perform(delete(ENTITY_API_URL_ID, bicicleta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bicicleta> bicicletaList = bicicletaRepository.findAll();
        assertThat(bicicletaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
