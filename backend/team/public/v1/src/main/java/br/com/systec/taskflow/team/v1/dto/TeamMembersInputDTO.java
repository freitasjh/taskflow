package br.com.systec.taskflow.team.v1.dto;


import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class TeamMembersInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 9015875467413004522L;

    private Long id;
    @NotNull(message = "Informe o funcionário")
    private EmployeeDTO employee;
    private Long teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
