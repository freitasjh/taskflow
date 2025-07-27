package br.com.systec.taskflow.task.status.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class WorkflowResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 553924273474488401L;

    private Long id;
    private String name;
    private int totalStatus;

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

    public int getTotalStatus() {
        return totalStatus;
    }

    public void setTotalStatus(int totalStatus) {
        this.totalStatus = totalStatus;
    }
}
