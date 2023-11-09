package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CarteBancaire;
import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Commande;
import com.mycompany.myapp.service.dto.CarteBancaireDTO;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.CommandeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {
    @Mapping(target = "carteBancaire", source = "carteBancaire", qualifiedByName = "carteBancaireId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    CommandeDTO toDto(Commande s);

    @Named("carteBancaireId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarteBancaireDTO toDtoCarteBancaireId(CarteBancaire carteBancaire);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
