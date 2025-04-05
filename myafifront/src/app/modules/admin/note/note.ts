import { UserAll } from "../etudiant/user-all";

export interface Note {
  id?: number;
  semestre: string;
  matiere: string;
  credit: number;
  valeur: number;
  user: UserAll;
}
