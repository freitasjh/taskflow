package br.com.systec.taskflow.workflow.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class WorkflowVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5968723603369057230L;

    private Long id;
    private String name;
    private Long projectId;
    private List<StatusVO> listOfStatus;

    public WorkflowVO() {
    }

    public WorkflowVO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<StatusVO> getListOfStatus() {
        return listOfStatus;
    }

    public void setListOfStatus(List<StatusVO> listOfStatus) {
        this.listOfStatus = listOfStatus;
    }

//    public List<TransitionVO> getListOfTransition() {
//        return listOfTransition;
//    }
//
//    public void setListOfTransition(List<TransitionVO> listOfTransition) {
//        this.listOfTransition = listOfTransition;
//    }
}
