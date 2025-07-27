package br.com.systec.taskflow.project.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class ProjectCategoryResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2614502157448344081L;

    private Long id;
    private String name;
    private String description;

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
}
