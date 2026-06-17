package bf.olive.application_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Action interdite")
public class UnauthorizedAction extends RuntimeException  {
    public UnauthorizedAction(){
        super();
    }
}
