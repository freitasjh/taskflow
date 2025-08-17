package br.com.systec.taskflow.integration.suite;

import br.com.systec.taskflow.integration.employee.EmployeeControllerIT;
import br.com.systec.taskflow.integration.login.LoginControllerIT;
import br.com.systec.taskflow.integration.project.ProjectCategoryControllerIT;
import br.com.systec.taskflow.integration.project.ProjectControllerV1IT;
import br.com.systec.taskflow.integration.project.ProjectStatusControllerIT;
import br.com.systec.taskflow.integration.task.controller.TaskControllerV1IT;
import br.com.systec.taskflow.integration.team.TeamControllerV1IT;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Suite
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SelectClasses({
        LoginControllerIT.class, EmployeeControllerIT.class,
        ProjectStatusControllerIT.class, ProjectCategoryControllerIT.class,
        TeamControllerV1IT.class, ProjectControllerV1IT.class,
        TaskControllerV1IT.class
    }
)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class IntegrationSuite {
}
