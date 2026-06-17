package bf.olive.application_test.Classe;

import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import bf.olive.application_test.Classe.dto.NewClasseRequest;
    import bf.olive.application_test.shared.StringResponse;

    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    
    import java.util.List;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PutMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("classe")
@RequiredArgsConstructor
public class ClasseController {
        
        private final ClasseService classeService;
    
        @GetMapping("")
        public ResponseEntity<List<Classe>>AllClasse(){
            return ResponseEntity.ok(
                classeService.getAll()
            );
        }
    
        @GetMapping("findById")
        public ResponseEntity<Classe>findById(@RequestParam String id) {
            return ResponseEntity.ok(
                classeService.findById(id)
            );
        }
    
        @PutMapping("")
        public ResponseEntity<Classe> createClasse(@Valid @RequestBody NewClasseRequest request) {
            return ResponseEntity.ok(
                classeService.createClasse(request)
            );
        }
    
        @PostMapping("")
        public ResponseEntity<StringResponse> updateClasse(@RequestBody Classe id) {

            classeService.updateClasse(id);
            return ResponseEntity.ok(new StringResponse("OK"));
        }
    
        @DeleteMapping("")
        public ResponseEntity<StringResponse> deleteClasse(@RequestParam String id){
            classeService.deleteClasse(id);
            return ResponseEntity.ok(new StringResponse("OK"));
        }
    }



