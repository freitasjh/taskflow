package br.com.systec.taskflow.team.api.vo;

import br.com.systec.taskflow.employee.api.vo.EmployeeVO;

import java.io.Serial;
import java.io.Serializable;

public class TeamMembersVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2954820340838475529L;
    private Long id;
    private EmployeeVO employee;
    private TeamVO team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeVO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeVO employee) {
        this.employee = employee;
    }

    public TeamVO getTeam() {
        return team;
    }

    public void setTeam(TeamVO team) {
        this.team = team;
    }
}
