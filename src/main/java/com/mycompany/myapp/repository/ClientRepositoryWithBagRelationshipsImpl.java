package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Client;
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
public class ClientRepositoryWithBagRelationshipsImpl implements ClientRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Client> fetchBagRelationships(Optional<Client> client) {
        return client.map(this::fetchCarteBancaires);
    }

    @Override
    public Page<Client> fetchBagRelationships(Page<Client> clients) {
        return new PageImpl<>(fetchBagRelationships(clients.getContent()), clients.getPageable(), clients.getTotalElements());
    }

    @Override
    public List<Client> fetchBagRelationships(List<Client> clients) {
        return Optional.of(clients).map(this::fetchCarteBancaires).orElse(Collections.emptyList());
    }

    Client fetchCarteBancaires(Client result) {
        return entityManager
            .createQuery("select client from Client client left join fetch client.carteBancaires where client is :client", Client.class)
            .setParameter("client", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Client> fetchCarteBancaires(List<Client> clients) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, clients.size()).forEach(index -> order.put(clients.get(index).getId(), index));
        List<Client> result = entityManager
            .createQuery(
                "select distinct client from Client client left join fetch client.carteBancaires where client in :clients",
                Client.class
            )
            .setParameter("clients", clients)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
