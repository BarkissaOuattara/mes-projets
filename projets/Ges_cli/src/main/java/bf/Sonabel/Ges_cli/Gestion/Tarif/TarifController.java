package bf.Sonabel.Ges_cli.Gestion.Tarif;

import bf.Sonabel.Ges_cli.Gestion.Exception.ErrorResponse;
import bf.Sonabel.Ges_cli.Gestion.Exception.TarifAlreadyExisteException;
import bf.Sonabel.Ges_cli.Gestion.Tarif.dto.NewTarifRequest;
import bf.Sonabel.Ges_cli.Gestion.Tarif.dto.TarifDTO;
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
@RequestMapping("tarif")
public class TarifController {

    private final TarifService tarifService;

    // Récupérer tous les tarifs
    @GetMapping("")
    public ResponseEntity<List<TarifDTO>> getAllTarifs() {
        List<TarifDTO> tarifDTOs = tarifService.getAll().stream()
                .map(TarifDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tarifDTOs);
    }

    @PutMapping("")
    public ResponseEntity<?> createTarif(@Valid @RequestBody NewTarifRequest request) {
        try {
            Tarif tarif = tarifService.createTarif(request);
            TarifDTO tarifDTO = new TarifDTO(tarif);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarifDTO);
        } catch (TarifAlreadyExisteException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }
    

    // Mettre à jour un tarif existant
    @PostMapping("")
    public ResponseEntity<StringResponse> updateTarif(@Valid @RequestBody Tarif tarif) {
        tarifService.updateTarif(tarif);
        return ResponseEntity.ok(new StringResponse("OK"));
    }

    // Supprimer un tarif
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteTarif(@PathVariable String code) {
        tarifService.deleteTarif(code);
        return ResponseEntity.noContent().build();
    }


    // Rechercher un tarif par son code
    @GetMapping("{code}")
    public ResponseEntity<TarifDTO> getTarifByCode(@PathVariable String code) {
        Tarif tarif = tarifService.findById(code);
        return ResponseEntity.ok(new TarifDTO(tarif));
    }

    @GetMapping("/search")
    public ResponseEntity<TarifDTO> searchTarifByLibelle(@RequestParam String libelle) {
    Tarif tarif = tarifService.findByLibelle(libelle);
    return ResponseEntity.ok(new TarifDTO(tarif));
}

}
