package br.com.systec.taskflow.project.api.exceptions.validation;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class ProjectCategoryNotFoundException extends BaseException {
    @Serial
    private static final long serialVersionUID = -3293290080646186740L;

    public ProjectCategoryNotFoundException() {
        super("project.category.not.found");
    }

    public ProjectCategoryNotFoundException(Throwable e) {
        super("project.category.not.found", e);
    }
}
