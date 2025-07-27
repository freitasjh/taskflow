package br.com.systec.taskflow.task.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class TaskInputObservationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7187000063591665173L;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
