package bf.olive.application_test.etudiant.dto;

import bf.olive.application_test.Classe.Classe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewEtudiantRequest(
    @NotBlank
    @NotNull
    String matricule,
    String nom,
    String prenom,
    @NotNull
    Classe classe
) {}