package br.com.systec.taskflow.project.api.converter;

import br.com.systec.taskflow.project.api.model.ProjectCategory;
import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import org.springframework.data.domain.Page;

public class ProjectCategoryConverter {

    private ProjectCategoryConverter() {
        // Private constructor to prevent instantiation
    }

    public static ProjectCategory toModel(ProjectCategoryVO projectCategoryVO) {
        if (projectCategoryVO == null) {
            return null;
        }

        ProjectCategory projectCategory = new br.com.systec.taskflow.project.api.model.ProjectCategory();
        projectCategory.setId(projectCategoryVO.getId());
        projectCategory.setDescription(projectCategoryVO.getDescription());
        projectCategory.setName(projectCategoryVO.getName());

        return projectCategory;
    }

    public static ProjectCategoryVO toVO(br.com.systec.taskflow.project.api.model.ProjectCategory projectCategory) {
        if (projectCategory == null) {
            return null;
        }

        ProjectCategoryVO projectCategoryVO = new ProjectCategoryVO();
        projectCategoryVO.setId(projectCategory.getId());
        projectCategoryVO.setDescription(projectCategory.getDescription());
        projectCategoryVO.setName(projectCategory.getName());

        return projectCategoryVO;
    }

    public static Page<ProjectCategoryVO> toPageVO(Page<ProjectCategory> listCategory) {
        return listCategory.map(ProjectCategoryConverter::toVO);
    }
}
