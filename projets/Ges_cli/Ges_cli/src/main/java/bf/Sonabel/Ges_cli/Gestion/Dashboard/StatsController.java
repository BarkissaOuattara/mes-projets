package bf.Sonabel.Ges_cli.Gestion.Dashboard;

import bf.Sonabel.Ges_cli.Gestion.Abonne.AbonneRepository;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.AbonnementRepository;
import bf.Sonabel.Ges_cli.Gestion.Branchement.BranchementRepository;
import bf.Sonabel.Ges_cli.Gestion.Dashboard.dto.DashboardStatsDTO;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.ExploitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dashboard")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class StatsController {

    private final AbonneRepository abonneRepository;
    private final AbonnementRepository abonnementRepository;
    private final BranchementRepository branchementRepository;
    private final ExploitationRepository exploitationRepository;

    @GetMapping("")
    public DashboardStatsDTO getDashboardStats() {
        return new DashboardStatsDTO(
            String.valueOf(abonneRepository.count()),
            String.valueOf(abonnementRepository.count()),
            String.valueOf(branchementRepository.count()),
            String.valueOf(exploitationRepository.count())
        );
    }
}
