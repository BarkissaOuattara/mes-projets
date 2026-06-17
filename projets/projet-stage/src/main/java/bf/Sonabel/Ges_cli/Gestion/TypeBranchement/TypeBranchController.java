package bf.Sonabel.Ges_cli.Gestion.TypeBranchement;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("typebranch")

public class TypeBranchController {

private final TypeBranchService typeBranchService;

    
@GetMapping("")
    public ResponseEntity<List<TypeBranch>>AllTypeBranch(){
        return ResponseEntity.ok(
        typeBranchService.getAll()
        );
    }
}
