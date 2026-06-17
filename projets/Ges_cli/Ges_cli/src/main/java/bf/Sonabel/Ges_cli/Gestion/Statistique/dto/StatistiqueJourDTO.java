package bf.Sonabel.Ges_cli.Gestion.Statistique.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatistiqueJourDTO {
    private String date; // format "yyyy-MM-dd"
    private Long totalAbonnes;
    private Long totalAbonnements;
    private Long totalResiliations;
}
