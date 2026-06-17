package bf.Sonabel.Ges_cli.Gestion.Abonne.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewAbonneRequest {


    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotNull
    private Integer exploitationCode;

    @NotBlank
    private String typeClientCode;
}
