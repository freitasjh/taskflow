package br.com.systec.taskflow.team.v1.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5936161244697448786L;
    @NotNull(message = "Informe o ID do funcionário")
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
