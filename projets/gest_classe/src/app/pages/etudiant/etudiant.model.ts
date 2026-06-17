import { Classe, ClasseDTO } from "../classe/classe.model"

export type Etudiant = {
    id : string,
    matricule : string,
    nom : string,
    prenom : string,
    classe: Classe
}

export type EtudiantFull = {
    id : string,
    matricule : string,
    nom : string,
    prenom : string,
    classe: ClasseDTO
}