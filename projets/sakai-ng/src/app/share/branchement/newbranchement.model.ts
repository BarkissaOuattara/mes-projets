import { Exploitation, ExploitationDTO } from "../exploitation/exploitation.model";
import { TypeBranchDTO } from "../typebranch/typebranch.model";

export type NewBranchementRequest = {
    section: string;
    lot: number;
    parcelle: number;
    rang: number;
    etat: string;
    avoirs: number;
    nom: string;
    prenom: string;
    tel?: string;
    rue?: string;
    typeBranchCode: string;
    exploitation: number;
}
