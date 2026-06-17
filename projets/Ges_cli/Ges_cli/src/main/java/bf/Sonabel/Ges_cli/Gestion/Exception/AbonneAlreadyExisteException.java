package bf.Sonabel.Ges_cli.Gestion.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Cet abonné existe déjà")
public class AbonneAlreadyExisteException extends RuntimeException {

    public AbonneAlreadyExisteException() {
        super("Un abonné avec cette police existe déjà.");
    }

    public AbonneAlreadyExisteException(String message) {
        super(message);
    }

    public AbonneAlreadyExisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbonneAlreadyExisteException(Throwable cause) {
        super(cause);
    }
}
