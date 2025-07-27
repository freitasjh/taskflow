package br.com.systec.taskflow.workflow.api.vo;

import br.com.systec.taskflow.workflow.api.model.Workflow;

public class TransitionVO {

    private Long id;
    private Workflow workflow;
    private StatusVO fromStatus;
    private StatusVO toStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public StatusVO getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(StatusVO fromStatus) {
        this.fromStatus = fromStatus;
    }

    public StatusVO getToStatus() {
        return toStatus;
    }

    public void setToStatus(StatusVO toStatus) {
        this.toStatus = toStatus;
    }
}
