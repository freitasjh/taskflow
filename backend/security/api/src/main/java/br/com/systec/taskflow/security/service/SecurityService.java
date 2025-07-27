package br.com.systec.taskflow.security.service;

import br.com.systec.taskflow.employee.api.vo.EmployeeVO;

public interface SecurityService {

    EmployeeVO getCurrentEmployee();

    Long getCurrentUserId();

    Long getCurrentEmployeeId();
}
