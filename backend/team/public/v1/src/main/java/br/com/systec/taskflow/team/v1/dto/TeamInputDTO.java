package br.com.systec.taskflow.team.v1.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TeamInputDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5266971170062126927L;

    private Long id;
    @NotBlank(message = "Informar o nome da equipe é obrigatório")
    private String name;
    private String description;
    private Set<TeamMembersInputDTO> members;
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

    public Set<TeamMembersInputDTO> getMembers() {
        if (members == null) {
            members = new HashSet<>();
        }

        return members;
    }

    public void setMembers(Set<TeamMembersInputDTO> members) {
        this.members = members;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
