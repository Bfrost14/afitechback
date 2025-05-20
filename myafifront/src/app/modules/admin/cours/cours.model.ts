import { IUtilisateur } from "../ue/ue.model";


export interface ICours {
  id: number;
  intitule?: string | null;
  professeur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewCours = Omit<ICours, 'id'> & { id: null };
