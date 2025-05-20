import { IUE } from "../ue/ue.model";


export interface IMatiere {
  id: number;
  nom?: string | null;
  credit?: number | null;
  ue?: Pick<IUE, 'id'> | null;
}

export type NewMatiere = Omit<IMatiere, 'id'> & { id: null };
