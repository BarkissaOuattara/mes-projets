package bf.Sonabel.Ges_cli.Gestion.Tarif.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewTarifRequest {


    @NotBlank
    private String libelle;

    @NotBlank
    private String typeBranchCode;

    @NotBlank
    private String typeClientCode;

    @NotNull
    private Integer reglDisjonct;

    @NotNull
    private Integer puissance;

    @NotNull
    private Integer tarifHp;

    @NotNull
    private Integer tarifHpt;

    @NotNull
    private Integer tarifHc;

    @NotNull
    private Integer loccpt;

    @NotNull
    private Integer locposte;

    @NotNull
    private Integer loctransf;

    @NotNull
    private Integer mntAvCons;

    @NotNull
    private Integer fraisPol;

    @NotNull
    private Integer fraisTimb;

    @NotNull
    private Integer mntPrimFix;

    @NotNull
    private Integer mntRedev;
}
