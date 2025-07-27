package br.com.systec.taskflow.project.api.converter;

import br.com.systec.taskflow.project.api.model.Project;
import br.com.systec.taskflow.project.api.vo.ProjectVO;

public class ProjectConverter {

    private ProjectConverter() {
    }

    public static Project toEntity(ProjectVO projectVO) {
        Project project = new Project();
        project.setId(projectVO.getId());
        project.setName(projectVO.getName());
        project.setDescription(projectVO.getDescription());
        project.setProjectCategory(ProjectCategoryConverter.toModel(projectVO.getProjectCategory()));
        project.setStatus(ProjectStatusConverter.toModel(projectVO.getStatus()));
        project.setTeamId(projectVO.getTeamId());
        project.setPrefix(projectVO.getPrefix());

        return project;
    }

    public static ProjectVO toVO(Project project) {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(project.getId());
        projectVO.setName(project.getName());
        projectVO.setDescription(project.getDescription());

        projectVO.setProjectCategory(ProjectCategoryConverter.toVO(project.getProjectCategory()));
        projectVO.setStatus(ProjectStatusConverter.toVO(project.getStatus()));
        projectVO.setTeamId(project.getTeamId());
        projectVO.setPrefix(project.getPrefix());

        return projectVO;
    }
}
