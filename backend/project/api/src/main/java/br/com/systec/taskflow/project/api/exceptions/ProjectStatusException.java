package br.com.systec.taskflow.project.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class ProjectStatusException extends BaseException {

    @Serial
    private static final long serialVersionUID = -5085271232314134407L;

    public ProjectStatusException(String message, Throwable e) {
        super(message, e);
    }

    public ProjectStatusException(String message) {
        super(message);
    }
}
