package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Admin} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdminDTO implements Serializable {

    private Long id;

    private String identifiant;

    private String motDePasse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdminDTO)) {
            return false;
        }

        AdminDTO adminDTO = (AdminDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adminDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdminDTO{" +
            "id=" + getId() +
            ", identifiant='" + getIdentifiant() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            "}";
    }
}
