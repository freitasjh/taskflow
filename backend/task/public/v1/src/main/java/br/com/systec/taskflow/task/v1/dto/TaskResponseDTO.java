package br.com.systec.taskflow.task.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class TaskResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8305713731831433632L;

    private Long id;
    private String description;
    private String createByName;
    private String accountableName;
    private LocalDate dateCreated;
    private LocalDate dateUpdate;
    private LocalDate dateClose;
    private String teamName;
    private String observation;
    private String workflowStatusName;


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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDate dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public LocalDate getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDate dateClose) {
        this.dateClose = dateClose;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getWorkflowStatusName() {
        return workflowStatusName;
    }

    public void setWorkflowStatusName(String workflowStatusName) {
        this.workflowStatusName = workflowStatusName;
    }
}
