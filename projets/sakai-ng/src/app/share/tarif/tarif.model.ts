import { TypeBranch, TypeBranchDTO } from "../typebranch/typebranch.model";
import { TypeClient } from "../typecli/typeclient.model";

export type Tarif = {
    code: string;

    libelle: string;

    reglDisjonct: number;

    puissance: number;

    tarifHp: number;

    tarifHpt: number;

    tarifHc: number;

    loccpt: number;

    locposte: number;

    loctransf: number;

    mntAvCons: number;

    fraisPol: number;

    
    fraisTimb: number;

    
    mntPrimFix: number;

    mntRedev: number;
    typeBranchement: TypeBranch,
    typeClient : TypeClient
}
export type TarifDTO ={
    code: string;

    libelle: string;

    reglDisjonct: number;

    puissance: number;

    tarifHp: number;


    tarifHpt: number;

    tarifHc: number;

    loccpt: number;

    locposte: number;

    loctransf: number;

    mntAvCons: number;

    fraisPol: number;

    fraisTimb: number;

    mntPrimFix: number;

    mntRedev: number;
    typeBranch: TypeBranchDTO,
}
