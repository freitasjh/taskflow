package br.com.systec.taskflow.employee.api.converter;

import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;

public class EmployeeConverter {

    private EmployeeConverter() {
        // Prevent instantiation
    }

    public static EmployeeVO toVO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setId(employee.getId());
        employeeVO.setName(employee.getName());
        employeeVO.setEmail(employee.getEmail());
        employeeVO.setPhone(employee.getPhone());
        employeeVO.setCellphone(employee.getCellphone());
        employeeVO.setFederationId(employee.getFederationId());
        employeeVO.setDateCreated(employee.getDateCreated());
        employeeVO.setDateUpdated(employee.getDateUpdated());
        employeeVO.setUserId(employee.getUserId());
        employeeVO.setDepartament(employee.getDepartment());

        return employeeVO;
    }

    public static EmployeeVO toVO(Employee employee, String username) {
        if (employee == null) {
            return null;
        }

        EmployeeVO employeeVO = toVO(employee);
        employeeVO.setUsername(username);

        return employeeVO;
    }

    public static Employee toEntity(EmployeeVO employeeVO) {
        if (employeeVO == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(employeeVO.getId());
        employee.setName(employeeVO.getName());
        employee.setEmail(employeeVO.getEmail());
        employee.setPhone(employeeVO.getPhone());
        employee.setCellphone(employeeVO.getCellphone());
        employee.setFederationId(employeeVO.getFederationId());
        employee.setDateCreated(employeeVO.getDateCreated());
        employee.setDateUpdated(employeeVO.getDateUpdated());
        employee.setDepartment(employeeVO.getDepartament());

        return employee;
    }

    public static Employee toEntity(EmployeeVO employeeVO, Employee employeeExist) {
        if (employeeVO == null) {
            return null;
        }

        employeeExist.setName(employeeVO.getName());
        employeeExist.setEmail(employeeVO.getEmail());
        employeeExist.setPhone(employeeVO.getPhone());
        employeeExist.setCellphone(employeeVO.getCellphone());
        employeeExist.setFederationId(employeeVO.getFederationId());
        employeeExist.setDateCreated(employeeVO.getDateCreated());
        employeeExist.setDepartment(employeeVO.getDepartament());

        return employeeExist;
    }
}
