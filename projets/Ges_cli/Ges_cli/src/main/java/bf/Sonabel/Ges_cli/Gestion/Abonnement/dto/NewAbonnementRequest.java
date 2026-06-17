package bf.Sonabel.Ges_cli.Gestion.Abonnement.dto;

import lombok.Data;

@Data
public class NewAbonnementRequest {
    private String code;
    private Integer lot;
    private Integer parcelle;
    private Integer rang;
    private String section;
    private Integer codeExpl;
    private String statut;
    private String numPol;

}