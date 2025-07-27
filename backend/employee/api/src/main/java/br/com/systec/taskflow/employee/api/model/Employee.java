package br.com.systec.taskflow.employee.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity
@Table(name = "employee")
public class Employee extends BaseModel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "cellphone")
    private String cellphone;
    @Column(name = "federal_id", nullable = false, unique = true)
    private String federalId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Departament department;

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

    public String getFederationId() {
        return federalId;
    }

    public void setFederationId(String federationId) {
        this.federalId = federationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Departament getDepartment() {
        return department;
    }

    public void setDepartment(Departament department) {
        this.department = department;
    }
}
