package br.com.systec.taskflow.workflow.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class StatusVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2424649577121185463L;
    private Long id;
    private String name;
    private String description;
    private Integer order;
    private WorkflowVO workflow;
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
        if (order == null) {
            order = 0;
        }
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public WorkflowVO getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowVO workflow) {
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
