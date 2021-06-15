package com.ddn.levaramminhabike.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ddn.levaramminhabike.IntegrationTest;
import com.ddn.levaramminhabike.domain.BicicletaFoto;
import com.ddn.levaramminhabike.repository.BicicletaFotoRepository;
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
 * Integration tests for the {@link BicicletaFotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BicicletaFotoResourceIT {

    private static final String DEFAULT_URL_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bicicleta-fotos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BicicletaFotoRepository bicicletaFotoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBicicletaFotoMockMvc;

    private BicicletaFoto bicicletaFoto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BicicletaFoto createEntity(EntityManager em) {
        BicicletaFoto bicicletaFoto = new BicicletaFoto().urlImagem(DEFAULT_URL_IMAGEM);
        return bicicletaFoto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BicicletaFoto createUpdatedEntity(EntityManager em) {
        BicicletaFoto bicicletaFoto = new BicicletaFoto().urlImagem(UPDATED_URL_IMAGEM);
        return bicicletaFoto;
    }

    @BeforeEach
    public void initTest() {
        bicicletaFoto = createEntity(em);
    }

    @Test
    @Transactional
    void createBicicletaFoto() throws Exception {
        int databaseSizeBeforeCreate = bicicletaFotoRepository.findAll().size();
        // Create the BicicletaFoto
        restBicicletaFotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicletaFoto)))
            .andExpect(status().isCreated());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeCreate + 1);
        BicicletaFoto testBicicletaFoto = bicicletaFotoList.get(bicicletaFotoList.size() - 1);
        assertThat(testBicicletaFoto.getUrlImagem()).isEqualTo(DEFAULT_URL_IMAGEM);
    }

    @Test
    @Transactional
    void createBicicletaFotoWithExistingId() throws Exception {
        // Create the BicicletaFoto with an existing ID
        bicicletaFoto.setId(1L);

        int databaseSizeBeforeCreate = bicicletaFotoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBicicletaFotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicletaFoto)))
            .andExpect(status().isBadRequest());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBicicletaFotos() throws Exception {
        // Initialize the database
        bicicletaFotoRepository.saveAndFlush(bicicletaFoto);

        // Get all the bicicletaFotoList
        restBicicletaFotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bicicletaFoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlImagem").value(hasItem(DEFAULT_URL_IMAGEM)));
    }

    @Test
    @Transactional
    void getBicicletaFoto() throws Exception {
        // Initialize the database
        bicicletaFotoRepository.saveAndFlush(bicicletaFoto);

        // Get the bicicletaFoto
        restBicicletaFotoMockMvc
            .perform(get(ENTITY_API_URL_ID, bicicletaFoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bicicletaFoto.getId().intValue()))
            .andExpect(jsonPath("$.urlImagem").value(DEFAULT_URL_IMAGEM));
    }

    @Test
    @Transactional
    void getNonExistingBicicletaFoto() throws Exception {
        // Get the bicicletaFoto
        restBicicletaFotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBicicletaFoto() throws Exception {
        // Initialize the database
        bicicletaFotoRepository.saveAndFlush(bicicletaFoto);

        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();

        // Update the bicicletaFoto
        BicicletaFoto updatedBicicletaFoto = bicicletaFotoRepository.findById(bicicletaFoto.getId()).get();
        // Disconnect from session so that the updates on updatedBicicletaFoto are not directly saved in db
        em.detach(updatedBicicletaFoto);
        updatedBicicletaFoto.urlImagem(UPDATED_URL_IMAGEM);

        restBicicletaFotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBicicletaFoto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBicicletaFoto))
            )
            .andExpect(status().isOk());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
        BicicletaFoto testBicicletaFoto = bicicletaFotoList.get(bicicletaFotoList.size() - 1);
        assertThat(testBicicletaFoto.getUrlImagem()).isEqualTo(UPDATED_URL_IMAGEM);
    }

    @Test
    @Transactional
    void putNonExistingBicicletaFoto() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();
        bicicletaFoto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBicicletaFotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bicicletaFoto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaFoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBicicletaFoto() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();
        bicicletaFoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaFotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaFoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBicicletaFoto() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();
        bicicletaFoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaFotoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bicicletaFoto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBicicletaFotoWithPatch() throws Exception {
        // Initialize the database
        bicicletaFotoRepository.saveAndFlush(bicicletaFoto);

        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();

        // Update the bicicletaFoto using partial update
        BicicletaFoto partialUpdatedBicicletaFoto = new BicicletaFoto();
        partialUpdatedBicicletaFoto.setId(bicicletaFoto.getId());

        restBicicletaFotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBicicletaFoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBicicletaFoto))
            )
            .andExpect(status().isOk());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
        BicicletaFoto testBicicletaFoto = bicicletaFotoList.get(bicicletaFotoList.size() - 1);
        assertThat(testBicicletaFoto.getUrlImagem()).isEqualTo(DEFAULT_URL_IMAGEM);
    }

    @Test
    @Transactional
    void fullUpdateBicicletaFotoWithPatch() throws Exception {
        // Initialize the database
        bicicletaFotoRepository.saveAndFlush(bicicletaFoto);

        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();

        // Update the bicicletaFoto using partial update
        BicicletaFoto partialUpdatedBicicletaFoto = new BicicletaFoto();
        partialUpdatedBicicletaFoto.setId(bicicletaFoto.getId());

        partialUpdatedBicicletaFoto.urlImagem(UPDATED_URL_IMAGEM);

        restBicicletaFotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBicicletaFoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBicicletaFoto))
            )
            .andExpect(status().isOk());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
        BicicletaFoto testBicicletaFoto = bicicletaFotoList.get(bicicletaFotoList.size() - 1);
        assertThat(testBicicletaFoto.getUrlImagem()).isEqualTo(UPDATED_URL_IMAGEM);
    }

    @Test
    @Transactional
    void patchNonExistingBicicletaFoto() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();
        bicicletaFoto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBicicletaFotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bicicletaFoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaFoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBicicletaFoto() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();
        bicicletaFoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaFotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bicicletaFoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBicicletaFoto() throws Exception {
        int databaseSizeBeforeUpdate = bicicletaFotoRepository.findAll().size();
        bicicletaFoto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBicicletaFotoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bicicletaFoto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BicicletaFoto in the database
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBicicletaFoto() throws Exception {
        // Initialize the database
        bicicletaFotoRepository.saveAndFlush(bicicletaFoto);

        int databaseSizeBeforeDelete = bicicletaFotoRepository.findAll().size();

        // Delete the bicicletaFoto
        restBicicletaFotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, bicicletaFoto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BicicletaFoto> bicicletaFotoList = bicicletaFotoRepository.findAll();
        assertThat(bicicletaFotoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
