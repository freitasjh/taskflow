package br.com.systec.taskflow.commons.exceptions;

import java.io.Serial;

public class AuthenticateException extends BaseException{
    @Serial
    private static final long serialVersionUID = 3190005438013341362L;

    public AuthenticateException() { }

    public AuthenticateException(String message) {
        super(message);
    }

    public AuthenticateException(String message, Throwable cause) {
        super(message, cause);
    }
}
