import { Tarif } from "../tarif/tarif.model";

export type TypeClient = {
    code: string;
    libelle: string;
    tarif: Tarif[];
  };