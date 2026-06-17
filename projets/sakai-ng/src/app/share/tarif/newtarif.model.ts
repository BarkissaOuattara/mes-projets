export type NewTarifRequest = {
    libelle: string;
    typeBranchCode: string;
    typeClientCode: string;
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
}
