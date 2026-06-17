package bf.olive.application_test.Classe.dto;

import bf.olive.application_test.Classe.Classe;

public record ClasseDTO(
    String id,
    String nom
) {
    public ClasseDTO(Classe classe){
        this(
            classe.getId(),
            classe.getNom()
        );
    }
}
