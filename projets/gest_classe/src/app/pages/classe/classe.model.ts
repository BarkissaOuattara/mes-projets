import { Etudiant } from "../etudiant/etudiant.model"

export type Classe = {
    id: String,
    nom: String,
    etudiants: Etudiant[]
}

export type ClasseDTO = {
    id: String,
    nom: String
}