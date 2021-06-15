package com.ddn.levaramminhabike.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ddn.levaramminhabike.IntegrationTest;
import com.ddn.levaramminhabike.domain.UsuarioAplicacao;
import com.ddn.levaramminhabike.repository.UsuarioAplicacaoRepository;
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
 * Integration tests for the {@link UsuarioAplicacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsuarioAplicacaoResourceIT {

    private static final String DEFAULT_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_CONTATO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/usuario-aplicacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsuarioAplicacaoRepository usuarioAplicacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioAplicacaoMockMvc;

    private UsuarioAplicacao usuarioAplicacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioAplicacao createEntity(EntityManager em) {
        UsuarioAplicacao usuarioAplicacao = new UsuarioAplicacao().contato(DEFAULT_CONTATO);
        return usuarioAplicacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarioAplicacao createUpdatedEntity(EntityManager em) {
        UsuarioAplicacao usuarioAplicacao = new UsuarioAplicacao().contato(UPDATED_CONTATO);
        return usuarioAplicacao;
    }

    @BeforeEach
    public void initTest() {
        usuarioAplicacao = createEntity(em);
    }

    @Test
    @Transactional
    void createUsuarioAplicacao() throws Exception {
        int databaseSizeBeforeCreate = usuarioAplicacaoRepository.findAll().size();
        // Create the UsuarioAplicacao
        restUsuarioAplicacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isCreated());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioAplicacao testUsuarioAplicacao = usuarioAplicacaoList.get(usuarioAplicacaoList.size() - 1);
        assertThat(testUsuarioAplicacao.getContato()).isEqualTo(DEFAULT_CONTATO);
    }

    @Test
    @Transactional
    void createUsuarioAplicacaoWithExistingId() throws Exception {
        // Create the UsuarioAplicacao with an existing ID
        usuarioAplicacao.setId(1L);

        int databaseSizeBeforeCreate = usuarioAplicacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioAplicacaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUsuarioAplicacaos() throws Exception {
        // Initialize the database
        usuarioAplicacaoRepository.saveAndFlush(usuarioAplicacao);

        // Get all the usuarioAplicacaoList
        restUsuarioAplicacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioAplicacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].contato").value(hasItem(DEFAULT_CONTATO)));
    }

    @Test
    @Transactional
    void getUsuarioAplicacao() throws Exception {
        // Initialize the database
        usuarioAplicacaoRepository.saveAndFlush(usuarioAplicacao);

        // Get the usuarioAplicacao
        restUsuarioAplicacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, usuarioAplicacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioAplicacao.getId().intValue()))
            .andExpect(jsonPath("$.contato").value(DEFAULT_CONTATO));
    }

    @Test
    @Transactional
    void getNonExistingUsuarioAplicacao() throws Exception {
        // Get the usuarioAplicacao
        restUsuarioAplicacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsuarioAplicacao() throws Exception {
        // Initialize the database
        usuarioAplicacaoRepository.saveAndFlush(usuarioAplicacao);

        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();

        // Update the usuarioAplicacao
        UsuarioAplicacao updatedUsuarioAplicacao = usuarioAplicacaoRepository.findById(usuarioAplicacao.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioAplicacao are not directly saved in db
        em.detach(updatedUsuarioAplicacao);
        updatedUsuarioAplicacao.contato(UPDATED_CONTATO);

        restUsuarioAplicacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUsuarioAplicacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUsuarioAplicacao))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
        UsuarioAplicacao testUsuarioAplicacao = usuarioAplicacaoList.get(usuarioAplicacaoList.size() - 1);
        assertThat(testUsuarioAplicacao.getContato()).isEqualTo(UPDATED_CONTATO);
    }

    @Test
    @Transactional
    void putNonExistingUsuarioAplicacao() throws Exception {
        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();
        usuarioAplicacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioAplicacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioAplicacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuarioAplicacao() throws Exception {
        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();
        usuarioAplicacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioAplicacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuarioAplicacao() throws Exception {
        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();
        usuarioAplicacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioAplicacaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioAplicacaoWithPatch() throws Exception {
        // Initialize the database
        usuarioAplicacaoRepository.saveAndFlush(usuarioAplicacao);

        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();

        // Update the usuarioAplicacao using partial update
        UsuarioAplicacao partialUpdatedUsuarioAplicacao = new UsuarioAplicacao();
        partialUpdatedUsuarioAplicacao.setId(usuarioAplicacao.getId());

        partialUpdatedUsuarioAplicacao.contato(UPDATED_CONTATO);

        restUsuarioAplicacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioAplicacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarioAplicacao))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
        UsuarioAplicacao testUsuarioAplicacao = usuarioAplicacaoList.get(usuarioAplicacaoList.size() - 1);
        assertThat(testUsuarioAplicacao.getContato()).isEqualTo(UPDATED_CONTATO);
    }

    @Test
    @Transactional
    void fullUpdateUsuarioAplicacaoWithPatch() throws Exception {
        // Initialize the database
        usuarioAplicacaoRepository.saveAndFlush(usuarioAplicacao);

        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();

        // Update the usuarioAplicacao using partial update
        UsuarioAplicacao partialUpdatedUsuarioAplicacao = new UsuarioAplicacao();
        partialUpdatedUsuarioAplicacao.setId(usuarioAplicacao.getId());

        partialUpdatedUsuarioAplicacao.contato(UPDATED_CONTATO);

        restUsuarioAplicacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuarioAplicacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuarioAplicacao))
            )
            .andExpect(status().isOk());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
        UsuarioAplicacao testUsuarioAplicacao = usuarioAplicacaoList.get(usuarioAplicacaoList.size() - 1);
        assertThat(testUsuarioAplicacao.getContato()).isEqualTo(UPDATED_CONTATO);
    }

    @Test
    @Transactional
    void patchNonExistingUsuarioAplicacao() throws Exception {
        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();
        usuarioAplicacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioAplicacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioAplicacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuarioAplicacao() throws Exception {
        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();
        usuarioAplicacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioAplicacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuarioAplicacao() throws Exception {
        int databaseSizeBeforeUpdate = usuarioAplicacaoRepository.findAll().size();
        usuarioAplicacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioAplicacaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioAplicacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsuarioAplicacao in the database
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuarioAplicacao() throws Exception {
        // Initialize the database
        usuarioAplicacaoRepository.saveAndFlush(usuarioAplicacao);

        int databaseSizeBeforeDelete = usuarioAplicacaoRepository.findAll().size();

        // Delete the usuarioAplicacao
        restUsuarioAplicacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuarioAplicacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsuarioAplicacao> usuarioAplicacaoList = usuarioAplicacaoRepository.findAll();
        assertThat(usuarioAplicacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
