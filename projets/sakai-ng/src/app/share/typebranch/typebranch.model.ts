import { Branchement } from "../branchement/branchement.model";
import { Tarif } from "../tarif/tarif.model";

export type TypeBranch = {
    code: string;
    calibrage: string;
    libelle: string;
    fraisrcvrt: number;
    fraisremise: number;
    fraisrepose: number;
    fraisetal: number;
    forfTypebr: number;
    forfMntBran: number;
    forfGle: number;
    branchements: Branchement[];
     tarif?: Tarif;

}
export type TypeBranchDTO = {
    code: string;
    calibrage: string;
    libelle: string;
    fraisrcvrt: number;
    fraisremise: number;
    fraisrepose: number;
    fraisetal: number;
    forfTypebr: number;
    forfMntBran: number;
    forfGle: number;
}
