package br.com.systec.taskflow.task.api.vo;

import java.time.LocalDate;

public class TaskFilterResponseVO {

    private Long id;
    private String description;
    private String createByName;
    private String accountableName;
    private String teamName;
    private LocalDate dateCreate;
    private LocalDate dateUpdate;
    private String workFlowStatusName;
    private String observation;

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

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getAccountableName() {
        return accountableName;
    }

    public void setAccountableName(String accountableName) {
        this.accountableName = accountableName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getWorkFlowStatusName() {
        return workFlowStatusName;
    }

    public void setWorkFlowStatusName(String workFlowStatusName) {
        this.workFlowStatusName = workFlowStatusName;
    }

    public LocalDate getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDate dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
