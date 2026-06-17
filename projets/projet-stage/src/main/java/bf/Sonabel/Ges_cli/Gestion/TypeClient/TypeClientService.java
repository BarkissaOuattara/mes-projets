package bf.Sonabel.Ges_cli.Gestion.TypeClient;

import java.util.List;

public interface TypeClientService {

    List<TypeClient> getAll();
    TypeClient getByCode(String code);
    TypeClient create(TypeClient typeClient);
    void updateTypeClient(TypeClient updated);
    void deleteTypeClient(String code);
}
