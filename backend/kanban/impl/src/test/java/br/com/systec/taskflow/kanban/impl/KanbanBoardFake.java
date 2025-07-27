package br.com.systec.taskflow.kanban.impl;

import br.com.systec.taskflow.kanban.api.model.Kanban;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;

public class KanbanBoardFake {

    public static KanbanVO toVO() {
        KanbanVO kanbanVO = new KanbanVO();
        kanbanVO.setId(1L);
        kanbanVO.setName("Test Board");
        kanbanVO.setProjectId(2L);
        kanbanVO.setWorkflowId(3L);

        return kanbanVO;
    }

    public static Kanban toModel() {
        Kanban kanban = new Kanban();
        kanban.setId(1L);
        kanban.setName("Test Board");
        kanban.setProjectId(2L);
        kanban.setWorkflowId(3L);

        return kanban;
    }
}
