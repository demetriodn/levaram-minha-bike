package com.ddn.levaramminhabike.repository;

import com.ddn.levaramminhabike.domain.BicicletaFoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BicicletaFoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BicicletaFotoRepository extends JpaRepository<BicicletaFoto, Long> {}
