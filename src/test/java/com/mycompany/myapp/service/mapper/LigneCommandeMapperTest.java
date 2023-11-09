package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LigneCommandeMapperTest {

    private LigneCommandeMapper ligneCommandeMapper;

    @BeforeEach
    public void setUp() {
        ligneCommandeMapper = new LigneCommandeMapperImpl();
    }
}
