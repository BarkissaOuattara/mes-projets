package bf.olive.application_test.Classe;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import bf.olive.application_test.Classe.dto.NewClasseRequest;
import bf.olive.application_test.exception.ItemNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;

    @Override
    public List<Classe> getAll() {
        return classeRepository.findAll();
    }

    @Override
    public Optional<Classe> findByIdOptional(String id) {
        return classeRepository.findById(id);
    }

    @Override
    public Classe findById(String id) {
        return classeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public Classe createClasse(NewClasseRequest request) {
        Classe classe = Classe.builder()
                .nom(request.nom())
                .build();

        return classeRepository.save(classe);
    }

    @Override
    public void updateClasse(Classe updatedClasse) {
        Classe existingClasse = classeRepository.findById(updatedClasse.getId())
                .orElseThrow(ItemNotFoundException::new);

        // Met à jour uniquement si un nouveau nom est fourni
        existingClasse.update(updatedClasse.getNom());

        classeRepository.save(existingClasse);
    }

    @Override
    public void deleteClasse(String id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);

        classeRepository.delete(classe);
    }
}
