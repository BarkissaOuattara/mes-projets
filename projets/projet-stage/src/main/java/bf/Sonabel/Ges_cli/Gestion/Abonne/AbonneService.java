package bf.Sonabel.Ges_cli.Gestion.Abonne;

import bf.Sonabel.Ges_cli.Gestion.Abonne.dto.NewAbonneRequest;

import java.util.List;

public interface AbonneService {
    List<Abonne> getAll();
    Abonne createAbonne(NewAbonneRequest request);
    void updateAbonne(Abonne abonne);
    void deleteAbonne(Integer numPolice);
    Abonne findByNumPolice(Integer numPolice);
}
