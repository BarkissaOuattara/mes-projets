package bf.Sonabel.Ges_cli.Gestion.TypeBranchement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewTypeBranchRequest {
    @NotBlank
    private String code;

    String calibrage;

    String libelle;

    Integer fraisrcvrt;

    Integer fraisremise;

    Integer fraisrepose;

    Integer fraisetal;

    Integer forfTypebr;

    Integer forfMntBran;

    Integer forfGle;
}
