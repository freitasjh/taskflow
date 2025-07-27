package br.com.systec.taskflow.security.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class LoginFailedException extends BaseException {

    @Serial
    private static final long serialVersionUID = 8219952789621257104L;

    public LoginFailedException(Throwable e) {
        super(e);
    }

    public LoginFailedException(String message, Throwable e, HttpStatus httpStatus) {
        super(message, e, httpStatus);
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
