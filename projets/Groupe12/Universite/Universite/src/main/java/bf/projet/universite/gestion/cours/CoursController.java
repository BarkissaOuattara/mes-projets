package bf.projet.universite.gestion.cours;

import bf.projet.universite.gestion.cours.dto.CoursDTO;
import bf.projet.universite.gestion.cours.dto.NewCoursRequest;
import bf.projet.universite.gestion.cours.dto.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    @GetMapping
    public ResponseEntity<List<CoursDTO>> getAllCours() {
        return ResponseEntity.ok(coursService.getAllCours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoursDTO> getCoursById(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.getCoursById(id));
    }

    @PostMapping
    public ResponseEntity<CoursDTO> createCours(@Valid @RequestBody NewCoursRequest request) {
        return new ResponseEntity<>(coursService.createCours(request), HttpStatus.CREATED);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoursDTO> updateCours(
            @PathVariable Long id,
            @Valid @RequestBody NewCoursRequest request) {
        return ResponseEntity.ok(coursService.updateCours(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CoursDTO> patchCours(
            @PathVariable Long id,
            @RequestBody Cours cours) {
        return ResponseEntity.ok(coursService.patchCours(id, cours));
    }

    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteCours(@PathVariable Long id) {

    coursService.deleteCours(id);

    return ResponseEntity.ok(
            new SuccessResponse("Suppression réussie")
    );
}
}