package br.com.systec.taskflow.task.status.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class StatusInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2106977234361935099L;

    private Long id;
    private String name;
    private String description;
    private Integer order;
    private Long workflowId;
    private boolean initial;
    private boolean finish;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
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
