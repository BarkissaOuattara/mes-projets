package bf.Sonabel.Ges_cli.Gestion.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Action interdite")
public class UnauthorizedAction extends RuntimeException  {
    public UnauthorizedAction(){
        super();
    }
}
