import { IProduit } from 'app/entities/produit/produit.model';

export interface ICategory {
  id: number;
  idCategory?: number | null;
  nom?: string | null;
  produits?: Pick<IProduit, 'id'>[] | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
