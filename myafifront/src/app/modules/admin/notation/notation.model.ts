import { ICours } from "../cours/cours.model";
import { IUtilisateur } from "../ue/ue.model";



export interface INotation {
  id: number;
  note?: number | null;
  appreciation?: string | null;
  cours?: Pick<ICours, 'id'> | null;
  etudiant?: Pick<IUtilisateur, 'id'> | null;
}

export type NewNotation = Omit<INotation, 'id'> & { id: null };
