package bf.Sonabel.Ges_cli.Gestion.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("log")
@CrossOrigin(origins = "http://localhost:4200")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @PutMapping
    public ResponseEntity<AppLogs> createLog(@RequestBody AppLogs log) {
        log.setDateTime(LocalDateTime.now()); // auto-ajout de la date côté back
        String author = log.getAuthorCode();
    if (author == null || author.trim().isEmpty() || author.equalsIgnoreCase("system")) {
    log.setAuthorCode("INCONNU");

    }
        AppLogs savedLog = logRepository.save(log);
        return ResponseEntity.ok(savedLog);
    }

    @GetMapping
    public List<AppLogs> getAllLogs() {
        return logRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
    }
}
