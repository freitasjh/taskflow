package br.com.systec.taskflow.project.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity(name = "project_status")
@Table
public class ProjectStatus extends BaseModel {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "color", nullable = false)
    private String color;
    @Column(name = "name", nullable = false)
    private String name;


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
