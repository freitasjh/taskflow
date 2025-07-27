package br.com.systec.taskflow.team.api;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class TeamException extends BaseException {

    @Serial
    private static final long serialVersionUID = 4911784746726313077L;

    public TeamException() {
    }

    public TeamException(String message) {
        super(message);
    }

    public TeamException(String message, Throwable cause) {
        super(message, cause);
    }
}
