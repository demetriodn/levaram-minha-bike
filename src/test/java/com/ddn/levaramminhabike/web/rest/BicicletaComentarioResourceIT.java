package com.ddn.levaramminhabike.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ddn.levaramminhabike.IntegrationTest;
import com.ddn.levaramminhabike.domain.BicicletaComentario;
import com.ddn.levaramminhabike.repository.BicicletaComentarioRepository;
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

/**
 * Integration tests for the {@link BicicletaComentarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BicicletaComentarioResourceIT {

    private static final String DEFAULT_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bicicleta-comentarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BicicletaComentarioRepository bicicletaComentarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBicicletaComentarioMockMvc;

    private BicicletaComentario bicicletaComentario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BicicletaComentario createEntity(EntityManager em) {
        BicicletaComentario bicicletaComentario = new BicicletaComentario().observacao(DEFAULT_OBSERVACAO);
        return bicicletaComentario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BicicletaComentario createUpdatedEntity(EntityManager em) {
        BicicletaComentario bicicletaComentario = new BicicletaComentario().observacao(UPDATED_OBSERVACAO);
        return bicicletaComentario;
    }

    @BeforeEach
    public void initTest() {
        bicicletaComentario = createEntity(em);
    }

    @Test
    @Transactional
    void createBicicletaComentario() throws Exception {
        int databaseSizeBeforeCreate = bicicletaComentarioRepository.findAll().size();
        // Create the BicicletaComentario
        restBicicletaComentarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isCreated());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeCreate + 1);
        BicicletaComentario testBicicletaComentario = bicicletaComentarioList.get(bicicletaComentarioList.size() - 1);
        assertThat(testBicicletaComentario.getObservacao()).isEqualTo(DEFAULT_OBSERVACAO);
    }

    @Test
    @Transactional
    void createBicicletaComentarioWithExistingId() throws Exception {
        // Create the BicicletaComentario with an existing ID
        bicicletaComentario.setId(1L);

        int databaseSizeBeforeCreate = bicicletaComentarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBicicletaComentarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBicicletaComentarios() throws Exception {
        // Initialize the database
        bicicletaComentarioRepository.saveAndFlush(bicicletaComentario);

        // Get all the bicicletaComentarioList
        restBicicletaComentarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bicicletaComentario.getId().intValue())))
            .andExpect(jsonPath("$.[*].observacao").value(hasItem(DEFAULT_OBSERVACAO)));
    }

    @Test
    @Transactional
    void getBicicletaComentario() throws Exception {
        // Initialize the database
        bicicletaComentarioRepository.saveAndFlush(bicicletaComentario);

        // Get the bicicletaComentario
        restBicicletaComentarioMockMvc
            .perform(get(ENTITY_API_URL_ID, bicicletaComentario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bicicletaComentario.getId().intValue()))
            .andExpect(jsonPath("$.observacao").value(DEFAULT_OBSERVACAO));
    }

    @Test
    @Transactional
    void getNonExistingBicicletaComentario() throws Exception {
        // Get the bicicletaComentario
        restBicicletaComentarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBicicletaComentario() throws Exception {
        // Initialize the database
        bicicletaComentarioRepository.saveAndFlush(bicicletaComentario);

        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();

        // Update the bicicletaComentario
        BicicletaComentario updatedBicicletaComentario = bicicletaComentarioRepository.findById(bicicletaComentario.getId()).get();
        // Disconnect from session so that the updates on updatedBicicletaComentario are not directly saved in db
        em.detach(updatedBicicletaComentario);
        updatedBicicletaComentario.observacao(UPDATED_OBSERVACAO);

        restBicicletaComentarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBicicletaComentario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBicicletaComentario))
            )
            .andExpect(status().isOk());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
        BicicletaComentario testBicicletaComentario = bicicletaComentarioList.get(bicicletaComentarioList.size() - 1);
        assertThat(testBicicletaComentario.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void putNonExistingBicicletaComentario() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();
        bicicletaComentario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBicicletaComentarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bicicletaComentario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBicicletaComentario() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();
        bicicletaComentario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaComentarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBicicletaComentario() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();
        bicicletaComentario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaComentarioMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBicicletaComentarioWithPatch() throws Exception {
        // Initialize the database
        bicicletaComentarioRepository.saveAndFlush(bicicletaComentario);

        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();

        // Update the bicicletaComentario using partial update
        BicicletaComentario partialUpdatedBicicletaComentario = new BicicletaComentario();
        partialUpdatedBicicletaComentario.setId(bicicletaComentario.getId());

        partialUpdatedBicicletaComentario.observacao(UPDATED_OBSERVACAO);

        restBicicletaComentarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBicicletaComentario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBicicletaComentario))
            )
            .andExpect(status().isOk());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
        BicicletaComentario testBicicletaComentario = bicicletaComentarioList.get(bicicletaComentarioList.size() - 1);
        assertThat(testBicicletaComentario.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void fullUpdateBicicletaComentarioWithPatch() throws Exception {
        // Initialize the database
        bicicletaComentarioRepository.saveAndFlush(bicicletaComentario);

        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();

        // Update the bicicletaComentario using partial update
        BicicletaComentario partialUpdatedBicicletaComentario = new BicicletaComentario();
        partialUpdatedBicicletaComentario.setId(bicicletaComentario.getId());

        partialUpdatedBicicletaComentario.observacao(UPDATED_OBSERVACAO);

        restBicicletaComentarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBicicletaComentario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBicicletaComentario))
            )
            .andExpect(status().isOk());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
        BicicletaComentario testBicicletaComentario = bicicletaComentarioList.get(bicicletaComentarioList.size() - 1);
        assertThat(testBicicletaComentario.getObservacao()).isEqualTo(UPDATED_OBSERVACAO);
    }

    @Test
    @Transactional
    void patchNonExistingBicicletaComentario() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();
        bicicletaComentario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBicicletaComentarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bicicletaComentario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBicicletaComentario() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();
        bicicletaComentario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaComentarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBicicletaComentario() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaComentarioRepository.findAll().size();
        bicicletaComentario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaComentarioMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaComentario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BicicletaComentario in the database
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBicicletaComentario() throws Exception {
        // Initialize the database
        bicicletaComentarioRepository.saveAndFlush(bicicletaComentario);

        int databaseSizeBeforeDelete = bicicletaComentarioRepository.findAll().size();

        // Delete the bicicletaComentario
        restBicicletaComentarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, bicicletaComentario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BicicletaComentario> bicicletaComentarioList = bicicletaComentarioRepository.findAll();
        assertThat(bicicletaComentarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
