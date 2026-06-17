package bf.Sonabel.Ges_cli.Gestion.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Ce branchement existe deja")
public class BranchementAlreadyExistsException extends RuntimeException {

    // Constructeur par défaut
    public BranchementAlreadyExistsException() {
        super("Un branchement avec ces critères existe déjà.");
    }

    // Constructeur avec message personnalisé
    public BranchementAlreadyExistsException(String message) {
        super(message);
    }

    // Constructeur avec message et cause
    public BranchementAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec cause
    public BranchementAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}

