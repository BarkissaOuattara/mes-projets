import { Abonne, AbonneDTO } from "../../share/abonne/abonne.model";
import { Branchement } from "../../share/branchement/branchement.model";
import { Tarif } from "../../share/tarif/tarif.model";
import { TypeBranch } from "../../share/typebranch/typebranch.model";

// export interface Abonnement {
//   abonne: Abonne | null;
//   typeBranch: TypeBranch | null;
//   nomAbonne: string;
//   prenomAbonne: string;
//   libelleTypeBranchement: string;
//   codeTarif: string;
//   libelleTarif: string;
//   code?: string;
//   numPol: number;
//   dateAbonnement: Date;
//   dateResiliation?: Date;
//   codeBranch: string;
//   statut: 'A' | 'W';

//   // Informations de localisation du branchement
//   codeExpl: number;
//   lot: number;
//   parcelle: number;
//   section: string;
//   rang: number;

//   // Références vers d'autres entités
//   typeBranchementCode: string; // Clé du type de branchement
//   tarifCode: string;           // Clé du tarif appliqué

//   // Ajout : branchement complet
//   branchement?: Branchement;
// }


export interface Abonnement {
  code?: string;
  abonne: Abonne | null;
  typeBranch: TypeBranch | null;
  nomAbonne: string;
  prenomAbonne: string;
  libelleTypeBranchement: string;
  codeTarif: string;
  libelleTarif: string;
  numPol: string;
  dateAbonnement: Date;
  dateResiliation?: Date;
  codeBranch: string;
  statut: 'A' | 'W';

  // Informations de localisation du branchement
  codeExpl: number;
  lot: number;
  parcelle: number;
  section: string;
  rang: number;

  // Références vers d'autres entités
  typeBranchementCode: string; // Clé du type de branchement
  tarif: string;           // Clé du tarif appliqué

  // Ajout : branchement complet
  branchement?: Branchement;
}





export interface AbonnementDTO {
  code?: string;
  dateDebut?: Date;
  dateFin?: Date;
  dateResiliation?: Date;

    statut: 'A' | 'W';
    numPol: string;
  abonne: AbonneDTO | null;

  branchement?: Branchement;

 tarif: Tarif | null;
}