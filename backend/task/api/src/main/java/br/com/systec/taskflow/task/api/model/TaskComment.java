package br.com.systec.taskflow.task.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;

@Entity
@Table(name = "task_comment")
public class TaskComment extends BaseModel {
    @Serial
    private static final long serialVersionUID = -546467537382134837L;

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "content")
    private String content;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "task_id")
    private Task task;

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
