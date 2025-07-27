package br.com.systec.taskflow.task.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class TaskCommentVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 260064802202189170L;

    private Long id;
    private Long createBy;
    private String content;
    private TaskVO task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TaskVO getTask() {
        return task;
    }

    public void setTask(TaskVO task) {
        this.task = task;
    }
}
