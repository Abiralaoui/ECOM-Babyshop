package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CarteBancaire;
import com.mycompany.myapp.service.dto.CarteBancaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarteBancaire} and its DTO {@link CarteBancaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarteBancaireMapper extends EntityMapper<CarteBancaireDTO, CarteBancaire> {}
