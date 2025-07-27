package br.com.systec.taskflow.team.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import br.com.systec.taskflow.employee.api.model.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team extends BaseModel {

    @Serial
    private static final long serialVersionUID = -747838469813707670L;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<TeamMembers> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TeamMembers> getMembers() {
        if (members == null) {
            members = new HashSet<>();
        }

        return members;
    }

    public void setMembers(Set<TeamMembers> members) {
        this.members = members;
    }

    public void addMember(Employee employee) {
        if (members == null) {
            members = new HashSet<>();
        }

        TeamMembers member = new TeamMembers();
        member.setEmployee(employee);
        member.setTeam(this);
        members.add(member);
    }

    public void removeMember(TeamMembers member) {
        members.remove(member);
        member.setTeam(null);
    }
}
