package br.com.systec.taskflow.workflow.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class StatusException extends BaseException {
    @Serial
    private static final long serialVersionUID = -3170801045390022191L;

    public StatusException(String message) {
        super(message);
    }

    public StatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
