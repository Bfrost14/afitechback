import { IFiliere } from "../filiere/filiere.model";
import { IMatiere } from "../matiere/matiere.model";
import { ISemestre } from "../semestre/semestre.model";
import { IUtilisateur } from "../ue/ue.model";


export interface IMatiereUtilisateur {
  id: number;
  anneeScolaire?: string | null;
  user?: Pick<IUtilisateur, 'id'> | null;
  matiere?: Pick<IMatiere, 'id'> | null;
  filiere?: Pick<IFiliere, 'id'> | null;
  semestre?: Pick<ISemestre, 'id'> | null;
}

export type NewMatiereUtilisateur = Omit<IMatiereUtilisateur, 'id'> & { id: null };
