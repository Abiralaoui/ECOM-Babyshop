package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Commande;
import com.mycompany.myapp.domain.LigneCommande;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.CommandeDTO;
import com.mycompany.myapp.service.dto.LigneCommandeDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LigneCommande} and its DTO {@link LigneCommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LigneCommandeMapper extends EntityMapper<LigneCommandeDTO, LigneCommande> {
    @Mapping(target = "commande", source = "commande", qualifiedByName = "commandeId")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitId")
    LigneCommandeDTO toDto(LigneCommande s);

    @Named("commandeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CommandeDTO toDtoCommandeId(Commande commande);

    @Named("produitId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProduitDTO toDtoProduitId(Produit produit);
}
