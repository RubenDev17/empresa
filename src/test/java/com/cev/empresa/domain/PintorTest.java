package com.cev.empresa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cev.empresa.web.rest.TestUtil;

public class PintorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pintor.class);
        Pintor pintor1 = new Pintor();
        pintor1.setId(1L);
        Pintor pintor2 = new Pintor();
        pintor2.setId(pintor1.getId());
        assertThat(pintor1).isEqualTo(pintor2);
        pintor2.setId(2L);
        assertThat(pintor1).isNotEqualTo(pintor2);
        pintor1.setId(null);
        assertThat(pintor1).isNotEqualTo(pintor2);
    }
}
