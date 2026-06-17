package bf.Sonabel.Ges_cli.Gestion.Tarif;

import bf.Sonabel.Ges_cli.Gestion.Tarif.dto.NewTarifRequest;
import java.util.List;

public interface TarifService {
    List<Tarif> getAll();
    Tarif createTarif(NewTarifRequest request);
    void updateTarif(Tarif tarif);
    void deleteTarif(String code);
    Tarif findById(String code);
    boolean existsByCode(String code);
}
