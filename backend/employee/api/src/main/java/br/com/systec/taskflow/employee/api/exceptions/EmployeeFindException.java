package br.com.systec.taskflow.employee.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class EmployeeFindException extends BaseException {

    @Serial
    private static final long serialVersionUID = -2417917553339984534L;

    public EmployeeFindException() {
        super("employee.find.error");
    }

    public EmployeeFindException(Throwable e) {
        super("employee.find.error", e);
    }
}
