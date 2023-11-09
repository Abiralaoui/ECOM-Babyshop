import { ICarteBancaire } from 'app/entities/carte-bancaire/carte-bancaire.model';

export interface IClient {
  id: number;
  identifiant?: string | null;
  motDePasse?: string | null;
  tel?: string | null;
  address?: string | null;
  carteBancaires?: Pick<ICarteBancaire, 'id'>[] | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
