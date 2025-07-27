package br.com.systec.taskflow.project.api.exceptions.validation;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class ProjectNotFoundException extends BaseException {
    @Serial
    private static final long serialVersionUID = 3032235264989581752L;

    public ProjectNotFoundException() {
        super("project.not.found");
    }

    public ProjectNotFoundException(Throwable e) {
        super("project.not.found", e);
    }
}
