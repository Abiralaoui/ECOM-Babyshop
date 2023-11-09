package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvisDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisDTO.class);
        AvisDTO avisDTO1 = new AvisDTO();
        avisDTO1.setId(1L);
        AvisDTO avisDTO2 = new AvisDTO();
        assertThat(avisDTO1).isNotEqualTo(avisDTO2);
        avisDTO2.setId(avisDTO1.getId());
        assertThat(avisDTO1).isEqualTo(avisDTO2);
        avisDTO2.setId(2L);
        assertThat(avisDTO1).isNotEqualTo(avisDTO2);
        avisDTO1.setId(null);
        assertThat(avisDTO1).isNotEqualTo(avisDTO2);
    }
}
