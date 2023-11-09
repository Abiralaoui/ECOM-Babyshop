import { ICategory } from 'app/entities/category/category.model';

export interface IProduit {
  id: number;
  idProduit?: number | null;
  libelle?: string | null;
  description?: string | null;
  prixUnitaire?: number | null;
  taille?: number | null;
  couleur?: string | null;
  categories?: Pick<ICategory, 'id'>[] | null;
}

export type NewProduit = Omit<IProduit, 'id'> & { id: null };
