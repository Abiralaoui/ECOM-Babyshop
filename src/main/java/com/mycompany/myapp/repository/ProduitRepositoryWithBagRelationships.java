package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Produit;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProduitRepositoryWithBagRelationships {
    Optional<Produit> fetchBagRelationships(Optional<Produit> produit);

    List<Produit> fetchBagRelationships(List<Produit> produits);

    Page<Produit> fetchBagRelationships(Page<Produit> produits);
}
