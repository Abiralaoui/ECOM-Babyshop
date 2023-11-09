package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvisMapperTest {

    private AvisMapper avisMapper;

    @BeforeEach
    public void setUp() {
        avisMapper = new AvisMapperImpl();
    }
}
