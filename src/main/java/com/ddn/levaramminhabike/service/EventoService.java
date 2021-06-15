package com.ddn.levaramminhabike.service;

import com.ddn.levaramminhabike.domain.Evento;
import com.ddn.levaramminhabike.repository.EventoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Evento}.
 */
@Service
@Transactional
public class EventoService {

    private final Logger log = LoggerFactory.getLogger(EventoService.class);

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    /**
     * Save a evento.
     *
     * @param evento the entity to save.
     * @return the persisted entity.
     */
    public Evento save(Evento evento) {
        log.debug("Request to save Evento : {}", evento);
        return eventoRepository.save(evento);
    }

    /**
     * Partially update a evento.
     *
     * @param evento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Evento> partialUpdate(Evento evento) {
        log.debug("Request to partially update Evento : {}", evento);

        return eventoRepository
            .findById(evento.getId())
            .map(
                existingEvento -> {
                    if (evento.getDataHoraEvento() != null) {
                        existingEvento.setDataHoraEvento(evento.getDataHoraEvento());
                    }
                    if (evento.getTipoEvento() != null) {
                        existingEvento.setTipoEvento(evento.getTipoEvento());
                    }
                    if (evento.getTipoLocal() != null) {
                        existingEvento.setTipoLocal(evento.getTipoLocal());
                    }
                    if (evento.getCoordenadaLocal() != null) {
                        existingEvento.setCoordenadaLocal(evento.getCoordenadaLocal());
                    }
                    if (evento.getDetalhesLocal() != null) {
                        existingEvento.setDetalhesLocal(evento.getDetalhesLocal());
                    }
                    if (evento.getDataHoraCriacaoRegistro() != null) {
                        existingEvento.setDataHoraCriacaoRegistro(evento.getDataHoraCriacaoRegistro());
                    }
                    if (evento.getDescricaoEvento() != null) {
                        existingEvento.setDescricaoEvento(evento.getDescricaoEvento());
                    }
                    if (evento.getFormaFixacao() != null) {
                        existingEvento.setFormaFixacao(evento.getFormaFixacao());
                    }
                    if (evento.getMetodoCoercao() != null) {
                        existingEvento.setMetodoCoercao(evento.getMetodoCoercao());
                    }
                    if (evento.getNumEnvolvidosAssaltantes() != null) {
                        existingEvento.setNumEnvolvidosAssaltantes(evento.getNumEnvolvidosAssaltantes());
                    }
                    if (evento.getNumEnvolvidosVitimas() != null) {
                        existingEvento.setNumEnvolvidosVitimas(evento.getNumEnvolvidosVitimas());
                    }

                    return existingEvento;
                }
            )
            .map(eventoRepository::save);
    }

    /**
     * Get all the eventos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Evento> findAll(Pageable pageable) {
        log.debug("Request to get all Eventos");
        return eventoRepository.findAll(pageable);
    }

    /**
     * Get one evento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Evento> findOne(Long id) {
        log.debug("Request to get Evento : {}", id);
        return eventoRepository.findById(id);
    }

    /**
     * Delete the evento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Evento : {}", id);
        eventoRepository.deleteById(id);
    }
}
