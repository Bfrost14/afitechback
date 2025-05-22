import { IAnneeScolaire } from "../annee-scolaire/annee-scolaire.model";
import { ISemestre } from "../semestre/semestre.model";
import { IUtilisateur } from "../ue/ue.model";


export interface IAnneeScolaireUtilisateur {
  id: number;
  anneeScolaire?: Pick<IAnneeScolaire, 'id'> | null;
  semestre?: Pick<ISemestre, 'id'> | null;
  user?: Pick<IUtilisateur, 'id'> | null;
}

export type NewAnneeScolaireUtilisateur = Omit<IAnneeScolaireUtilisateur, 'id'> & { id: null };
