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
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "id_produit")
    private Integer idProduit;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "description")
    private String description;

    @DecimalMin(value = "0")
    @Column(name = "prix_unitaire")
    private Float prixUnitaire;

    @Min(value = 0)
    @Column(name = "taille")
    private Integer taille;

    @Column(name = "couleur")
    private String couleur;

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit" }, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commande", "produit" }, allowSetters = true)
    private Set<LigneCommande> ligneCommandes = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "client" }, allowSetters = true)
    private Set<Avis> avis = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_produit__category",
        joinColumns = @JoinColumn(name = "produit_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProduit() {
        return this.idProduit;
    }

    public Produit idProduit(Integer idProduit) {
        this.setIdProduit(idProduit);
        return this;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Produit libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public Produit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public Produit prixUnitaire(Float prixUnitaire) {
        this.setPrixUnitaire(prixUnitaire);
        return this;
    }

    public void setPrixUnitaire(Float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getTaille() {
        return this.taille;
    }

    public Produit taille(Integer taille) {
        this.setTaille(taille);
        return this;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public Produit couleur(String couleur) {
        this.setCouleur(couleur);
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public void setImages(Set<Image> images) {
        if (this.images != null) {
            this.images.forEach(i -> i.setProduit(null));
        }
        if (images != null) {
            images.forEach(i -> i.setProduit(this));
        }
        this.images = images;
    }

    public Produit images(Set<Image> images) {
        this.setImages(images);
        return this;
    }

    public Produit addImage(Image image) {
        this.images.add(image);
        image.setProduit(this);
        return this;
    }

    public Produit removeImage(Image image) {
        this.images.remove(image);
        image.setProduit(null);
        return this;
    }

    public Set<LigneCommande> getLigneCommandes() {
        return this.ligneCommandes;
    }

    public void setLigneCommandes(Set<LigneCommande> ligneCommandes) {
        if (this.ligneCommandes != null) {
            this.ligneCommandes.forEach(i -> i.setProduit(null));
        }
        if (ligneCommandes != null) {
            ligneCommandes.forEach(i -> i.setProduit(this));
        }
        this.ligneCommandes = ligneCommandes;
    }

    public Produit ligneCommandes(Set<LigneCommande> ligneCommandes) {
        this.setLigneCommandes(ligneCommandes);
        return this;
    }

    public Produit addLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.add(ligneCommande);
        ligneCommande.setProduit(this);
        return this;
    }

    public Produit removeLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.remove(ligneCommande);
        ligneCommande.setProduit(null);
        return this;
    }

    public Set<Avis> getAvis() {
        return this.avis;
    }

    public void setAvis(Set<Avis> avis) {
        if (this.avis != null) {
            this.avis.forEach(i -> i.setProduit(null));
        }
        if (avis != null) {
            avis.forEach(i -> i.setProduit(this));
        }
        this.avis = avis;
    }

    public Produit avis(Set<Avis> avis) {
        this.setAvis(avis);
        return this;
    }

    public Produit addAvis(Avis avis) {
        this.avis.add(avis);
        avis.setProduit(this);
        return this;
    }

    public Produit removeAvis(Avis avis) {
        this.avis.remove(avis);
        avis.setProduit(null);
        return this;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Produit categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Produit addCategory(Category category) {
        this.categories.add(category);
        category.getProduits().add(this);
        return this;
    }

    public Produit removeCategory(Category category) {
        this.categories.remove(category);
        category.getProduits().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", idProduit=" + getIdProduit() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", taille=" + getTaille() +
            ", couleur='" + getCouleur() + "'" +
            "}";
    }
}
