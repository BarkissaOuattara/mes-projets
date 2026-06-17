import { Abonnement } from "../../model/abonnement.model"
import { AbonnementDTO } from "../../pages/abonnement/abonnement.model"
import { Exploitation, ExploitationDTO } from "../exploitation/exploitation.model"
import { TypeBranch, TypeBranchDTO } from "../typebranch/typebranch.model"
import { NewBranchementRequest } from "./newbranchement.model"

export type Branchement = NewBranchementRequest &  {
    code: string,
    explCode: number,
    section: string,
    lot: number,
    parcelle: number,
    rang: number,
    etat: string,
    avoirs: number,
    nom: string,
    prenom: string,
    tel?: string,
    rue?: string,
    typeBranchement: TypeBranch,
    exploitation : Exploitation
}

export type BranchementDTO = {
    code: string,
    section: string,
    lot: number,
    parcelle: number,
    rang: number,
    etat: string,
    avoirs: number,
    nom: string,
    prenom: string,
    tel?: string,
    rue?: string,
    typeBranch: TypeBranchDTO,
    exploitation : ExploitationDTO,
    abonnement : AbonnementDTO


}
