package bf.Sonabel.Ges_cli.Gestion.Statistique;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.Abonnement;

@Component
public class StatistiqueEntityListener {

    private static StatistiqueService statistiqueService;

    @Autowired
    public void init(StatistiqueService statistiqueService) {
        StatistiqueEntityListener.statistiqueService = statistiqueService;
    }

    @EventListener
    public void onStatistiqueUpdate(StatistiqueUpdateEvent event) {
        Object entity = event.getSource();
        if (entity instanceof Abonne || entity instanceof Abonnement) {
            statistiqueService.enregistrerStatistiqueJour();
        }
    }
}

