export interface IAnneeScolaire {
  id: number;
  nom?: string | null;
}

export type NewAnneeScolaire = Omit<IAnneeScolaire, 'id'> & { id: null };
