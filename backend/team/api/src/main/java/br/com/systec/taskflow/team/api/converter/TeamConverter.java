package br.com.systec.taskflow.team.api.converter;

import br.com.systec.taskflow.employee.api.converter.EmployeeConverter;
import br.com.systec.taskflow.team.api.model.Team;
import br.com.systec.taskflow.team.api.model.TeamMembers;
import br.com.systec.taskflow.team.api.vo.TeamMembersVO;
import br.com.systec.taskflow.team.api.vo.TeamVO;

import java.util.Set;

public class TeamConverter {

    private TeamConverter() {}

    public static TeamVO toVO(Team team) {
        if (team == null) {
            return null;
        }

        TeamVO teamVO = new TeamVO();
        teamVO.setId(team.getId());
        teamVO.setName(team.getName());
        teamVO.setDescription(team.getDescription());

        if(team.getMembers() != null) {
            for( TeamMembers teamMember : team.getMembers()) {
                TeamMembersVO teamMembersVO = toTeamMembersVO(teamVO, teamMember);
                if (teamMembersVO != null) {
                    teamVO.getMembers().add(teamMembersVO);
                }
            }
        }

        return teamVO;
    }

    public static Team toTeam(TeamVO teamVO) {
        if (teamVO == null) {
            return null;
        }

        Team team = new Team();
        team.setId(teamVO.getId());
        team.setName(teamVO.getName());
        team.setDescription(teamVO.getDescription());

        if (teamVO.getMembers() != null) {
            for( TeamMembersVO teamMembersVO : teamVO.getMembers()) {
                toTeamMember(team, teamMembersVO);
            }
        }

        return team;
    }

    public static Team toUpdateTeam(TeamVO teamVO, Team existingTeam) {
        if (teamVO == null || existingTeam == null) {
            return null;
        }

        existingTeam.setName(teamVO.getName());
        existingTeam.setDescription(teamVO.getDescription());

        if (teamVO.getMembers() != null) {
            Set<TeamMembers> existingMembers = existingTeam.getMembers();
            existingMembers.clear(); // Clear existing members to avoid duplicates

            for (TeamMembersVO teamMembersVO : teamVO.getMembers()) {
                TeamMembers teamMember = new TeamMembers();
                teamMember.setId(teamMembersVO.getId());
                teamMember.setEmployee(EmployeeConverter.toEntity(teamMembersVO.getEmployee()));
                teamMember.setTeam(existingTeam);
                existingMembers.add(teamMember);
            }
        }

        return existingTeam;
    }

    public static void toTeamMember(Team team, TeamMembersVO teamMembersVO) {

        if(teamMembersVO.getId() == null) {
            team.addMember(EmployeeConverter.toEntity(teamMembersVO.getEmployee()));
            return;
        }

        TeamMembers teamMember = new TeamMembers();
        teamMember.setId(teamMembersVO.getId());
        teamMember.setEmployee(EmployeeConverter.toEntity(teamMembersVO.getEmployee()));
        teamMember.setTeam(toTeam(teamMembersVO.getTeam()));
        team.getMembers().add(teamMember);
    }

    public static TeamMembersVO toTeamMembersVO(TeamVO teamVO, TeamMembers teamMembers) {
        if (teamMembers == null) {
            return null;
        }

        TeamMembersVO teamMembersVO = new TeamMembersVO();
        teamMembersVO.setId(teamMembers.getId());
        teamMembersVO.setEmployee(EmployeeConverter.toVO(teamMembers.getEmployee()));
        teamMembersVO.setTeam(teamVO);

        return teamMembersVO;
    }
}
