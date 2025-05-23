import { TypeNote } from "../enumerations/type-note.model";
import { IMatiereUtilisateur } from "../matiere-utilisateur/matiere-utilisateur.model";
import { IMatiere } from "../matiere/matiere.model";
import { IUtilisateur } from "../ue/ue.model";

export interface INote {
  id: number;
  valeur?: number | null;
  user?: Pick<IUtilisateur, 'id'> | null;
  matiereUser?: Pick<IMatiereUtilisateur, 'id'> | null;
  typeNote?: TypeNote | null
}

export type NewNote = Omit<INote, 'id'> & { id: null };