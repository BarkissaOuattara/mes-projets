package bf.projet.universite.gestion.cours;

import bf.projet.universite.gestion.cours.dto.CoursDTO;
import bf.projet.universite.gestion.cours.dto.NewCoursRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoursService {
    List<CoursDTO> getAllCours();
    CoursDTO getCoursById(Long id);
    CoursDTO createCours(NewCoursRequest request);
    CoursDTO updateCours(Long id, NewCoursRequest request);
    void deleteCours(Long id);
    CoursDTO patchCours(Long id, Cours cours);
    Page<CoursDTO> getAllCours(int page, int size);
}
