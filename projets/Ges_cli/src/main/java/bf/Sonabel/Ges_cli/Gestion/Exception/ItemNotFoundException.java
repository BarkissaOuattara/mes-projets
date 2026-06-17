package bf.Sonabel.Ges_cli.Gestion.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "INTROUVABLE")
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super("Élément non trouvé.");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}


