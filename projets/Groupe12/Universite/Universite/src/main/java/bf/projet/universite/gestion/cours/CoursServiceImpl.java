package bf.projet.universite.gestion.cours;

import bf.projet.universite.gestion.cours.dto.CoursDTO;
import bf.projet.universite.gestion.cours.dto.NewCoursRequest;
import bf.projet.universite.gestion.cours.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class CoursServiceImpl implements CoursService {

    private final CoursRepository repository;

    public CoursServiceImpl(CoursRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CoursDTO> getAllCours() {
        return repository.findAll()
                .stream()
                .map(CoursDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CoursDTO getCoursById(Long id) {
        Cours cours = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));
        return new CoursDTO(cours);
    }

    @Override
public Page<CoursDTO> getAllCours(int page, int size) {
    return repository.findAll(PageRequest.of(page, size))
            .map(CoursDTO::new);
}
    @Override
    public CoursDTO createCours(NewCoursRequest request) {
        Cours cours = new Cours();
        cours.setTitre(request.getTitre());
        cours.setEnseignant(request.getEnseignant());
        cours.setVolumeHoraire(request.getVolumeHoraire());
        cours.setActif(request.getActif());

        return new CoursDTO(repository.save(cours));
    }

    @Override
    public CoursDTO updateCours(Long id, NewCoursRequest request) {
        Cours cours = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));

        cours.setTitre(request.getTitre());
        cours.setEnseignant(request.getEnseignant());
        cours.setVolumeHoraire(request.getVolumeHoraire());
        cours.setActif(request.getActif());

        return new CoursDTO(repository.save(cours));
    }

    @Override
    public void deleteCours(Long id) {
        Cours cours = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));

        repository.delete(cours);
    }

    
    @Override
    public CoursDTO patchCours(Long id, Cours coursDetails) {
        Cours cours = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cours non trouvé avec l'id : " + id));

        if (coursDetails.getTitre() != null) {
            cours.setTitre(coursDetails.getTitre());
        }
        if (coursDetails.getEnseignant() != null) {
            cours.setEnseignant(coursDetails.getEnseignant());
        }
        if (coursDetails.getVolumeHoraire() != null) {
            if (coursDetails.getVolumeHoraire() <= 0) {
                throw new IllegalArgumentException("Le volume horaire doit être supérieur à 0");
            }
            cours.setVolumeHoraire(coursDetails.getVolumeHoraire());
        }
        if (coursDetails.getActif() != null) {
            cours.setActif(coursDetails.getActif());
        }

        return new CoursDTO(repository.save(cours));
    }
}