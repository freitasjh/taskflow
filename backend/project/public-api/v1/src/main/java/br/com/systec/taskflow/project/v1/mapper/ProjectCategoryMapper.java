package br.com.systec.taskflow.project.v1.mapper;

import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import br.com.systec.taskflow.project.v1.dto.ProjectCategoryInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectCategoryResponseDTO;
import org.springframework.data.domain.Page;

public class ProjectCategoryMapper {

    private ProjectCategoryMapper() {}

    public static ProjectCategoryVO toVO(ProjectCategoryInputDTO inputDTO) {
        if(inputDTO == null) {
            return null;
        }

        ProjectCategoryVO category = new ProjectCategoryVO();
        category.setId(inputDTO.getId());
        category.setName(inputDTO.getName());
        category.setDescription(inputDTO.getDescription());

        return category;
    }

    public static ProjectCategoryInputDTO toInputDTO(ProjectCategoryVO categoryVO) {
        if(categoryVO == null) {
            return null;
        }
        ProjectCategoryInputDTO category = new ProjectCategoryInputDTO();
        category.setId(categoryVO.getId());
        category.setName(categoryVO.getName());
        category.setDescription(categoryVO.getDescription());

        return category;
    }

    public static ProjectCategoryResponseDTO toResponseDTO(ProjectCategoryVO categoryVO) {
        ProjectCategoryResponseDTO category = new ProjectCategoryResponseDTO();
        category.setId(categoryVO.getId());
        category.setName(categoryVO.getName());
        category.setDescription(categoryVO.getDescription());

        return category;
    }

    public static Page<ProjectCategoryResponseDTO> toPageResponse(Page<ProjectCategoryVO> listCategory) {
        return listCategory.map(ProjectCategoryMapper::toResponseDTO);
    }
}
