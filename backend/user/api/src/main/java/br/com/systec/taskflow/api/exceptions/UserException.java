package br.com.systec.taskflow.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class UserException extends BaseException {
    @Serial
    private static final long serialVersionUID = 2209186835519452231L;

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable e) {
        super(e);
    }

    public UserException(String message) {
        super(message);
    }
}
