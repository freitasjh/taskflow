package br.com.systec.taskflow.workflow.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity
@Table(name = "transition")
public class Transition extends BaseModel {

    @Serial
    private static final long serialVersionUID = 8015843846646399904L;
    @ManyToOne
    private Workflow workflow;
    @ManyToOne
    private Status fromStatus;
    @ManyToOne
    private Status toStatus;

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Status getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Status fromStatus) {
        this.fromStatus = fromStatus;
    }

    public Status getToStatus() {
        return toStatus;
    }

    public void setToStatus(Status toStatus) {
        this.toStatus = toStatus;
    }
}
