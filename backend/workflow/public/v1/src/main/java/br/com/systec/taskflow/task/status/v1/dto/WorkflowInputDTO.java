package br.com.systec.taskflow.task.status.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class WorkflowInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -168337130341728216L;

    private Long id;
    private Long projectId;
    private String name;
    private List<StatusInputDTO> listOfStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StatusInputDTO> getListOfStatus() {
        return listOfStatus;
    }

    public void setListOfStatus(List<StatusInputDTO> listOfStatus) {
        this.listOfStatus = listOfStatus;
    }
}
