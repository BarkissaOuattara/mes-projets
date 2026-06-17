package bf.Sonabel.Ges_cli.Gestion.Dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDTO {
    private String totalAbonnes;
    private String totalAbonnements;
    private String totalBranchements;
    private String totalExploitations;
}
