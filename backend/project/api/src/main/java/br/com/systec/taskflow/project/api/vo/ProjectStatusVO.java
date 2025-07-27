package br.com.systec.taskflow.project.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class ProjectStatusVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private String name;
    private String color;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
