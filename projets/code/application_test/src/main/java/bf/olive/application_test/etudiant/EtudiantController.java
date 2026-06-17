package bf.olive.application_test.etudiant;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bf.olive.application_test.etudiant.dto.EtudiantFull;
import bf.olive.application_test.etudiant.dto.NewEtudiantRequest;
import bf.olive.application_test.shared.StringResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("etudiant")
@RequiredArgsConstructor
@Slf4j
public class EtudiantController {

    private final EtudiantService etudiantService;

    
    @GetMapping("")
    public ResponseEntity<List<EtudiantFull>> allEtudiant() {
        return ResponseEntity.ok(
            etudiantService.getAll()
        );
    }

    @GetMapping("findById")
    public ResponseEntity<EtudiantFull> findById(@RequestParam String id) {
        Etudiant etudiant = etudiantService.findById(id);
        return ResponseEntity.ok(new EtudiantFull(etudiant));
    }


    @PutMapping("")
    public ResponseEntity<EtudiantFull> createEtudiant(@Valid @RequestBody NewEtudiantRequest request) {
        Etudiant etudiant = etudiantService.createEtudiant(request);
        EtudiantFull etudiantFull = new EtudiantFull(etudiant);
        return ResponseEntity.ok(etudiantFull);
    }


    @PostMapping("")
    public ResponseEntity<StringResponse> updateEtudiant(@RequestBody Etudiant etudiant) {
        etudiantService.updateEtudiant(etudiant);
        return ResponseEntity.ok(new StringResponse("OK"));
    }

    @DeleteMapping("")
    public ResponseEntity<StringResponse> deleteEtudiant(@RequestParam String id){
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.ok(new StringResponse("OK"));
    }
    
}
