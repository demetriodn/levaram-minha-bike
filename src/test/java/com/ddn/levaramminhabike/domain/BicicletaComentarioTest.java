package com.ddn.levaramminhabike.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ddn.levaramminhabike.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BicicletaComentarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BicicletaComentario.class);
        BicicletaComentario bicicletaComentario1 = new BicicletaComentario();
        bicicletaComentario1.setId(1L);
        BicicletaComentario bicicletaComentario2 = new BicicletaComentario();
        bicicletaComentario2.setId(bicicletaComentario1.getId());
        assertThat(bicicletaComentario1).isEqualTo(bicicletaComentario2);
        bicicletaComentario2.setId(2L);
        assertThat(bicicletaComentario1).isNotEqualTo(bicicletaComentario2);
        bicicletaComentario1.setId(null);
        assertThat(bicicletaComentario1).isNotEqualTo(bicicletaComentario2);
    }
}
