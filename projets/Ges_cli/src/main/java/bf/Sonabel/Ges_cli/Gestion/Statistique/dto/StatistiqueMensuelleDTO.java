package bf.Sonabel.Ges_cli.Gestion.Statistique.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatistiqueMensuelleDTO {
    private String mois; // format "yyyy-MMMM"
    private Long totalAbonnes;
    private Long totalAbonnements;
    private Long totalResiliations;
}
