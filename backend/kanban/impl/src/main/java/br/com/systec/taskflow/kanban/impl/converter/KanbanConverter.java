package br.com.systec.taskflow.kanban.impl.converter;

import br.com.systec.taskflow.kanban.api.model.Kanban;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;

public class KanbanConverter {

    private KanbanConverter(){}

    public static KanbanConverter of(){
        return new KanbanConverter();
    }

    public KanbanVO toVO(Kanban kanban) {
        KanbanVO kanbanVO = new KanbanVO();
        kanbanVO.setId(kanban.getId());
        kanbanVO.setName(kanban.getName());
        kanbanVO.setWorkflowId(kanban.getWorkflowId());
        kanbanVO.setProjectId(kanban.getProjectId());

        return kanbanVO;
    }

    public Kanban toModel(KanbanVO kanbanVO) {
        Kanban kanban = new Kanban();
        kanban.setId(kanbanVO.getId());
        kanban.setName(kanbanVO.getName());
        kanban.setWorkflowId(kanbanVO.getWorkflowId());
        kanban.setProjectId(kanbanVO.getProjectId());

        return kanban;
    }
}
