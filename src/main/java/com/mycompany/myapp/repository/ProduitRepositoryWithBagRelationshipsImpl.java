package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Produit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProduitRepositoryWithBagRelationshipsImpl implements ProduitRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Produit> fetchBagRelationships(Optional<Produit> produit) {
        return produit.map(this::fetchCategories);
    }

    @Override
    public Page<Produit> fetchBagRelationships(Page<Produit> produits) {
        return new PageImpl<>(fetchBagRelationships(produits.getContent()), produits.getPageable(), produits.getTotalElements());
    }

    @Override
    public List<Produit> fetchBagRelationships(List<Produit> produits) {
        return Optional.of(produits).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    Produit fetchCategories(Produit result) {
        return entityManager
            .createQuery("select produit from Produit produit left join fetch produit.categories where produit is :produit", Produit.class)
            .setParameter("produit", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Produit> fetchCategories(List<Produit> produits) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, produits.size()).forEach(index -> order.put(produits.get(index).getId(), index));
        List<Produit> result = entityManager
            .createQuery(
                "select distinct produit from Produit produit left join fetch produit.categories where produit in :produits",
                Produit.class
            )
            .setParameter("produits", produits)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
