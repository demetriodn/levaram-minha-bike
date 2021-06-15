package com.ddn.levaramminhabike.repository;

import com.ddn.levaramminhabike.domain.BicicletaComentario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BicicletaComentario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BicicletaComentarioRepository extends JpaRepository<BicicletaComentario, Long> {}
