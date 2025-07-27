package br.com.systec.taskflow.commons.exceptions;

public class ValidatorException extends BaseException {

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }
}