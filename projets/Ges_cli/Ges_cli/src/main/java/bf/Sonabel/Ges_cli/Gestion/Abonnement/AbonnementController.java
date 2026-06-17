package bf.Sonabel.Ges_cli.Gestion.Abonnement;

import bf.Sonabel.Ges_cli.Gestion.Abonnement.dto.ResiliationRequest;
import bf.Sonabel.Ges_cli.Gestion.Branchement.Branchement;
import bf.Sonabel.Ges_cli.Gestion.Abonne.Abonne;
import bf.Sonabel.Ges_cli.Gestion.Abonnement.dto.AbonnementDTO;
import bf.Sonabel.Ges_cli.Gestion.Exception.ErrorResponse;
import bf.Sonabel.Ges_cli.Gestion.Exception.ItemNotFoundException;
import bf.Sonabel.Ges_cli.Gestion.Exploitation.Exploitation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/abonnement")
@RequiredArgsConstructor
public class AbonnementController {

    private final AbonnementService abonnementService;

    @GetMapping
public ResponseEntity<List<AbonnementDTO>> getAllAbonnements() {
    List<AbonnementDTO> abonnements = abonnementService.getAll()
        .stream()
        .map(AbonnementDTO::toDTO)
        .toList();

    return ResponseEntity.ok(abonnements);
}


    @PutMapping
    public ResponseEntity<?> createAbonnement(@RequestBody AbonnementDTO request) {

        try {
            AbonnementDTO dto = abonnementService.createAbonnement(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (ItemNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/resilier")
    public ResponseEntity<?> resilierAbonnement(@RequestBody ResiliationRequest request) {
        try {
            AbonnementDTO dto = abonnementService.resilierAbonnement(request);

            return ResponseEntity.ok(dto);
        } catch (ItemNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erreur lors de la résiliation de l'abonnement."));
        }
    }

    @GetMapping("/dernier")
    public ResponseEntity<?> getDerniersAbonnements() {
        List<AbonnementDTO> abonnements = abonnementService.getDerniersAbonnements();
        return ResponseEntity.ok(abonnements);
    }

    

    @GetMapping("/infos-creation/{codeExpl}/{lot}/{parcelle}/{section}/{rang}/{numPol}")
public ResponseEntity<?> getInfosPourCreation(
        @PathVariable String numPol,
        @PathVariable Integer codeExpl,
        @PathVariable Integer lot,
        @PathVariable Integer parcelle,
        @PathVariable String section,
        @PathVariable Integer rang
) {
    try {
        AbonnementDTO dto = abonnementService.getAbonnementActifPourResiliation(
                codeExpl, lot, parcelle, section, rang, numPol);
                if (dto != null && dto.getTarif() == null) {
                }
        return ResponseEntity.ok(dto);
    } catch (ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    } catch (IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

   
}

 @GetMapping("/branchement/{codeExpl}/{lot}/{parcelle}/{section}/{rang}")
public ResponseEntity<?> getBranchement(
        @PathVariable Integer codeExpl,
        @PathVariable Integer lot,
        @PathVariable Integer parcelle,
        @PathVariable String section,
        @PathVariable Integer rang
) {
    try {
        Exploitation exploitation = new Exploitation();
        exploitation.setCode(codeExpl);
        Branchement dto = abonnementService.findBranchement(lot, parcelle, rang, section, exploitation);
        return ResponseEntity.ok(dto);
    } catch (ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse(ex.getMessage()));
    } catch (IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }
}


  @GetMapping("/{numPol}")
public ResponseEntity<?> findByNumPol(
        @PathVariable String numPol
        
) {
    try {
        Abonne dto = abonnementService.findByNumPol(numPol);
        return ResponseEntity.ok(dto);
    } catch (ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse(ex.getMessage()));
    } catch (IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }
}

@GetMapping("/by-police/{numPol}")
public ResponseEntity<?> findAbonnementByNumPol(@PathVariable String numPol) {
    try {
        AbonnementDTO dto = abonnementService.findAbonnementByNumPol(numPol);
        return ResponseEntity.ok(dto);
    } catch (ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse(ex.getMessage()));
    } catch (IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }
}

}