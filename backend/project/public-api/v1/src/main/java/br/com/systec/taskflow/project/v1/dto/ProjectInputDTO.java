package br.com.systec.taskflow.project.v1.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class ProjectInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6312647281484946732L;

    private Long id;
    private String description;
    private String name;
    private ProjectStatusInputDTO status;
    private ProjectCategoryInputDTO category;
    @NotNull(message = "Infome o time do projeto")
    private Long teamId;
    private String teamName;
    @NotNull(message = "Informe o prefixo do projeto")
    private String prefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectStatusInputDTO getStatus() {
        return status;
    }

    public void setStatus(ProjectStatusInputDTO status) {
        this.status = status;
    }

    public ProjectCategoryInputDTO getCategory() {
        return category;
    }

    public void setCategory(ProjectCategoryInputDTO category) {
        this.category = category;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
