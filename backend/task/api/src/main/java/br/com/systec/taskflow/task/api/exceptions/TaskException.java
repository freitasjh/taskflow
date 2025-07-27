package br.com.systec.taskflow.task.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class TaskException extends BaseException {

    @Serial
    private static final long serialVersionUID = 2737261166883672612L;

    public TaskException() {
    }

    public TaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskException(String message) {
        super(message);
    }
}
