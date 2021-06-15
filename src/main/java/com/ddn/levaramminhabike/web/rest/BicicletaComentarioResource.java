package com.ddn.levaramminhabike.web.rest;

import com.ddn.levaramminhabike.domain.BicicletaComentario;
import com.ddn.levaramminhabike.repository.BicicletaComentarioRepository;
import com.ddn.levaramminhabike.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ddn.levaramminhabike.domain.BicicletaComentario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BicicletaComentarioResource {

    private final Logger log = LoggerFactory.getLogger(BicicletaComentarioResource.class);

    private static final String ENTITY_NAME = "bicicletaComentario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BicicletaComentarioRepository bicicletaComentarioRepository;

    public BicicletaComentarioResource(BicicletaComentarioRepository bicicletaComentarioRepository) {
        this.bicicletaComentarioRepository = bicicletaComentarioRepository;
    }

    /**
     * {@code POST  /bicicleta-comentarios} : Create a new bicicletaComentario.
     *
     * @param bicicletaComentario the bicicletaComentario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bicicletaComentario, or with status {@code 400 (Bad Request)} if the bicicletaComentario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bicicleta-comentarios")
    public ResponseEntity<BicicletaComentario> createBicicletaComentario(@RequestBody BicicletaComentario bicicletaComentario)
        throws URISyntaxException {
        log.debug("REST request to save BicicletaComentario : {}", bicicletaComentario);
        if (bicicletaComentario.getId() != null) {
            throw new BadRequestAlertException("A new bicicletaComentario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BicicletaComentario result = bicicletaComentarioRepository.save(bicicletaComentario);
        return ResponseEntity
            .created(new URI("/api/bicicleta-comentarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bicicleta-comentarios/:id} : Updates an existing bicicletaComentario.
     *
     * @param id the id of the bicicletaComentario to save.
     * @param bicicletaComentario the bicicletaComentario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bicicletaComentario,
     * or with status {@code 400 (Bad Request)} if the bicicletaComentario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bicicletaComentario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bicicleta-comentarios/{id}")
    public ResponseEntity<BicicletaComentario> updateBicicletaComentario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BicicletaComentario bicicletaComentario
    ) throws URISyntaxException {
        log.debug("REST request to update BicicletaComentario : {}, {}", id, bicicletaComentario);
        if (bicicletaComentario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bicicletaComentario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bicicletaComentarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BicicletaComentario result = bicicletaComentarioRepository.save(bicicletaComentario);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bicicletaComentario.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bicicleta-comentarios/:id} : Partial updates given fields of an existing bicicletaComentario, field will ignore if it is null
     *
     * @param id the id of the bicicletaComentario to save.
     * @param bicicletaComentario the bicicletaComentario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bicicletaComentario,
     * or with status {@code 400 (Bad Request)} if the bicicletaComentario is not valid,
     * or with status {@code 404 (Not Found)} if the bicicletaComentario is not found,
     * or with status {@code 500 (Internal Server Error)} if the bicicletaComentario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bicicleta-comentarios/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BicicletaComentario> partialUpdateBicicletaComentario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BicicletaComentario bicicletaComentario
    ) throws URISyntaxException {
        log.debug("REST request to partial update BicicletaComentario partially : {}, {}", id, bicicletaComentario);
        if (bicicletaComentario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bicicletaComentario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bicicletaComentarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BicicletaComentario> result = bicicletaComentarioRepository
            .findById(bicicletaComentario.getId())
            .map(
                existingBicicletaComentario -> {
                    if (bicicletaComentario.getObservacao() != null) {
                        existingBicicletaComentario.setObservacao(bicicletaComentario.getObservacao());
                    }

                    return existingBicicletaComentario;
                }
            )
            .map(bicicletaComentarioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bicicletaComentario.getId().toString())
        );
    }

    /**
     * {@code GET  /bicicleta-comentarios} : get all the bicicletaComentarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bicicletaComentarios in body.
     */
    @GetMapping("/bicicleta-comentarios")
    public List<BicicletaComentario> getAllBicicletaComentarios() {
        log.debug("REST request to get all BicicletaComentarios");
        return bicicletaComentarioRepository.findAll();
    }

    /**
     * {@code GET  /bicicleta-comentarios/:id} : get the "id" bicicletaComentario.
     *
     * @param id the id of the bicicletaComentario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bicicletaComentario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bicicleta-comentarios/{id}")
    public ResponseEntity<BicicletaComentario> getBicicletaComentario(@PathVariable Long id) {
        log.debug("REST request to get BicicletaComentario : {}", id);
        Optional<BicicletaComentario> bicicletaComentario = bicicletaComentarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bicicletaComentario);
    }

    /**
     * {@code DELETE  /bicicleta-comentarios/:id} : delete the "id" bicicletaComentario.
     *
     * @param id the id of the bicicletaComentario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bicicleta-comentarios/{id}")
    public ResponseEntity<Void> deleteBicicletaComentario(@PathVariable Long id) {
        log.debug("REST request to delete BicicletaComentario : {}", id);
        bicicletaComentarioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
