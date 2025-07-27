package br.com.systec.taskflow.task.v1.dto;

import br.com.systec.taskflow.task.api.model.TaskPriority;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2868852252572406200L;

    private Long id;
    private String code;
    private String description;
    @NotNull(message = "Informe a equipe")
    private Long teamId;
    private Long createdBy;
    private String status;
    private Long workflowStatusId;
    private String workflowStatusName;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateClose;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateUpdate;
    private Long accountable;
    @NotNull(message = "Informe a criticidade da tarefa")
    private TaskPriority priority;
    private String observation;
    private List<TaskCommentDTO> listComments;
    private Long kanbanBoardId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWorkflowStatusId() {
        return workflowStatusId;
    }

    public void setWorkflowStatusId(Long workflowStatusId) {
        this.workflowStatusId = workflowStatusId;
    }

    public String getWorkflowStatusName() {
        return workflowStatusName;
    }

    public void setWorkflowStatusName(String workflowStatusName) {
        this.workflowStatusName = workflowStatusName;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDateTime dateClose) {
        this.dateClose = dateClose;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public List<TaskCommentDTO> getListComments() {
        return listComments;
    }

    public void setListComments(List<TaskCommentDTO> listComments) {
        this.listComments = listComments;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getKanbanBoardId() {
        return kanbanBoardId;
    }

    public void setKanbanBoardId(Long kanbanBoardId) {
        this.kanbanBoardId = kanbanBoardId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
