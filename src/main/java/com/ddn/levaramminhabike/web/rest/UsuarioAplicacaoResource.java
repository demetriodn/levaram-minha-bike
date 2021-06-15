package com.ddn.levaramminhabike.web.rest;

import com.ddn.levaramminhabike.domain.UsuarioAplicacao;
import com.ddn.levaramminhabike.repository.UsuarioAplicacaoRepository;
import com.ddn.levaramminhabike.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ddn.levaramminhabike.domain.UsuarioAplicacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UsuarioAplicacaoResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioAplicacaoResource.class);

    private static final String ENTITY_NAME = "usuarioAplicacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioAplicacaoRepository usuarioAplicacaoRepository;

    public UsuarioAplicacaoResource(UsuarioAplicacaoRepository usuarioAplicacaoRepository) {
        this.usuarioAplicacaoRepository = usuarioAplicacaoRepository;
    }

    /**
     * {@code POST  /usuario-aplicacaos} : Create a new usuarioAplicacao.
     *
     * @param usuarioAplicacao the usuarioAplicacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioAplicacao, or with status {@code 400 (Bad Request)} if the usuarioAplicacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usuario-aplicacaos")
    public ResponseEntity<UsuarioAplicacao> createUsuarioAplicacao(@Valid @RequestBody UsuarioAplicacao usuarioAplicacao)
        throws URISyntaxException {
        log.debug("REST request to save UsuarioAplicacao : {}", usuarioAplicacao);
        if (usuarioAplicacao.getId() != null) {
            throw new BadRequestAlertException("A new usuarioAplicacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioAplicacao result = usuarioAplicacaoRepository.save(usuarioAplicacao);
        return ResponseEntity
            .created(new URI("/api/usuario-aplicacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usuario-aplicacaos/:id} : Updates an existing usuarioAplicacao.
     *
     * @param id the id of the usuarioAplicacao to save.
     * @param usuarioAplicacao the usuarioAplicacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioAplicacao,
     * or with status {@code 400 (Bad Request)} if the usuarioAplicacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioAplicacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usuario-aplicacaos/{id}")
    public ResponseEntity<UsuarioAplicacao> updateUsuarioAplicacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioAplicacao usuarioAplicacao
    ) throws URISyntaxException {
        log.debug("REST request to update UsuarioAplicacao : {}, {}", id, usuarioAplicacao);
        if (usuarioAplicacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioAplicacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioAplicacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UsuarioAplicacao result = usuarioAplicacaoRepository.save(usuarioAplicacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioAplicacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /usuario-aplicacaos/:id} : Partial updates given fields of an existing usuarioAplicacao, field will ignore if it is null
     *
     * @param id the id of the usuarioAplicacao to save.
     * @param usuarioAplicacao the usuarioAplicacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioAplicacao,
     * or with status {@code 400 (Bad Request)} if the usuarioAplicacao is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioAplicacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioAplicacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/usuario-aplicacaos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UsuarioAplicacao> partialUpdateUsuarioAplicacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsuarioAplicacao usuarioAplicacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsuarioAplicacao partially : {}, {}", id, usuarioAplicacao);
        if (usuarioAplicacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioAplicacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioAplicacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioAplicacao> result = usuarioAplicacaoRepository
            .findById(usuarioAplicacao.getId())
            .map(
                existingUsuarioAplicacao -> {
                    if (usuarioAplicacao.getContato() != null) {
                        existingUsuarioAplicacao.setContato(usuarioAplicacao.getContato());
                    }

                    return existingUsuarioAplicacao;
                }
            )
            .map(usuarioAplicacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioAplicacao.getId().toString())
        );
    }

    /**
     * {@code GET  /usuario-aplicacaos} : get all the usuarioAplicacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioAplicacaos in body.
     */
    @GetMapping("/usuario-aplicacaos")
    public List<UsuarioAplicacao> getAllUsuarioAplicacaos() {
        log.debug("REST request to get all UsuarioAplicacaos");
        return usuarioAplicacaoRepository.findAll();
    }

    /**
     * {@code GET  /usuario-aplicacaos/:id} : get the "id" usuarioAplicacao.
     *
     * @param id the id of the usuarioAplicacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioAplicacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usuario-aplicacaos/{id}")
    public ResponseEntity<UsuarioAplicacao> getUsuarioAplicacao(@PathVariable Long id) {
        log.debug("REST request to get UsuarioAplicacao : {}", id);
        Optional<UsuarioAplicacao> usuarioAplicacao = usuarioAplicacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(usuarioAplicacao);
    }

    /**
     * {@code DELETE  /usuario-aplicacaos/:id} : delete the "id" usuarioAplicacao.
     *
     * @param id the id of the usuarioAplicacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usuario-aplicacaos/{id}")
    public ResponseEntity<Void> deleteUsuarioAplicacao(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioAplicacao : {}", id);
        usuarioAplicacaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
