package br.com.systec.taskflow.team.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TeamVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5048188660956616029L;
    private Long id;
    private String name;
    private String description;
    private Set<TeamMembersVO> members;
    private String prefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<TeamMembersVO> getMembers() {
        if(members == null) {
            members = new HashSet<>();
        }

        return members;
    }

    public void setMembers(Set<TeamMembersVO> members) {
        this.members = members;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
