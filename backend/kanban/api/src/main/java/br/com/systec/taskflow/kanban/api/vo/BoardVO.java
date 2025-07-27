package br.com.systec.taskflow.kanban.api.vo;

import java.io.Serial;
import java.io.Serializable;

public class BoardVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3380221552256601142L;

    private Long projectId;
    private Long kanbanId;
    private String kanbanName;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getKanbanId() {
        return kanbanId;
    }

    public void setKanbanId(Long kanbanId) {
        this.kanbanId = kanbanId;
    }

    public String getKanbanName() {
        return kanbanName;
    }

    public void setKanbanName(String kanbanName) {
        this.kanbanName = kanbanName;
    }
}
