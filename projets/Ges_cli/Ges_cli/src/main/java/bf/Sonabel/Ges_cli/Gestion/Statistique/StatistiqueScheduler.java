package bf.Sonabel.Ges_cli.Gestion.Statistique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component  // IMPORTANT pour que Spring gère ce scheduler
public class StatistiqueScheduler {

    @Autowired
    private StatistiqueService statistiqueService;

    @Scheduled(cron = "0 0 1 * * ?") // tous les jours à 01h00
    public void genererStatistiquesDuJour() {
        statistiqueService.enregistrerStatistiqueJour();
        statistiqueService.enregistrerStatistiqueMensuelle();

    }
}
