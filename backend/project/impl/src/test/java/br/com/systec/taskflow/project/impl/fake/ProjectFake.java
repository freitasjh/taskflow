package br.com.systec.taskflow.project.impl.fake;

import br.com.systec.taskflow.project.api.model.Project;
import br.com.systec.taskflow.project.api.model.ProjectCategory;
import br.com.systec.taskflow.project.api.model.ProjectStatus;
import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import br.com.systec.taskflow.project.api.vo.ProjectVO;

public class ProjectFake {

    public static Project toFakeProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Fake Project");
        project.setDescription("Fake Project Description");
        project.setProjectCategory(toFakeProjectCategory());
        project.setStatus(toFakeProjectStatus());

        return project;
    }

    public static ProjectVO toFakeProjectVO() {
        ProjectVO project = new ProjectVO();
        project.setId(1L);
        project.setName("Fake Project");
        project.setDescription("Fake Project Description");
        project.setProjectCategory(toFakeProjectCategoryVO());
        project.setStatus(toFakeProjectStatusVO());

        return project;
    }

    public static ProjectCategory toFakeProjectCategory() {
        ProjectCategory projectCategory = new ProjectCategory();
        projectCategory.setId(1L);
        projectCategory.setDescription("Fake Category descricao");
        projectCategory.setName("Fake category");

        return projectCategory;
    }

    public static ProjectCategoryVO toFakeProjectCategoryVO() {
        ProjectCategoryVO projectCategoryVO = new ProjectCategoryVO();
        projectCategoryVO.setId(1L);
        projectCategoryVO.setDescription("Fake Category VO descricao");
        projectCategoryVO.setName("Fake category VO");

        return projectCategoryVO;
    }

    public static ProjectStatus toFakeProjectStatus() {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(1L);
        projectStatus.setDescription("Fake Status");
        projectStatus.setName("Fake Status Name");
        projectStatus.setColor("#FFFFFF");

        return projectStatus;
    }

    public static ProjectStatusVO toFakeProjectStatusVO() {
        ProjectStatusVO projectStatusVO = new ProjectStatusVO();
        projectStatusVO.setId(1L);
        projectStatusVO.setDescription("Fake Status VO");
        projectStatusVO.setName("Fake Status Name VO");
        projectStatusVO.setColor("#FFFFFF");

        return projectStatusVO;
    }
}
