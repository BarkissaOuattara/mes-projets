package bf.Sonabel.Ges_cli.Gestion.TypeClient;

import bf.Sonabel.Ges_cli.Gestion.shared.StringResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("typecli")
@RequiredArgsConstructor
public class TypeClientController {

    private final TypeClientService typeClientService;

    // Récupérer tous les types de client
    @GetMapping("")
    public ResponseEntity<List<TypeClient>> getAll() {
        return ResponseEntity.ok(typeClientService.getAll());
    }

    // Trouver un type de client par son code
    @GetMapping("/{code}")
    public ResponseEntity<TypeClient> getByCode(@PathVariable String code) {
        TypeClient typeClient = typeClientService.getByCode(code);
        if (typeClient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(typeClient);
    }

    // Créer un nouveau type de client
    @PutMapping("")
    public ResponseEntity<TypeClient> create(@Valid @RequestBody TypeClient typeClient) {
        TypeClient created = typeClientService.create(typeClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Mettre à jour un type de client
    @PostMapping("")
    public ResponseEntity<StringResponse> updateTypeClient(@Valid @RequestBody TypeClient updated) {
        typeClientService.updateTypeClient(updated);
        return ResponseEntity.ok(new StringResponse("OK"));
    }

    // Supprimer un type de client
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        typeClientService.deleteTypeClient(code);
        return ResponseEntity.noContent().build();
    }

}
