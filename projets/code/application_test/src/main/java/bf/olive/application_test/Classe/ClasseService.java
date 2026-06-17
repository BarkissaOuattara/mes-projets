package bf.olive.application_test.Classe;

import java.util.List;
import java.util.Optional;

import bf.olive.application_test.Classe.dto.NewClasseRequest;

public interface ClasseService {
    List<Classe> getAll();
    Classe findById(String id);
    Optional<Classe> findByIdOptional(String id);
    Classe createClasse(NewClasseRequest request);
    void updateClasse(Classe classe);
    void deleteClasse(String id);
    
}
