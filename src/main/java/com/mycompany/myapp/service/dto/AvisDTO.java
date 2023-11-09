package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Avis} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvisDTO implements Serializable {

    private Long id;

    @Min(value = 1)
    @Max(value = 5)
    private Integer note;

    private String commentaire;

    private Instant date;

    private ProduitDTO produit;

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvisDTO)) {
            return false;
        }

        AvisDTO avisDTO = (AvisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, avisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvisDTO{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", commentaire='" + getCommentaire() + "'" +
            ", date='" + getDate() + "'" +
            ", produit=" + getProduit() +
            ", client=" + getClient() +
            "}";
    }
}
