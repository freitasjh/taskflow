package br.com.systec.taskflow.workflow.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class WorkflowException extends BaseException {
    @Serial
    private static final long serialVersionUID = -7627164113343586854L;

    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
