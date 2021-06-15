package com.ddn.levaramminhabike.web.rest;

import com.ddn.levaramminhabike.domain.Bicicleta;
import com.ddn.levaramminhabike.repository.BicicletaRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ddn.levaramminhabike.domain.Bicicleta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BicicletaResource {

    private final Logger log = LoggerFactory.getLogger(BicicletaResource.class);

    private static final String ENTITY_NAME = "bicicleta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BicicletaRepository bicicletaRepository;

    public BicicletaResource(BicicletaRepository bicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
    }

    /**
     * {@code POST  /bicicletas} : Create a new bicicleta.
     *
     * @param bicicleta the bicicleta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bicicleta, or with status {@code 400 (Bad Request)} if the bicicleta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bicicletas")
    public ResponseEntity<Bicicleta> createBicicleta(@Valid @RequestBody Bicicleta bicicleta) throws URISyntaxException {
        log.debug("REST request to save Bicicleta : {}", bicicleta);
        if (bicicleta.getId() != null) {
            throw new BadRequestAlertException("A new bicicleta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bicicleta result = bicicletaRepository.save(bicicleta);
        return ResponseEntity
            .created(new URI("/api/bicicletas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bicicletas/:id} : Updates an existing bicicleta.
     *
     * @param id the id of the bicicleta to save.
     * @param bicicleta the bicicleta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bicicleta,
     * or with status {@code 400 (Bad Request)} if the bicicleta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bicicleta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bicicletas/{id}")
    public ResponseEntity<Bicicleta> updateBicicleta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bicicleta bicicleta
    ) throws URISyntaxException {
        log.debug("REST request to update Bicicleta : {}, {}", id, bicicleta);
        if (bicicleta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bicicleta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bicicletaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bicicleta result = bicicletaRepository.save(bicicleta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bicicleta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bicicletas/:id} : Partial updates given fields of an existing bicicleta, field will ignore if it is null
     *
     * @param id the id of the bicicleta to save.
     * @param bicicleta the bicicleta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bicicleta,
     * or with status {@code 400 (Bad Request)} if the bicicleta is not valid,
     * or with status {@code 404 (Not Found)} if the bicicleta is not found,
     * or with status {@code 500 (Internal Server Error)} if the bicicleta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bicicletas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Bicicleta> partialUpdateBicicleta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bicicleta bicicleta
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bicicleta partially : {}, {}", id, bicicleta);
        if (bicicleta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bicicleta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bicicletaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bicicleta> result = bicicletaRepository
            .findById(bicicleta.getId())
            .map(
                existingBicicleta -> {
                    if (bicicleta.getDescricao() != null) {
                        existingBicicleta.setDescricao(bicicleta.getDescricao());
                    }
                    if (bicicleta.getStatus() != null) {
                        existingBicicleta.setStatus(bicicleta.getStatus());
                    }
                    if (bicicleta.getNumeroQuadro() != null) {
                        existingBicicleta.setNumeroQuadro(bicicleta.getNumeroQuadro());
                    }
                    if (bicicleta.getNumeroBikeRegistrada() != null) {
                        existingBicicleta.setNumeroBikeRegistrada(bicicleta.getNumeroBikeRegistrada());
                    }

                    return existingBicicleta;
                }
            )
            .map(bicicletaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bicicleta.getId().toString())
        );
    }

    /**
     * {@code GET  /bicicletas} : get all the bicicletas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bicicletas in body.
     */
    @GetMapping("/bicicletas")
    public ResponseEntity<List<Bicicleta>> getAllBicicletas(Pageable pageable) {
        log.debug("REST request to get a page of Bicicletas");
        Page<Bicicleta> page = bicicletaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bicicletas/:id} : get the "id" bicicleta.
     *
     * @param id the id of the bicicleta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bicicleta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bicicletas/{id}")
    public ResponseEntity<Bicicleta> getBicicleta(@PathVariable Long id) {
        log.debug("REST request to get Bicicleta : {}", id);
        Optional<Bicicleta> bicicleta = bicicletaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bicicleta);
    }

    /**
     * {@code DELETE  /bicicletas/:id} : delete the "id" bicicleta.
     *
     * @param id the id of the bicicleta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bicicletas/{id}")
    public ResponseEntity<Void> deleteBicicleta(@PathVariable Long id) {
        log.debug("REST request to delete Bicicleta : {}", id);
        bicicletaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
