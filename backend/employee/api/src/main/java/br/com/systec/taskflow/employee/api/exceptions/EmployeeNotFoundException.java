package br.com.systec.taskflow.employee.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;

public class EmployeeNotFoundException extends ObjectNotFoundException {

    public EmployeeNotFoundException() {
        super("employee.not.found");
    }
}
