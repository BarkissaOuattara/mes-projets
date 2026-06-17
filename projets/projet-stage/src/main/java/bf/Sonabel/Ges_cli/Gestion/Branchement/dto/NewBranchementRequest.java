package bf.Sonabel.Ges_cli.Gestion.Branchement.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewBranchementRequest {

    @NotNull
    private Integer exploitation;
    @NotBlank
    private String typeBranchCode;

    @NotNull
    private String section;

    @NotNull
    private Integer lot;

    @NotNull
    private Integer parcelle;

    @NotNull
    private Integer rang;

    @NotNull
    private String etat;

    @NotNull
    private Integer avoirs;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private String tel;

    private String rue;
}
