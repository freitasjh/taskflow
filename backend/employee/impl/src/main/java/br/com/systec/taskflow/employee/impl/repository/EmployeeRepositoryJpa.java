package br.com.systec.taskflow.employee.impl.repository;

import br.com.systec.taskflow.employee.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryJpa extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {


}
