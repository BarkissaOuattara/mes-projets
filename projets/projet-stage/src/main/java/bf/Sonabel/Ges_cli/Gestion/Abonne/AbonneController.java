package bf.Sonabel.Ges_cli.Gestion.Abonne;

import bf.Sonabel.Ges_cli.Gestion.Abonne.dto.AbonneDTO;
import bf.Sonabel.Ges_cli.Gestion.Abonne.dto.NewAbonneRequest;
import bf.Sonabel.Ges_cli.Gestion.Exception.AbonneAlreadyExisteException;
import bf.Sonabel.Ges_cli.Gestion.Exception.ErrorResponse;
import bf.Sonabel.Ges_cli.Gestion.shared.StringResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("abonne")
public class AbonneController {

    private final AbonneService abonneService;

    @GetMapping
    public ResponseEntity<List<AbonneDTO>> getAllAbonnes() {
        List<AbonneDTO> abonnes = abonneService.getAll()
                .stream()
                .map(AbonneDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(abonnes);
    }

    @PutMapping
    public ResponseEntity<?> createAbonne(@Valid @RequestBody NewAbonneRequest request) {
        try {
            Abonne abonne = abonneService.createAbonne(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new AbonneDTO(abonne));
        } catch (AbonneAlreadyExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<StringResponse> updateAbonne(@Valid @RequestBody Abonne abonne) {
        abonneService.updateAbonne(abonne);
        return ResponseEntity.ok(new StringResponse("Mise à jour réussie"));
    }

    @DeleteMapping("/{numPolice}")
    public ResponseEntity<Void> deleteAbonne(@PathVariable Integer numPolice) {
        abonneService.deleteAbonne(numPolice);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{numPolice}")
    public ResponseEntity<AbonneDTO> findByNumPolice(@PathVariable Integer numPolice) {
        Abonne abonne = abonneService.findByNumPolice(numPolice);
        return ResponseEntity.ok(new AbonneDTO(abonne));
    }
}
