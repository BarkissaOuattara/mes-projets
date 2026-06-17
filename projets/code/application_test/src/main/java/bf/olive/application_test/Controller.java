package bf.olive.application_test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("test")
public class Controller {
    
    @GetMapping("")
    public ResponseEntity<Etudiant> testRoute() {
        return ResponseEntity.ok(
            new Etudiant("OUATTARA", "BARKISSA")
        );
    }

    @GetMapping("/test")
    public String test() {
        return "TEST";
    }

    @PutMapping("put")
    public String putMethodName() {
        return "PUT";
    }
    
}