package br.com.systec.taskflow.employee.v1.dto;

import br.com.systec.taskflow.employee.api.model.Departament;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public class EmployeeInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    private String phone;
    private String cellphone;
    @NotBlank
    private String federalId;
    @NotBlank
    private String username;
    private String password;
    private Departament departament;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getFederalId() {
        return federalId;
    }

    public void setFederalId(String federalId) {
        this.federalId = federalId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }
}
