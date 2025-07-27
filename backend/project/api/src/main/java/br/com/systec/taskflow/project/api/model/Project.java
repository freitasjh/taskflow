package br.com.systec.taskflow.project.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity(name = "project")
@Table
public class Project extends BaseModel {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_status_id")
    private ProjectStatus status;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_category_id")
    private ProjectCategory projectCategory;
    private Long teamId;
    @Column(name = "prefix")
    private String prefix;

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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public ProjectCategory getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(ProjectCategory projectCategory) {
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
