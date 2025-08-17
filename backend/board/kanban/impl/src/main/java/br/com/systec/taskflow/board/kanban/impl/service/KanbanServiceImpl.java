package br.com.systec.taskflow.board.kanban.impl.service;

import br.com.systec.board.kanban.api.service.KanbanService;
import br.com.systec.board.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.board.kanban.impl.converter.KanbanConverter;
import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.task.api.service.TaskService;
import br.com.systec.taskflow.workflow.api.service.WorkflowService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class KanbanServiceImpl implements KanbanService {

    private final WorkflowService workflowService;
    private final TaskService taskService;

    public KanbanServiceImpl(WorkflowService workflowService, TaskService taskService) {
        this.workflowService = workflowService;
        this.taskService = taskService;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public KanbanVO generateKanban(Long projectId) throws BaseException {
        try {
            WorkflowVO workflow = workflowService.findWorkflowByProjectId(projectId);

            List<StatusVO> listOfStatus = workflow.getListOfStatus().stream().sorted(
                    Comparator.comparingInt(s -> s.getOrder() != null ? s.getOrder() : 0)
            ).toList();

            return KanbanConverter.of().toKanban(listOfStatus);

        } catch (Exception e) {
            throw new BaseException("Error generating Kanban for project ID: " + projectId, e);
        }
    }
}
