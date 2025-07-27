package br.com.systec.taskflow.employee.impl.converter;

import br.com.systec.taskflow.employee.impl.fake.EmployeeFake;
import br.com.systec.taskflow.employee.api.converter.EmployeeConverter;
import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EmployeeConverterTest {
    private static final Logger log = LoggerFactory.getLogger(EmployeeConverterTest.class);

    @Test
    void whenConvertToVO_thenReturnEmployeeVO() {
        log.info("@@@ whenConvertToVO_thenReturnEmployeeVO @@@");
        Employee employee = EmployeeFake.toFake();

        EmployeeVO employeeVOConverted = EmployeeConverter.toVO(employee);

        Assertions.assertThat(employeeVOConverted)
                .isNotNull()
                .extracting(EmployeeVO::getId, EmployeeVO::getName, EmployeeVO::getEmail, EmployeeVO::getCellphone,
                        EmployeeVO::getFederationId, EmployeeVO::getUserId
                )
                .containsExactly(employee.getId(), employee.getName(), employee.getEmail(),
                        employee.getCellphone(), employee.getFederationId(), employee.getUserId()
                );
    }

    @Test
    void whenConvertToVOWithUsername_thenReturnEmployeeVO() {
        log.info("@@@ whenConvertToVOWithUsername_thenReturnEmployeeVO @@@");
        Employee employee = EmployeeFake.toFake();
        String username = "testUser";

        EmployeeVO employeeVOConverted = EmployeeConverter.toVO(employee, username);

        Assertions.assertThat(employeeVOConverted)
                .isNotNull()
                .extracting(EmployeeVO::getId, EmployeeVO::getName, EmployeeVO::getEmail, EmployeeVO::getCellphone,
                        EmployeeVO::getFederationId, EmployeeVO::getUserId, EmployeeVO::getUsername
                )
                .containsExactly(employee.getId(), employee.getName(), employee.getEmail(),
                        employee.getCellphone(), employee.getFederationId(), employee.getUserId(), username
                );
    }

    @Test
    void whenConvertToEntity_thenReturnEmployee() {
        log.info("@@@ whenConvertToEntity_thenReturnEmployee @@@");
        EmployeeVO employeeVO = EmployeeFake.toFakeVO();

        Employee employeeConverted = EmployeeConverter.toEntity(employeeVO);

        Assertions.assertThat(employeeConverted)
                .isNotNull()
                .extracting(Employee::getId, Employee::getName, Employee::getEmail, Employee::getCellphone,
                        Employee::getFederationId, Employee::getUserId
                )
                .containsExactly(employeeVO.getId(), employeeVO.getName(), employeeVO.getEmail(),
                        employeeVO.getCellphone(), employeeVO.getFederationId(), employeeVO.getUserId()
                );
    }

}
