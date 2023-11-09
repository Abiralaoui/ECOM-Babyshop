import { IProduit } from 'app/entities/produit/produit.model';

export interface IImage {
  id: number;
  url?: string | null;
  produit?: Pick<IProduit, 'id'> | null;
}

export type NewImage = Omit<IImage, 'id'> & { id: null };
