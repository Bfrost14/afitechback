import { IMatiere } from "../matiere/matiere.model";
import { ISemestre } from "../semestre/semestre.model";
import { IUtilisateur } from "../ue/ue.model";

export interface INote {
  id: number;
  valeur?: number | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
  matiere?: Pick<IMatiere, 'id'> | null;
  semestre?: Pick<ISemestre, 'id'> | null;
}

export type NewNote = Omit<INote, 'id'> & { id: null };