package br.com.systec.board.kanban.api.service;

import br.com.systec.board.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.commons.exceptions.BaseException;

public interface KanbanService {

    KanbanVO generateKanban(Long projectId) throws BaseException;
}
