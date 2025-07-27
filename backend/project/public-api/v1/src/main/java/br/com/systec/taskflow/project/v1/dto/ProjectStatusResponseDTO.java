package br.com.systec.taskflow.project.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class ProjectStatusResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1483236708392310193L;

    private Long id;
    private String description;
    private String color;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
