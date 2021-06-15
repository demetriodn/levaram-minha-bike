package com.ddn.levaramminhabike.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ddn.levaramminhabike.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BicicletaFotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BicicletaFoto.class);
        BicicletaFoto bicicletaFoto1 = new BicicletaFoto();
        bicicletaFoto1.setId(1L);
        BicicletaFoto bicicletaFoto2 = new BicicletaFoto();
        bicicletaFoto2.setId(bicicletaFoto1.getId());
        assertThat(bicicletaFoto1).isEqualTo(bicicletaFoto2);
        bicicletaFoto2.setId(2L);
        assertThat(bicicletaFoto1).isNotEqualTo(bicicletaFoto2);
        bicicletaFoto1.setId(null);
        assertThat(bicicletaFoto1).isNotEqualTo(bicicletaFoto2);
    }
}
