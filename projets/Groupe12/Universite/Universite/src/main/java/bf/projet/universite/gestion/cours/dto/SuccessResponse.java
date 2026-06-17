package bf.projet.universite.gestion.cours.dto;

import java.time.LocalDateTime;

public class SuccessResponse {

    private String message;
    private LocalDateTime timestamp;

    public SuccessResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}