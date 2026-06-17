package bf.Sonabel.Ges_cli.Gestion.Statistique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class StatistiqueInit {

    @Autowired
    private StatistiqueService statistiqueService;

    @PostConstruct
    public void init() {
        // Ce code s'exécutera automatiquement au démarrage de l'application
        statistiqueService.enregistrerStatistiqueJour();
        statistiqueService.enregistrerStatistiqueMensuelle();
    }
}
