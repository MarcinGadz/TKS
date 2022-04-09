package model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BasicSOAPException extends Exception {
    public BasicSOAPException(String message) {
        super(message);
    }
}
