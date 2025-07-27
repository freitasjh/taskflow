package br.com.systec.taskflow.kanban.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity
@Table(name = "kanban")
public class Kanban extends BaseModel {

    @Serial
    private static final long serialVersionUID = 1351233025734857774L;
    @Column(name = "name")
    private String name;
    @Column(name = "workflow_id", nullable = false)
    private Long workflowId;
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
