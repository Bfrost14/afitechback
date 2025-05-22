import { ICalendrierCours } from "../../calendrier-cours/calendrier-cours.model";
import { IUtilisateur } from "../../ue/ue.model";



export interface INotation {
  id: number;
  note?: number | null;
  appreciation?: string | null;
  calendrierCours?: Pick<ICalendrierCours, 'id'> | null;
  etudiant?: Pick<IUtilisateur, 'id'> | null;
}

export type NewNotation = Omit<INotation, 'id'> & { id: null };
