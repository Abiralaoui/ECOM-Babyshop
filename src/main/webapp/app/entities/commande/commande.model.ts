import dayjs from 'dayjs/esm';
import { ICarteBancaire } from 'app/entities/carte-bancaire/carte-bancaire.model';
import { IClient } from 'app/entities/client/client.model';
import { EtatCommande } from 'app/entities/enumerations/etat-commande.model';
import { TypePayement } from 'app/entities/enumerations/type-payement.model';

export interface ICommande {
  id: number;
  date?: dayjs.Dayjs | null;
  etat?: EtatCommande | null;
  typePayement?: TypePayement | null;
  carteBancaire?: Pick<ICarteBancaire, 'id'> | null;
  client?: Pick<IClient, 'id'> | null;
}

export type NewCommande = Omit<ICommande, 'id'> & { id: null };
