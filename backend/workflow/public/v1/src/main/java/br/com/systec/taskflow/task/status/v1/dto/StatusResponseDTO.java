package br.com.systec.taskflow.task.status.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class StatusResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5446035722436721429L;

    private Long id;
    private String name;
    private Integer order;
    private boolean active;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
