package bf.olive.application_test.etudiant.dto;

import bf.olive.application_test.Classe.dto.ClasseDTO;
import bf.olive.application_test.etudiant.Etudiant;

public record EtudiantFull(
    String id,
    String matricule,
    String nom,
    String prenom,
    ClasseDTO classe
) {
    public EtudiantFull(Etudiant etudiant){
        this(
            etudiant.getId(),
            etudiant.getMatricule(),
            etudiant.getNom(),
            etudiant.getPrenom(),
            new ClasseDTO(etudiant.getClasse())
        );
    }
}
