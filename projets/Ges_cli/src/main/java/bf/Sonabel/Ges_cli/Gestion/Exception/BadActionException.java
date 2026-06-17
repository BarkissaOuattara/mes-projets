package bf.Sonabel.Ges_cli.Gestion.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Action incohérente")
public class BadActionException extends RuntimeException  {
    public BadActionException(){
        super();
    }
}