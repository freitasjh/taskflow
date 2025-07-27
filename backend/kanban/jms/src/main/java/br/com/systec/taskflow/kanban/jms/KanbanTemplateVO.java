package br.com.systec.taskflow.kanban.jms;

import java.io.Serial;
import java.io.Serializable;

public class KanbanTemplateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1607145627721824308L;
    private Long workflowId;
    private Long projectId;

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
