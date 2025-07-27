package br.com.systec.taskflow.task.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class TaskCommentDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6995516428447393548L;
    private Long id;
    private Long createdBy;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
