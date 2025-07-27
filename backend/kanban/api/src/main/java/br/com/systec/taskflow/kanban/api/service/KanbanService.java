package br.com.systec.taskflow.kanban.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.kanban.api.filter.KanbanFilter;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;
import org.springframework.data.domain.Page;

public interface KanbanService {

    KanbanVO create(KanbanVO kanbanBoard) throws BaseException;

    KanbanVO update(KanbanVO kanbanBoard) throws BaseException;

    KanbanVO findById(Long id) throws BaseException;

    Page<KanbanVO> findByFilter(KanbanFilter filter) throws BaseException;

    Long getKanbanWorkflowInitial(Long teamId) throws BaseException;
}
