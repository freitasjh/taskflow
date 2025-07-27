package br.com.systec.taskflow.project.v1.mapper;

import br.com.systec.taskflow.project.api.vo.ProjectVO;
import br.com.systec.taskflow.project.v1.dto.ProjectInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectResponseDTO;

public class ProjectMapper {

    private ProjectMapper() {
    }

    public static ProjectVO toVO(ProjectInputDTO inputDTO) {
        if (inputDTO == null) {
            return null;
        }

        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(inputDTO.getId());
        projectVO.setDescription(inputDTO.getDescription());
        projectVO.setName(inputDTO.getName());
        projectVO.setStatus(ProjectStatusMapper.toVO(inputDTO.getStatus()));
        projectVO.setProjectCategory(ProjectCategoryMapper.toVO(inputDTO.getCategory()));
        projectVO.setTeamId(inputDTO.getTeamId());
        projectVO.setPrefix(inputDTO.getPrefix());


        return projectVO;
    }

    public static ProjectInputDTO toInputDTO(ProjectVO projectVO) {
        if (projectVO == null) {
            return null;
        }

        ProjectInputDTO project = new ProjectInputDTO();
        project.setId(projectVO.getId());
        project.setDescription(projectVO.getDescription());
        project.setName(projectVO.getName());
        project.setStatus(ProjectStatusMapper.toInput(projectVO.getStatus()));
        project.setCategory(ProjectCategoryMapper.toInputDTO(projectVO.getProjectCategory()));
        project.setTeamId(projectVO.getTeamId());
        project.setPrefix(projectVO.getPrefix());

        return project;
    }

    public static ProjectResponseDTO toResponseDTO(ProjectVO projectVO) {
        if (projectVO == null) {
            return null;
        }

        ProjectResponseDTO responseDTO = new ProjectResponseDTO();
        responseDTO.setId(projectVO.getId());
        responseDTO.setName(projectVO.getName());
        responseDTO.setDescription(projectVO.getDescription());

        if (projectVO.getStatus() != null) {
            responseDTO.setStatus(projectVO.getStatus().getName());
        }

        return responseDTO;
    }
}
