package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Avis;
import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.AvisDTO;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Avis} and its DTO {@link AvisDTO}.
 */
@Mapper(componentModel = "spring")
public interface AvisMapper extends EntityMapper<AvisDTO, Avis> {
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    AvisDTO toDto(Avis s);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
