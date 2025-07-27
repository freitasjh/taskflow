package br.com.systec.taskflow.employee.api.service;

import br.com.systec.taskflow.employee.api.exceptions.EmployeeFindException;
import br.com.systec.taskflow.employee.api.exceptions.EmployeeNotFoundException;
import br.com.systec.taskflow.employee.api.exceptions.EmployeeSavedException;
import br.com.systec.taskflow.employee.api.filter.EmployeeFilter;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeVO save(EmployeeVO employeeVO) throws EmployeeSavedException;

    EmployeeVO update(EmployeeVO employeeVO) throws EmployeeSavedException;

    EmployeeVO findById(Long id) throws EmployeeNotFoundException;

    Page<EmployeeVO> findByFilter(EmployeeFilter filter) throws EmployeeFindException;

    EmployeeVO findByUserId(Long userId) throws EmployeeFindException;
}
