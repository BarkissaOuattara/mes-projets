package bf.Sonabel.Ges_cli.Gestion.Statistique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistique")
@CrossOrigin(origins = "http://localhost:4200")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/jours/{nbJours}")
    public List<StatistiqueJour> getStatsDerniersJours(@PathVariable long nbJours) {
        return statistiqueService.getDerniersJours(nbJours);
    }

    @GetMapping("/mois")
    public List<StatistiqueMensuelle> getStatsMensuelles() {
        return statistiqueService.getToutesLesStatistiquesMensuelles();
    }

    @GetMapping("/init/jours/{nbJours}")
public ResponseEntity<String> genererStatistiquesDerniersJours(@PathVariable long nbJours) {
    statistiqueService.genererStatsDerniersJours(nbJours);
    return ResponseEntity.ok("Statistiques des " + nbJours + " derniers jours générées avec succès.");
}


    // (optionnel) : génération manuelle de la stat mensuelle
    @GetMapping("/init/mois")
    public void genererStatMensuelle() {
        statistiqueService.enregistrerStatistiqueMensuelle();
    }
}
