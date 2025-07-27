package br.com.systec.taskflow.integration.util;

import br.com.systec.taskflow.api.v1.dto.LoginResponseDTO;
import br.com.systec.taskflow.employee.v1.dto.EmployeeInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectCategoryInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectStatusInputDTO;
import br.com.systec.taskflow.task.status.v1.dto.WorkflowInputDTO;
import br.com.systec.taskflow.team.v1.dto.TeamInputDTO;

public class IntegrationTestUtil {

    private static LoginResponseDTO loginResponseDTO;
    private static ProjectCategoryInputDTO projectCategory;
    private static ProjectStatusInputDTO projectStatus;
    private static EmployeeInputDTO employeeInputDTO;
    private static TeamInputDTO team;
    private static WorkflowInputDTO workflow;
    private static ProjectInputDTO project;

    private IntegrationTestUtil() {
    }

    public static LoginResponseDTO getLoginResponseDTO() {
        return loginResponseDTO;
    }

    public static void setLoginResponseDTO(LoginResponseDTO loginResponseDTO) {
        IntegrationTestUtil.loginResponseDTO = loginResponseDTO;
    }

    public static ProjectCategoryInputDTO getProjectCategory() {
        return projectCategory;
    }

    public static void setProjectCategory(ProjectCategoryInputDTO projectCategory) {
        IntegrationTestUtil.projectCategory = projectCategory;
    }

    public static ProjectStatusInputDTO getProjectStatus() {
        return projectStatus;
    }

    public static void setProjectStatus(ProjectStatusInputDTO projectStatus) {
        IntegrationTestUtil.projectStatus = projectStatus;
    }

    public static EmployeeInputDTO getEmployeeInputDTO() {
        return employeeInputDTO;
    }

    public static void setEmployeeInputDTO(EmployeeInputDTO employeeInputDTO) {
        IntegrationTestUtil.employeeInputDTO = employeeInputDTO;
    }

    public static TeamInputDTO getTeam() {
        return team;
    }

    public static void setTeam(TeamInputDTO team) {
        IntegrationTestUtil.team = team;
    }

    public static void setWorkflow(WorkflowInputDTO workflow) {
        IntegrationTestUtil.workflow = workflow;
    }

    public static WorkflowInputDTO getWorkflow() {
        return IntegrationTestUtil.workflow;
    }

    public static void setProject(ProjectInputDTO project) {
        IntegrationTestUtil.project = project;
    }

    public static ProjectInputDTO getProject() {
        return project;
    }
}
