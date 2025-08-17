package br.com.systec.taskflow.board.kanban.impl.converter;

import br.com.systec.board.kanban.api.vo.KanbanStatusVO;
import br.com.systec.board.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;

import java.util.ArrayList;
import java.util.List;

public class KanbanConverter {

    public static KanbanConverter of() {
        return new KanbanConverter();
    }

    public KanbanVO toKanban(List<StatusVO> listStatus) {
        KanbanVO kanbanVO = new KanbanVO();

        kanbanVO.setStatus(listStatus.stream()
                .map(status -> {
                    KanbanStatusVO kanbanStatusVO = new KanbanStatusVO();
                    kanbanStatusVO.setTitle(status.getName());
                    kanbanStatusVO.setTasks(new ArrayList<>());
                    return kanbanStatusVO;
                })
                .toList());
        return kanbanVO;
    }
}
