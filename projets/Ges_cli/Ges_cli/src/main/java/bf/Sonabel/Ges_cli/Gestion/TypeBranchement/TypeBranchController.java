package bf.Sonabel.Ges_cli.Gestion.TypeBranchement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/typebranch")
public class TypeBranchController {

    private final TypeBranchService typeBranchService;

    //Récupérer tous les TypeBranch
    @GetMapping("")
    public ResponseEntity<List<TypeBranch>> getAllTypeBranches() {
        return ResponseEntity.ok(typeBranchService.getAll());
    }

    //Créer un nouveau TypeBranch
    @PutMapping("")
    public ResponseEntity<TypeBranch> createTypeBranch(@RequestBody TypeBranch typeBranch) {
        return ResponseEntity.ok(typeBranchService.save(typeBranch));
    }

    //Modifier un TypeBranch existant
    @PostMapping("/{code}")
    public ResponseEntity<TypeBranch> updateTypeBranch(
            @PathVariable String code,
            @RequestBody TypeBranch updatedTypeBranch) {
        return ResponseEntity.ok(typeBranchService.update(code, updatedTypeBranch));
    }

    //Supprimer un TypeBranch
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteTypeBranch(@PathVariable String code) {
        typeBranchService.delete(code);
        return ResponseEntity.noContent().build();
    }

  @GetMapping("/{code}")
public ResponseEntity<TypeBranch> getTypeBranchByCode(@PathVariable String code) {
    TypeBranch typeBranch = typeBranchService.findByCode(code);
    return ResponseEntity.ok(typeBranch);
}


}
