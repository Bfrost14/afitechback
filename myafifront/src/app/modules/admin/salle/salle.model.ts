import { ICampus } from "../campus/campus.model";


export interface ISalle {
  id: number;
  numero?: string | null;
  campus?: Pick<ICampus, 'id'> | null;
}

export type NewSalle = Omit<ISalle, 'id'> & { id: null };
