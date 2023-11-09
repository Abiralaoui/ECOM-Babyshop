package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Produit} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProduitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idProduit;

    private StringFilter libelle;

    private StringFilter description;

    private FloatFilter prixUnitaire;

    private IntegerFilter taille;

    private StringFilter couleur;

    private LongFilter imageId;

    private LongFilter ligneCommandeId;

    private LongFilter avisId;

    private LongFilter categoryId;

    private Boolean distinct;

    public ProduitCriteria() {}

    public ProduitCriteria(ProduitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idProduit = other.idProduit == null ? null : other.idProduit.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.prixUnitaire = other.prixUnitaire == null ? null : other.prixUnitaire.copy();
        this.taille = other.taille == null ? null : other.taille.copy();
        this.couleur = other.couleur == null ? null : other.couleur.copy();
        this.imageId = other.imageId == null ? null : other.imageId.copy();
        this.ligneCommandeId = other.ligneCommandeId == null ? null : other.ligneCommandeId.copy();
        this.avisId = other.avisId == null ? null : other.avisId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdProduit() {
        return idProduit;
    }

    public IntegerFilter idProduit() {
        if (idProduit == null) {
            idProduit = new IntegerFilter();
        }
        return idProduit;
    }

    public void setIdProduit(IntegerFilter idProduit) {
        this.idProduit = idProduit;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public StringFilter libelle() {
        if (libelle == null) {
            libelle = new StringFilter();
        }
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public FloatFilter getPrixUnitaire() {
        return prixUnitaire;
    }

    public FloatFilter prixUnitaire() {
        if (prixUnitaire == null) {
            prixUnitaire = new FloatFilter();
        }
        return prixUnitaire;
    }

    public void setPrixUnitaire(FloatFilter prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public IntegerFilter getTaille() {
        return taille;
    }

    public IntegerFilter taille() {
        if (taille == null) {
            taille = new IntegerFilter();
        }
        return taille;
    }

    public void setTaille(IntegerFilter taille) {
        this.taille = taille;
    }

    public StringFilter getCouleur() {
        return couleur;
    }

    public StringFilter couleur() {
        if (couleur == null) {
            couleur = new StringFilter();
        }
        return couleur;
    }

    public void setCouleur(StringFilter couleur) {
        this.couleur = couleur;
    }

    public LongFilter getImageId() {
        return imageId;
    }

    public LongFilter imageId() {
        if (imageId == null) {
            imageId = new LongFilter();
        }
        return imageId;
    }

    public void setImageId(LongFilter imageId) {
        this.imageId = imageId;
    }

    public LongFilter getLigneCommandeId() {
        return ligneCommandeId;
    }

    public LongFilter ligneCommandeId() {
        if (ligneCommandeId == null) {
            ligneCommandeId = new LongFilter();
        }
        return ligneCommandeId;
    }

    public void setLigneCommandeId(LongFilter ligneCommandeId) {
        this.ligneCommandeId = ligneCommandeId;
    }

    public LongFilter getAvisId() {
        return avisId;
    }

    public LongFilter avisId() {
        if (avisId == null) {
            avisId = new LongFilter();
        }
        return avisId;
    }

    public void setAvisId(LongFilter avisId) {
        this.avisId = avisId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            categoryId = new LongFilter();
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProduitCriteria that = (ProduitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idProduit, that.idProduit) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(description, that.description) &&
            Objects.equals(prixUnitaire, that.prixUnitaire) &&
            Objects.equals(taille, that.taille) &&
            Objects.equals(couleur, that.couleur) &&
            Objects.equals(imageId, that.imageId) &&
            Objects.equals(ligneCommandeId, that.ligneCommandeId) &&
            Objects.equals(avisId, that.avisId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idProduit,
            libelle,
            description,
            prixUnitaire,
            taille,
            couleur,
            imageId,
            ligneCommandeId,
            avisId,
            categoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idProduit != null ? "idProduit=" + idProduit + ", " : "") +
            (libelle != null ? "libelle=" + libelle + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (prixUnitaire != null ? "prixUnitaire=" + prixUnitaire + ", " : "") +
            (taille != null ? "taille=" + taille + ", " : "") +
            (couleur != null ? "couleur=" + couleur + ", " : "") +
            (imageId != null ? "imageId=" + imageId + ", " : "") +
            (ligneCommandeId != null ? "ligneCommandeId=" + ligneCommandeId + ", " : "") +
            (avisId != null ? "avisId=" + avisId + ", " : "") +
            (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
