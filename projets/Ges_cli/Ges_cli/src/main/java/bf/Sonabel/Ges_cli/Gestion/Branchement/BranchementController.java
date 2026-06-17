package bf.Sonabel.Ges_cli.Gestion.Branchement;

import bf.Sonabel.Ges_cli.Gestion.Branchement.dto.BranchementDTO;
import bf.Sonabel.Ges_cli.Gestion.Branchement.dto.NewBranchementRequest;
import bf.Sonabel.Ges_cli.Gestion.Exception.BranchementAlreadyExistsException;
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
@RequestMapping("branchement")
public class BranchementController {

    private final BranchementService branchementService;

    // Récupérer tous les branchements
    @GetMapping("")
    public ResponseEntity<List<BranchementDTO>> getAllBranchements() {
        List<BranchementDTO> branchementDTOs = branchementService.getAll()
            .stream()
            .map(BranchementDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(branchementDTOs);
    }

    @GetMapping("/search")
    public List<Branchement> searchBranchements(
            @RequestParam Integer lot,
            @RequestParam Integer parcelle,
            @RequestParam Integer rang,
            @RequestParam String section,
            @RequestParam Integer codeExpl
    ) {
        return branchementService.findByParams(lot, parcelle, rang, section, codeExpl);
    }
    
    // Vérifie si des branchements existent pour une exploitation donnée
    @GetMapping("/exists-by-exploitation")
    public boolean existsByExploitationCode(@RequestParam Integer codeExpl) {
        return branchementService.existsByCodeExpl(codeExpl);
    }

    // Créer un branchement
    @PutMapping("")
    public ResponseEntity<?> createBranchement(@Valid @RequestBody NewBranchementRequest request) {
        try {
            Branchement branchement = branchementService.createBranchement(request);
            BranchementDTO branchementDTO = new BranchementDTO(branchement);
            return ResponseEntity.status(HttpStatus.CREATED).body(branchementDTO);
        } catch (BranchementAlreadyExistsException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    // Mettre à jour un branchement
    @PostMapping("")
    public ResponseEntity<StringResponse> updateBranchement(@Valid @RequestBody Branchement branchement) {
        branchementService.updateBranchement(branchement);
        return ResponseEntity.ok(new StringResponse("OK"));
    }

    // Supprimer un branchement
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteBranchement(@PathVariable String code) {
        branchementService.deleteBranchement(code);
        return ResponseEntity.noContent().build();
    }

    // Trouver un branchement par son code
    @GetMapping("/{code}")
    public ResponseEntity<BranchementDTO> findByCode(@PathVariable String code) {
        Branchement branchement = branchementService.findById(code);
        return ResponseEntity.ok(new BranchementDTO(branchement));
    }
}
