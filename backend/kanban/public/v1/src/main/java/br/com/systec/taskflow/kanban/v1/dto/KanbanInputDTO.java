package br.com.systec.taskflow.kanban.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class KanbanInputDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3786233566618803778L;

    private Long id;
    private String name;
    private Long workflowId;
    private Long projectId;


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
