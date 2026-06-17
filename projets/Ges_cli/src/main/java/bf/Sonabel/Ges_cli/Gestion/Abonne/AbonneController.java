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
    public ResponseEntity<List<Abonne>> getAllAbonnes() {
        List<Abonne> abonnes = abonneService.getAll()
                .stream()
                //.map(AbonneDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(abonnes);
    }

    @PutMapping
    public ResponseEntity<?> createAbonne(@RequestBody NewAbonneRequest request) {
        try {
            Abonne abonne = abonneService.createAbonne(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new AbonneDTO(abonne));
        } catch (AbonneAlreadyExisteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
        }
    }


    @PostMapping
    public ResponseEntity<StringResponse> updateAbonne(@Valid @RequestBody NewAbonneRequest abonne) {
        abonneService.updateAbonne(abonne);
        return ResponseEntity.ok(new StringResponse("Mise à jour réussie"));
    }

    @DeleteMapping("/{numPolice}")
    public ResponseEntity<Void> deleteAbonne(@PathVariable String numPolice) {
        abonneService.deleteAbonne(numPolice);
        return ResponseEntity.noContent().build();
    }


  @GetMapping("/{numPolice}")
public ResponseEntity<Abonne> findByNumPol(@PathVariable String numPolice) {
    Abonne abonne = abonneService.findByNumPolice(numPolice);
    if (abonne == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(abonne);
}


}
