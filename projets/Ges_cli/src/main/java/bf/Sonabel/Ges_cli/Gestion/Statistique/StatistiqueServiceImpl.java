package bf.Sonabel.Ges_cli.Gestion.Statistique;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bf.Sonabel.Ges_cli.Gestion.Abonne.AbonneRepository;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.AbonnementRepository;

@Service
public class StatistiqueServiceImpl implements StatistiqueService {

    @Autowired
    private StatistiqueJourRepository statistiqueJourRepository;

    @Autowired
    private StatistiqueMensuelleRepository statistiqueMensuelleRepository;

    @Autowired
    private AbonneRepository abonneRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    // Enregistrer les statistiques du jour (basé sur la date du jour)
    public void enregistrerStatistiqueJour() {
        LocalDate aujourdHui = LocalDate.now();

        StatistiqueJour stat = statistiqueJourRepository.findByDate(aujourdHui)
                .orElse(new StatistiqueJour());

        stat.setDate(aujourdHui);
        stat.setTotalAbonnes(abonneRepository.countByDateCreation(aujourdHui));
        stat.setTotalAbonnements(abonnementRepository.countByDateDebut(aujourdHui));
        stat.setTotalResiliations(abonnementRepository.countByDateResiliation(aujourdHui));

        statistiqueJourRepository.save(stat);
    }

   @Override
    public void enregistrerStatistiqueMensuelle() {
        YearMonth moisEnCours = YearMonth.now();
        LocalDate debutMois = moisEnCours.atDay(1);
        LocalDate finMois = moisEnCours.atEndOfMonth();

        // Générer ou mettre à jour les stats pour chaque jour du mois
        for (LocalDate date = debutMois; !date.isAfter(finMois); date = date.plusDays(1)) {
            long totalAbonnesJour = abonneRepository.countByDateCreation(date);
            long totalAbonnementsJour = abonnementRepository.countByDateDebut(date);
            long totalResiliationsJour = abonnementRepository.countByDateResiliation(date);

            StatistiqueJour statJour = statistiqueJourRepository.findByDate(date)
                    .orElse(new StatistiqueJour());

            statJour.setDate(date);
            statJour.setTotalAbonnes(totalAbonnesJour);
            statJour.setTotalAbonnements(totalAbonnementsJour);
            statJour.setTotalResiliations(totalResiliationsJour);

            statistiqueJourRepository.save(statJour);
        }

        // Calculer les totaux du mois (global)
        long totalAbonnesMois = abonneRepository.countByDateCreationBetween(debutMois, finMois);
        long totalAbonnementsMois = abonnementRepository.countByDateDebutBetween(debutMois, finMois);
        long totalResiliationsMois = abonnementRepository.countByDateResiliationBetween(debutMois, finMois);

        // Enregistrer la statistique mensuelle globale
        StatistiqueMensuelle statMensuelle = statistiqueMensuelleRepository.findByMois(moisEnCours)
                .orElse(new StatistiqueMensuelle());

        statMensuelle.setMois(moisEnCours);
        statMensuelle.setTotalAbonnes(totalAbonnesMois);
        statMensuelle.setTotalAbonnements(totalAbonnementsMois);
        statMensuelle.setTotalResiliations(totalResiliationsMois);

        statistiqueMensuelleRepository.save(statMensuelle);
    }

    // Derniers X jours de stats
    @Override
    public List<StatistiqueJour> getDerniersJours(long nbJours) {
        LocalDate aujourdHui = LocalDate.now();
        LocalDate debut = aujourdHui.minusDays(nbJours - 1);
        return statistiqueJourRepository.findAllByDateBetween(debut, aujourdHui);
    }

    // Toutes les stats mensuelles
    @Override
public List<StatistiqueMensuelle> getToutesLesStatistiquesMensuelles() {
    int anneeActuelle = YearMonth.now().getYear();

    // Récupérer les statistiques déjà enregistrées pour l'année
    List<StatistiqueMensuelle> statsExistantes = statistiqueMensuelleRepository.findAll()
        .stream()
        .filter(s -> s.getMois().getYear() == anneeActuelle)
        .toList();

    // Convertir en map pour accès rapide par mois
    Map<YearMonth, StatistiqueMensuelle> mapStats = statsExistantes.stream()
        .collect(Collectors.toMap(StatistiqueMensuelle::getMois, s -> s));

    // Générer la liste complète de janvier à décembre
    List<StatistiqueMensuelle> statsCompletes = new ArrayList<>();
    for (int mois = 1; mois <= 12; mois++) {
        YearMonth ym = YearMonth.of(anneeActuelle, mois);
        StatistiqueMensuelle stat = mapStats.getOrDefault(ym, creerStatistiqueVide(ym));
        statsCompletes.add(stat);
    }

    return statsCompletes;
}

private StatistiqueMensuelle creerStatistiqueVide(YearMonth ym) {
    StatistiqueMensuelle stat = new StatistiqueMensuelle();
    stat.setMois(ym);
    stat.setTotalAbonnes(0L);
    stat.setTotalAbonnements(0L);
    stat.setTotalResiliations(0L);
    return stat;
}


    // Générer ou mettre à jour les stats pour chaque jour des N derniers jours
    @Override
    public void genererStatsDerniersJours(long nbJours) {
        LocalDate aujourdHui = LocalDate.now();

        for (long i = nbJours - 1; i >= 0; i--) {
            LocalDate date = aujourdHui.minusDays(i);

            long totalAbonnes = abonneRepository.countByDateCreation(date);
            long totalAbonnements = abonnementRepository.countByDateDebut(date);
            long totalResiliations = abonnementRepository.countByDateResiliation(date);

            StatistiqueJour stat = statistiqueJourRepository.findByDate(date)
                    .orElse(new StatistiqueJour());

            stat.setDate(date);
            stat.setTotalAbonnes(totalAbonnes);
            stat.setTotalAbonnements(totalAbonnements);
            stat.setTotalResiliations(totalResiliations);

            statistiqueJourRepository.save(stat);
        }
    }
}