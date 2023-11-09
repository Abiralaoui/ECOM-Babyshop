import { ICommande } from 'app/entities/commande/commande.model';
import { IProduit } from 'app/entities/produit/produit.model';

export interface ILigneCommande {
  id: number;
  quantite?: number | null;
  prix?: number | null;
  commande?: Pick<ICommande, 'id'> | null;
  produit?: Pick<IProduit, 'id'> | null;
}

export type NewLigneCommande = Omit<ILigneCommande, 'id'> & { id: null };
