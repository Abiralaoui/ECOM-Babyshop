import { ILigneCommande, NewLigneCommande } from './ligne-commande.model';

export const sampleWithRequiredData: ILigneCommande = {
  id: 93307,
};

export const sampleWithPartialData: ILigneCommande = {
  id: 15797,
  quantite: 30844,
};

export const sampleWithFullData: ILigneCommande = {
  id: 62209,
  quantite: 62986,
  prix: 6875,
};

export const sampleWithNewData: NewLigneCommande = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
