package bf.Sonabel.Ges_cli.Gestion.Statistique;

import java.util.List;



public interface StatistiqueService {
    void enregistrerStatistiqueJour();
    void enregistrerStatistiqueMensuelle();
    List<StatistiqueJour> getDerniersJours(long nbJours);
    List<StatistiqueMensuelle> getToutesLesStatistiquesMensuelles();
    void genererStatsDerniersJours(long nbJours);

}
