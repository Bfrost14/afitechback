export interface IFiliere {
  id: number;
  nom?: string | null;
}

export type NewFiliere = Omit<IFiliere, 'id'> & { id: null };
