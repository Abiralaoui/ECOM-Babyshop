package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CarteBancaire.
 */
@Entity
@Table(name = "carte_bancaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_porteur")
    private String nomPorteur;

    @Column(name = "num_carte")
    private Integer numCarte;

    @Column(name = "date_expiration")
    private Instant dateExpiration;

    @Column(name = "cvv")
    private Integer cvv;

    @OneToMany(mappedBy = "carteBancaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligneCommandes", "carteBancaire", "client" }, allowSetters = true)
    private Set<Commande> commandes = new HashSet<>();

    @ManyToMany(mappedBy = "carteBancaires")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commandes", "avis", "carteBancaires" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CarteBancaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPorteur() {
        return this.nomPorteur;
    }

    public CarteBancaire nomPorteur(String nomPorteur) {
        this.setNomPorteur(nomPorteur);
        return this;
    }

    public void setNomPorteur(String nomPorteur) {
        this.nomPorteur = nomPorteur;
    }

    public Integer getNumCarte() {
        return this.numCarte;
    }

    public CarteBancaire numCarte(Integer numCarte) {
        this.setNumCarte(numCarte);
        return this;
    }

    public void setNumCarte(Integer numCarte) {
        this.numCarte = numCarte;
    }

    public Instant getDateExpiration() {
        return this.dateExpiration;
    }

    public CarteBancaire dateExpiration(Instant dateExpiration) {
        this.setDateExpiration(dateExpiration);
        return this;
    }

    public void setDateExpiration(Instant dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Integer getCvv() {
        return this.cvv;
    }

    public CarteBancaire cvv(Integer cvv) {
        this.setCvv(cvv);
        return this;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Set<Commande> getCommandes() {
        return this.commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        if (this.commandes != null) {
            this.commandes.forEach(i -> i.setCarteBancaire(null));
        }
        if (commandes != null) {
            commandes.forEach(i -> i.setCarteBancaire(this));
        }
        this.commandes = commandes;
    }

    public CarteBancaire commandes(Set<Commande> commandes) {
        this.setCommandes(commandes);
        return this;
    }

    public CarteBancaire addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setCarteBancaire(this);
        return this;
    }

    public CarteBancaire removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setCarteBancaire(null);
        return this;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.removeCarteBancaire(this));
        }
        if (clients != null) {
            clients.forEach(i -> i.addCarteBancaire(this));
        }
        this.clients = clients;
    }

    public CarteBancaire clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public CarteBancaire addClient(Client client) {
        this.clients.add(client);
        client.getCarteBancaires().add(this);
        return this;
    }

    public CarteBancaire removeClient(Client client) {
        this.clients.remove(client);
        client.getCarteBancaires().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteBancaire)) {
            return false;
        }
        return id != null && id.equals(((CarteBancaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteBancaire{" +
            "id=" + getId() +
            ", nomPorteur='" + getNomPorteur() + "'" +
            ", numCarte=" + getNumCarte() +
            ", dateExpiration='" + getDateExpiration() + "'" +
            ", cvv=" + getCvv() +
            "}";
    }
}
