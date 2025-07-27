package br.com.systec.taskflow.task.api.vo;

import br.com.systec.taskflow.task.api.model.TaskPriority;
import br.com.systec.taskflow.task.api.model.TaskStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1264096912238763802L;

    private Long id;
    private String code;
    private String description;
    private LocalDateTime dateClose;
    private LocalDate dateCreate;
    private LocalDate dateUpdate;
    private TaskStatus status;
    private Long workflowStatus;
    private Long teamId;
    private Long createdBy;
    private Long accountable;
    private TaskPriority priority;
    private String observation;
    private List<TaskCommentVO> listComments;
    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDateTime dateClose) {
        this.dateClose = dateClose;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(Long workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getAccountable() {
        return accountable;
    }

    public void setAccountable(Long accountable) {
        this.accountable = accountable;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDate getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDate dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<TaskCommentVO> getListComments() {
        return listComments;
    }

    public void setListComments(List<TaskCommentVO> listComments) {
        this.listComments = listComments;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
