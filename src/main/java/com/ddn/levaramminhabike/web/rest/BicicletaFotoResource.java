package com.ddn.levaramminhabike.web.rest;

import com.ddn.levaramminhabike.domain.BicicletaFoto;
import com.ddn.levaramminhabike.repository.BicicletaFotoRepository;
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
 * REST controller for managing {@link com.ddn.levaramminhabike.domain.BicicletaFoto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BicicletaFotoResource {

    private final Logger log = LoggerFactory.getLogger(BicicletaFotoResource.class);

    private static final String ENTITY_NAME = "bicicletaFoto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BicicletaFotoRepository bicicletaFotoRepository;

    public BicicletaFotoResource(BicicletaFotoRepository bicicletaFotoRepository) {
        this.bicicletaFotoRepository = bicicletaFotoRepository;
    }

    /**
     * {@code POST  /bicicleta-fotos} : Create a new bicicletaFoto.
     *
     * @param bicicletaFoto the bicicletaFoto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bicicletaFoto, or with status {@code 400 (Bad Request)} if the bicicletaFoto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bicicleta-fotos")
    public ResponseEntity<BicicletaFoto> createBicicletaFoto(@Valid @RequestBody BicicletaFoto bicicletaFoto) throws URISyntaxException {
        log.debug("REST request to save BicicletaFoto : {}", bicicletaFoto);
        if (bicicletaFoto.getId() != null) {
            throw new BadRequestAlertException("A new bicicletaFoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BicicletaFoto result = bicicletaFotoRepository.save(bicicletaFoto);
        return ResponseEntity
            .created(new URI("/api/bicicleta-fotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bicicleta-fotos/:id} : Updates an existing bicicletaFoto.
     *
     * @param id the id of the bicicletaFoto to save.
     * @param bicicletaFoto the bicicletaFoto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bicicletaFoto,
     * or with status {@code 400 (Bad Request)} if the bicicletaFoto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bicicletaFoto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bicicleta-fotos/{id}")
    public ResponseEntity<BicicletaFoto> updateBicicletaFoto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BicicletaFoto bicicletaFoto
    ) throws URISyntaxException {
        log.debug("REST request to update BicicletaFoto : {}, {}", id, bicicletaFoto);
        if (bicicletaFoto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bicicletaFoto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bicicletaFotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BicicletaFoto result = bicicletaFotoRepository.save(bicicletaFoto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bicicletaFoto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bicicleta-fotos/:id} : Partial updates given fields of an existing bicicletaFoto, field will ignore if it is null
     *
     * @param id the id of the bicicletaFoto to save.
     * @param bicicletaFoto the bicicletaFoto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bicicletaFoto,
     * or with status {@code 400 (Bad Request)} if the bicicletaFoto is not valid,
     * or with status {@code 404 (Not Found)} if the bicicletaFoto is not found,
     * or with status {@code 500 (Internal Server Error)} if the bicicletaFoto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bicicleta-fotos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BicicletaFoto> partialUpdateBicicletaFoto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BicicletaFoto bicicletaFoto
    ) throws URISyntaxException {
        log.debug("REST request to partial update BicicletaFoto partially : {}, {}", id, bicicletaFoto);
        if (bicicletaFoto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bicicletaFoto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bicicletaFotoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BicicletaFoto> result = bicicletaFotoRepository
            .findById(bicicletaFoto.getId())
            .map(
                existingBicicletaFoto -> {
                    if (bicicletaFoto.getUrlImagem() != null) {
                        existingBicicletaFoto.setUrlImagem(bicicletaFoto.getUrlImagem());
                    }

                    return existingBicicletaFoto;
                }
            )
            .map(bicicletaFotoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bicicletaFoto.getId().toString())
        );
    }

    /**
     * {@code GET  /bicicleta-fotos} : get all the bicicletaFotos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bicicletaFotos in body.
     */
    @GetMapping("/bicicleta-fotos")
    public List<BicicletaFoto> getAllBicicletaFotos() {
        log.debug("REST request to get all BicicletaFotos");
        return bicicletaFotoRepository.findAll();
    }

    /**
     * {@code GET  /bicicleta-fotos/:id} : get the "id" bicicletaFoto.
     *
     * @param id the id of the bicicletaFoto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bicicletaFoto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bicicleta-fotos/{id}")
    public ResponseEntity<BicicletaFoto> getBicicletaFoto(@PathVariable Long id) {
        log.debug("REST request to get BicicletaFoto : {}", id);
        Optional<BicicletaFoto> bicicletaFoto = bicicletaFotoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bicicletaFoto);
    }

    /**
     * {@code DELETE  /bicicleta-fotos/:id} : delete the "id" bicicletaFoto.
     *
     * @param id the id of the bicicletaFoto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bicicleta-fotos/{id}")
    public ResponseEntity<Void> deleteBicicletaFoto(@PathVariable Long id) {
        log.debug("REST request to delete BicicletaFoto : {}", id);
        bicicletaFotoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
