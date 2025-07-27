package br.com.systec.taskflow.project.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3670927659938521279L;

    private Long id;
    private String name;

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
}
