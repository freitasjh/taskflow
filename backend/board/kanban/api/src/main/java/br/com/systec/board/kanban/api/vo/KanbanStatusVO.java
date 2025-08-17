package br.com.systec.board.kanban.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class KanbanStatusVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7832852134323783300L;

    private String title;
    private List<KanbanTasksVO> tasks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<KanbanTasksVO> getTasks() {
        return tasks;
    }

    public void setTasks(List<KanbanTasksVO> tasks) {
        this.tasks = tasks;
    }
}
