package br.com.systec.taskflow.project.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class ProjectException extends BaseException {

    @Serial
    private static final long serialVersionUID = -5970262298401755792L;

    public ProjectException(String message, Throwable e) {
        super(message, e);
    }

    public ProjectException(String message) {
        super(message);
    }
}
