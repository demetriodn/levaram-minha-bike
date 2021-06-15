package com.ddn.levaramminhabike.repository;

import com.ddn.levaramminhabike.domain.Bicicleta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bicicleta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Long> {}
