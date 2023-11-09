package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CarteBancaire;
import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.service.dto.CarteBancaireDTO;
import com.mycompany.myapp.service.dto.ClientDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "carteBancaires", source = "carteBancaires", qualifiedByName = "carteBancaireIdSet")
    ClientDTO toDto(Client s);

    @Mapping(target = "removeCarteBancaire", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    @Named("carteBancaireId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarteBancaireDTO toDtoCarteBancaireId(CarteBancaire carteBancaire);

    @Named("carteBancaireIdSet")
    default Set<CarteBancaireDTO> toDtoCarteBancaireIdSet(Set<CarteBancaire> carteBancaire) {
        return carteBancaire.stream().map(this::toDtoCarteBancaireId).collect(Collectors.toSet());
    }
}
