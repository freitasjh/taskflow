package br.com.systec.taskflow.project.api.converter;

import br.com.systec.taskflow.project.api.model.ProjectStatus;
import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProjectStatusConverter {

    private ProjectStatusConverter() {
    }

    public static ProjectStatus toModel(ProjectStatusVO projectStatusVO) {
        if (projectStatusVO == null) {
            return null;
        }

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(projectStatusVO.getId());
        projectStatus.setDescription(projectStatusVO.getDescription());
        projectStatus.setName(projectStatusVO.getName());
        projectStatus.setColor(projectStatusVO.getColor());

        return projectStatus;
    }

    public static ProjectStatusVO toVO(ProjectStatus projectStatus) {
        if (projectStatus == null) {
            return null;
        }

        ProjectStatusVO projectStatusVO = new ProjectStatusVO();
        projectStatusVO.setId(projectStatus.getId());
        projectStatusVO.setDescription(projectStatus.getDescription());
        projectStatusVO.setName(projectStatus.getName());
        projectStatusVO.setColor(projectStatus.getColor());

        return projectStatusVO;
    }

    public static List<ProjectStatusVO> toListVO(List<ProjectStatus> listProjectStatus) {
        return listProjectStatus.stream().map(ProjectStatusConverter::toVO).toList();
    }

    public static Page<ProjectStatusVO> toPageVO(Page<ProjectStatus> listOfStatus) {
        return listOfStatus.map(ProjectStatusConverter::toVO);
    }
}
