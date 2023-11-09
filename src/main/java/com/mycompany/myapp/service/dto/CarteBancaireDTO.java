package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CarteBancaire} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarteBancaireDTO implements Serializable {

    private Long id;

    private String nomPorteur;

    private Integer numCarte;

    private Instant dateExpiration;

    private Integer cvv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPorteur() {
        return nomPorteur;
    }

    public void setNomPorteur(String nomPorteur) {
        this.nomPorteur = nomPorteur;
    }

    public Integer getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(Integer numCarte) {
        this.numCarte = numCarte;
    }

    public Instant getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Instant dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteBancaireDTO)) {
            return false;
        }

        CarteBancaireDTO carteBancaireDTO = (CarteBancaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carteBancaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteBancaireDTO{" +
            "id=" + getId() +
            ", nomPorteur='" + getNomPorteur() + "'" +
            ", numCarte=" + getNumCarte() +
            ", dateExpiration='" + getDateExpiration() + "'" +
            ", cvv=" + getCvv() +
            "}";
    }
}
