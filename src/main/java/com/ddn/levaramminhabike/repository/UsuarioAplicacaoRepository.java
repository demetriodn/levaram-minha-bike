package com.ddn.levaramminhabike.repository;

import com.ddn.levaramminhabike.domain.UsuarioAplicacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UsuarioAplicacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuarioAplicacaoRepository extends JpaRepository<UsuarioAplicacao, Long> {}
