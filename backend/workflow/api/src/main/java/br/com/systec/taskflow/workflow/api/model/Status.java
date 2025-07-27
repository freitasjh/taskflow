package br.com.systec.taskflow.workflow.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "status")
public class Status extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -7733497695511604593L;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "status_order")
    private Integer order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;
    private boolean initial;
    private boolean finish;

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

    public Integer getOrder() {
        if (order == null) {
            order = 0;
        }
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
