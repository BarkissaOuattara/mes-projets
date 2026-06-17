import { TarifDTO } from "../tarif/tarif.model";
import { TypeClient } from "../typecli/typeclient.model";
import { TypeBranch } from "../typebranch/typebranch.model";
import { Exploitation } from "../exploitation/exploitation.model";

// Représente un abonné simple (sans les objets liés)
export type Abonne = {
  code : number;
  numPol: string;
  nom: string;
  prenom: string;
  dateNaissance:Date;
  email?: string;
  genre : string;
  dateCNIB : Date;
  numReccm: string;
  numIfu : string;
  numCNIB : string;
  postbp: string;
  raisonSocial : string;
  telSer : string;
  telWhatsapp : string;
  ville : string;
  exploitationCode: string;
  typeClientCode: string;
  typeBranchCode: string;
  tarifCode: string;
};

// Représente un abonné avec les détails complets (utilisé pour l'affichage ou le formulaire enrichi)
export type AbonneDTO = {
  code : number;
  numPol: string;
  nom: string;
  prenom: string;
  dateNaissance?:Date;
  email?: string;
  genre : string;
  dateCNIB? : Date;
  numReccm: string;
  numIfu : string;
  numCNIB : string;
  postbp: string;
  raisonSocial : string;
  telSer : string;
  telWhatsapp : string;
  ville : string;
  exploitation: Exploitation;
  dateCreation?: Date;

};
