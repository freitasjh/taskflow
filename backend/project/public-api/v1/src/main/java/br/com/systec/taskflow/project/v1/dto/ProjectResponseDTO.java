package br.com.systec.taskflow.project.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class ProjectResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2835542258880650314L;
    private Long id;
    private String name;
    private String description;
    private String status;
    private String teamName;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
