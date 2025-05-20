import { IFiliere } from "../filiere/filiere.model";
import { IMatiere } from "../matiere/matiere.model";
import { IUtilisateur } from "../ue/ue.model";


export interface IMatiereUtilisateur {
  id: number;
  anneeScolaire?: string | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
  matiere?: Pick<IMatiere, 'id'> | null;
  filiere?: Pick<IFiliere, 'id'> | null;
}

export type NewMatiereUtilisateur = Omit<IMatiereUtilisateur, 'id'> & { id: null };
