package com.ddn.levaramminhabike.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ddn.levaramminhabike.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioAplicacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioAplicacao.class);
        UsuarioAplicacao usuarioAplicacao1 = new UsuarioAplicacao();
        usuarioAplicacao1.setId(1L);
        UsuarioAplicacao usuarioAplicacao2 = new UsuarioAplicacao();
        usuarioAplicacao2.setId(usuarioAplicacao1.getId());
        assertThat(usuarioAplicacao1).isEqualTo(usuarioAplicacao2);
        usuarioAplicacao2.setId(2L);
        assertThat(usuarioAplicacao1).isNotEqualTo(usuarioAplicacao2);
        usuarioAplicacao1.setId(null);
        assertThat(usuarioAplicacao1).isNotEqualTo(usuarioAplicacao2);
    }
}
