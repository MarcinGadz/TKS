package shop.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BasicException extends Exception {
    public BasicException(String message) {
        super(message);
    }
}
