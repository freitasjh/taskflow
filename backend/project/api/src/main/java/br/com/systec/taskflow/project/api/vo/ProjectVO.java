package br.com.systec.taskflow.project.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class ProjectVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4200627913551554346L;

    private Long id;
    private String description;
    private String name;
    private ProjectStatusVO status;
    private ProjectCategoryVO projectCategory;
    private Long teamId;
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

    public ProjectStatusVO getStatus() {
        return status;
    }

    public void setStatus(ProjectStatusVO status) {
        this.status = status;
    }

    public ProjectCategoryVO getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(ProjectCategoryVO projectCategory) {
        this.projectCategory = projectCategory;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
