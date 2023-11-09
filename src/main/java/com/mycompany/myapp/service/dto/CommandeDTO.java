package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.EtatCommande;
import com.mycompany.myapp.domain.enumeration.TypePayement;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Commande} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandeDTO implements Serializable {

    private Long id;

    private Instant date;

    private EtatCommande etat;

    private TypePayement typePayement;

    private CarteBancaireDTO carteBancaire;

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }

    public TypePayement getTypePayement() {
        return typePayement;
    }

    public void setTypePayement(TypePayement typePayement) {
        this.typePayement = typePayement;
    }

    public CarteBancaireDTO getCarteBancaire() {
        return carteBancaire;
    }

    public void setCarteBancaire(CarteBancaireDTO carteBancaire) {
        this.carteBancaire = carteBancaire;
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
        if (!(o instanceof CommandeDTO)) {
            return false;
        }

        CommandeDTO commandeDTO = (CommandeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commandeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", etat='" + getEtat() + "'" +
            ", typePayement='" + getTypePayement() + "'" +
            ", carteBancaire=" + getCarteBancaire() +
            ", client=" + getClient() +
            "}";
    }
}
