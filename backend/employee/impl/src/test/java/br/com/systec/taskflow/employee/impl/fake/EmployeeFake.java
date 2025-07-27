package br.com.systec.taskflow.employee.impl.fake;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.employee.api.model.Departament;
import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EmployeeFake {

    public static Employee toFake() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setEmail("teste@teste.com.br");
        employee.setCellphone("47991911223");
        employee.setFederationId("11111111111");
        employee.setUserId(1L);
        employee.setDepartment(Departament.ADMIN);

        return employee;
    }

    public static EmployeeVO toFakeVO() {
        EmployeeVO employeeVO = new EmployeeVO();
        employeeVO.setId(1L);
        employeeVO.setName("John Doe");
        employeeVO.setEmail("teste@teste.com.br");
        employeeVO.setUsername("johndoe");
        employeeVO.setFederationId("11111111111");
        employeeVO.setDepartament(Departament.ADMIN);

        return employeeVO;
    }

    public static User toFakeUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        user.setEmail("teste@teste.com.br");

        return user;
    }
}
