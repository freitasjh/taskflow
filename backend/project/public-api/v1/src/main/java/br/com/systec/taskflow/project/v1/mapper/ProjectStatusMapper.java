package br.com.systec.taskflow.project.v1.mapper;

import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import br.com.systec.taskflow.project.v1.dto.ProjectStatusInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectStatusResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProjectStatusMapper {

    private ProjectStatusMapper(){}

    public static ProjectStatusVO toVO(ProjectStatusInputDTO inputDTO) {
        ProjectStatusVO projectStatus = new ProjectStatusVO();

        projectStatus.setId(inputDTO.getId());
        projectStatus.setName(inputDTO.getName());
        projectStatus.setDescription(inputDTO.getDescription());
        projectStatus.setColor(inputDTO.getColor());

        return projectStatus;
    }

    public static ProjectStatusInputDTO toInput(ProjectStatusVO projectStatusVO) {
        ProjectStatusInputDTO projectStatus = new ProjectStatusInputDTO();

        projectStatus.setId(projectStatusVO.getId());
        projectStatus.setName(projectStatusVO.getName());
        projectStatus.setDescription(projectStatusVO.getDescription());
        projectStatus.setColor(projectStatusVO.getColor());

        return projectStatus;
    }

    public static ProjectStatusResponseDTO toResponseDTO(ProjectStatusVO projectStatusVO) {
        ProjectStatusResponseDTO projectStatusResponse = new ProjectStatusResponseDTO();

        projectStatusResponse.setId(projectStatusVO.getId());
        projectStatusResponse.setName(projectStatusVO.getName());
        projectStatusResponse.setDescription(projectStatusVO.getDescription());
        projectStatusResponse.setColor(projectStatusVO.getColor());

        return projectStatusResponse;
    }

    public static List<ProjectStatusResponseDTO> toListResponse(List<ProjectStatusVO> listProjectStatus) {
        return listProjectStatus.stream().map(ProjectStatusMapper::toResponseDTO).toList();
    }

    public static Page<ProjectStatusResponseDTO> toPage(Page<ProjectStatusVO> listOfStatus) {
        return listOfStatus.map(ProjectStatusMapper::toResponseDTO);
    }

}
