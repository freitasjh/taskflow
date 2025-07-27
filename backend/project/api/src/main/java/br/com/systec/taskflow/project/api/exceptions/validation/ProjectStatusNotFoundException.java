package br.com.systec.taskflow.project.api.exceptions.validation;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class ProjectStatusNotFoundException extends BaseException {
    @Serial
    private static final long serialVersionUID = -8398044323244079731L;

    public ProjectStatusNotFoundException() {
        super("project.status.not.found");
    }

    public ProjectStatusNotFoundException(Throwable e) {
        super("project.status.not.found", e);
    }
}
