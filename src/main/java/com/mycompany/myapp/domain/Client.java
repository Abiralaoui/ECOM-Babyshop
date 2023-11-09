package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "identifiant")
    private String identifiant;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Pattern(regexp = "[0-9]{10}")
    @Column(name = "tel")
    private String tel;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligneCommandes", "carteBancaire", "client" }, allowSetters = true)
    private Set<Commande> commandes = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "client" }, allowSetters = true)
    private Set<Avis> avis = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_client__carte_bancaire",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "carte_bancaire_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commandes", "clients" }, allowSetters = true)
    private Set<CarteBancaire> carteBancaires = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return this.identifiant;
    }

    public Client identifiant(String identifiant) {
        this.setIdentifiant(identifiant);
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }

    public Client motDePasse(String motDePasse) {
        this.setMotDePasse(motDePasse);
        return this;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTel() {
        return this.tel;
    }

    public Client tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return this.address;
    }

    public Client address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Commande> getCommandes() {
        return this.commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        if (this.commandes != null) {
            this.commandes.forEach(i -> i.setClient(null));
        }
        if (commandes != null) {
            commandes.forEach(i -> i.setClient(this));
        }
        this.commandes = commandes;
    }

    public Client commandes(Set<Commande> commandes) {
        this.setCommandes(commandes);
        return this;
    }

    public Client addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setClient(this);
        return this;
    }

    public Client removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setClient(null);
        return this;
    }

    public Set<Avis> getAvis() {
        return this.avis;
    }

    public void setAvis(Set<Avis> avis) {
        if (this.avis != null) {
            this.avis.forEach(i -> i.setClient(null));
        }
        if (avis != null) {
            avis.forEach(i -> i.setClient(this));
        }
        this.avis = avis;
    }

    public Client avis(Set<Avis> avis) {
        this.setAvis(avis);
        return this;
    }

    public Client addAvis(Avis avis) {
        this.avis.add(avis);
        avis.setClient(this);
        return this;
    }

    public Client removeAvis(Avis avis) {
        this.avis.remove(avis);
        avis.setClient(null);
        return this;
    }

    public Set<CarteBancaire> getCarteBancaires() {
        return this.carteBancaires;
    }

    public void setCarteBancaires(Set<CarteBancaire> carteBancaires) {
        this.carteBancaires = carteBancaires;
    }

    public Client carteBancaires(Set<CarteBancaire> carteBancaires) {
        this.setCarteBancaires(carteBancaires);
        return this;
    }

    public Client addCarteBancaire(CarteBancaire carteBancaire) {
        this.carteBancaires.add(carteBancaire);
        carteBancaire.getClients().add(this);
        return this;
    }

    public Client removeCarteBancaire(CarteBancaire carteBancaire) {
        this.carteBancaires.remove(carteBancaire);
        carteBancaire.getClients().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", identifiant='" + getIdentifiant() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            ", tel='" + getTel() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
