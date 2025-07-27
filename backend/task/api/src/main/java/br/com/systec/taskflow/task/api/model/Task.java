package br.com.systec.taskflow.task.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
public class Task extends BaseModel {
    @Serial
    private static final long serialVersionUID = -4992736890706388853L;

    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
    @Column(name = "date_close")
    private LocalDateTime dateClose;
    @Column(name = "status")
    private TaskStatus status;
    @Column(name = "workflow_status")
    private Long workflowStatus;
    @Column(name = "accountable")
    private Long accountable;
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<TaskComment> listComments;
    @Column(name = "task_priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    @Column(name = "observation")
    private String observation;
    @Column(name = "project_id")
    private Long projectId;

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

    public Long getAccountable() {
        return accountable;
    }

    public void setAccountable(Long accountable) {
        this.accountable = accountable;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public List<TaskComment> getListComments() {
        return listComments;
    }

    public void setListComments(List<TaskComment> listComments) {
        this.listComments = listComments;
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

    public void addComment(String content, Long employeeId) {
        TaskComment taskObservation = new TaskComment();
        taskObservation.setContent(content);
        taskObservation.setTask(this);
        taskObservation.setCreatedBy(employeeId);

        if (listComments == null) {
            listComments = new ArrayList<>();
        }

        listComments.add(taskObservation);
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
