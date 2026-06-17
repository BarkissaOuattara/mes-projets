import { TarifDTO } from "../tarif/tarif.model";

// Représente un type client simple, sans tarif intégré
export type TypeClient = {
  code: string;
  libelle: string;
};

// Représente un type client avec tarif (utilisé par exemple côté affichage ou formulaire)
export type TypeClientDTO = {
  code: string;
  libelle: string;
  tarif: TarifDTO;
};
