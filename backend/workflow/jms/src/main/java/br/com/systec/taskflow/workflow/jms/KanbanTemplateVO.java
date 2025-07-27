package br.com.systec.taskflow.workflow.jms;

import java.io.Serial;
import java.io.Serializable;

public class KanbanTemplateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1003819997298864058L;
    private Long projectId;
    private Long workflowId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String toString() {
        return "KanbanTemplateVO{" +
                "projectId=" + projectId +
                ", workflowId=" + workflowId +
                '}';
    }
}
