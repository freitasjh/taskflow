package br.com.systec.taskflow.team.v1.mapper;

import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.team.api.vo.TeamMembersVO;
import br.com.systec.taskflow.team.api.vo.TeamVO;
import br.com.systec.taskflow.team.v1.dto.EmployeeDTO;
import br.com.systec.taskflow.team.v1.dto.TeamInputDTO;
import br.com.systec.taskflow.team.v1.dto.TeamMembersInputDTO;
import br.com.systec.taskflow.team.v1.dto.TeamResponseDTO;

public class TeamMapper {

    private TeamMapper() {
    }

    public static TeamVO toVO(TeamInputDTO inputDTO) {
        if (inputDTO == null) {
            return null;
        }

        TeamVO teamVO = new TeamVO();
        teamVO.setId(inputDTO.getId());
        teamVO.setName(inputDTO.getName());
        teamVO.setDescription(inputDTO.getDescription());
        teamVO.setPrefix(inputDTO.getPrefix());

        for (TeamMembersInputDTO membersInput : inputDTO.getMembers()) {
            TeamMembersVO teamMembersVO = new TeamMembersVO();
            teamMembersVO.setId(membersInput.getId());
            teamMembersVO.setTeam(teamVO);

            if (membersInput.getEmployee() != null) {
                EmployeeVO employeeVO = new EmployeeVO();
                employeeVO.setId(membersInput.getEmployee().getId());
                teamMembersVO.setEmployee(employeeVO);
            }

            teamVO.getMembers().add(teamMembersVO);
        }

        return teamVO;
    }

    public static TeamInputDTO toInputDTO(TeamVO teamVO) {
        TeamInputDTO inputDTO = new TeamInputDTO();
        inputDTO.setId(teamVO.getId());
        inputDTO.setName(teamVO.getName());
        inputDTO.setDescription(teamVO.getDescription());
        inputDTO.setPrefix(teamVO.getPrefix());

        for(TeamMembersVO membersVO : teamVO.getMembers()) {
            TeamMembersInputDTO membersInput = new TeamMembersInputDTO();
            membersInput.setId(membersVO.getId());
            membersInput.setTeamId(inputDTO.getId());

            if (membersVO.getEmployee() != null) {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setId(membersVO.getEmployee().getId());
                employeeDTO.setName(membersVO.getEmployee().getName());
                membersInput.setEmployee(employeeDTO);
            }

            inputDTO.getMembers().add(membersInput);
        }

        return inputDTO;
    }

    public static TeamResponseDTO toResponseDTO(TeamVO teamVO) {
        TeamResponseDTO responseDTO = new TeamResponseDTO();
        responseDTO.setId(teamVO.getId());
        responseDTO.setName(teamVO.getName());
        responseDTO.setTotalMembers(teamVO.getMembers().size());

        return responseDTO;
    }
}
