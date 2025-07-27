package br.com.systec.taskflow.workflow.api.model;

import br.com.systec.taskflow.commons.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workflow")
public class Workflow extends BaseModel {
    @Serial
    private static final long serialVersionUID = 6223511858588272218L;

    @Column(name = "name")
    private String name;
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<Status> listOfStatus;
//    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
//    private List<Transition> listOfTransition;


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

    public List<Status> getListOfStatus() {
        if(listOfStatus == null) {
            listOfStatus = new ArrayList<>();
        }
        return listOfStatus;
    }

    public void setListOfStatus(List<Status> listOfStatus) {
        this.listOfStatus = listOfStatus;
    }

    public void addStatus(Status status) {
        if(listOfStatus == null) {
            listOfStatus = new ArrayList<>();
        }

        status.setWorkflow(this);
        listOfStatus.add(status);
    }

//    public List<Transition> getListOfTransition() {
//        return listOfTransition;
//    }
//
//    public void setListOfTransition(List<Transition> listOfTransition) {
//        this.listOfTransition = listOfTransition;
//    }
}
