package br.com.systec.taskflow.employee.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class EmployeeSavedException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1783116081833961790L;

    public EmployeeSavedException() {
        super("employee.saved.error");
    }

    public EmployeeSavedException(Throwable e) {
        super("employee.saved.error", e);
    }
}
