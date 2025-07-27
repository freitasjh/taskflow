package br.com.systec.taskflow.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class UserNotFoundException extends BaseException {

    @Serial
    private static final long serialVersionUID = 7171335278067587630L;

    public UserNotFoundException() {
        super("user.not.found");
    }

}
