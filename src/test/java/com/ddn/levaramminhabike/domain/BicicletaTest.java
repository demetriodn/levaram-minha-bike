package com.ddn.levaramminhabike.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ddn.levaramminhabike.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BicicletaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bicicleta.class);
        Bicicleta bicicleta1 = new Bicicleta();
        bicicleta1.setId(1L);
        Bicicleta bicicleta2 = new Bicicleta();
        bicicleta2.setId(bicicleta1.getId());
        assertThat(bicicleta1).isEqualTo(bicicleta2);
        bicicleta2.setId(2L);
        assertThat(bicicleta1).isNotEqualTo(bicicleta2);
        bicicleta1.setId(null);
        assertThat(bicicleta1).isNotEqualTo(bicicleta2);
    }
}
