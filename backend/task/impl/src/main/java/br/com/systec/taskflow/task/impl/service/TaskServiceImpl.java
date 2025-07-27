package br.com.systec.taskflow.task.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.employee.api.service.EmployeeService;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.kanban.api.service.KanbanService;
import br.com.systec.taskflow.project.api.service.ProjectService;
import br.com.systec.taskflow.project.api.vo.ProjectVO;
import br.com.systec.taskflow.security.service.SecurityService;
import br.com.systec.taskflow.task.api.converter.TaskConverter;
import br.com.systec.taskflow.task.api.exceptions.TaskException;
import br.com.systec.taskflow.task.api.filter.TaskFilter;
import br.com.systec.taskflow.task.api.model.Task;
import br.com.systec.taskflow.task.api.model.TaskComment;
import br.com.systec.taskflow.task.api.model.TaskStatus;
import br.com.systec.taskflow.task.api.service.TaskService;
import br.com.systec.taskflow.task.api.vo.TaskFilterResponseVO;
import br.com.systec.taskflow.task.api.vo.TaskVO;
import br.com.systec.taskflow.task.impl.repository.TaskRepository;
import br.com.systec.taskflow.task.impl.repository.TaskRepositoryJPA;
import br.com.systec.taskflow.workflow.api.service.WorkflowService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository repository;
    private final TaskRepositoryJPA repositoryJPA;
    private final EmployeeService employeeService;
    private final SecurityService securityService;
    private final KanbanService kanbanService;
    private final WorkflowService workflowService;
    private final ProjectService projectService;

    public TaskServiceImpl(TaskRepository repository, TaskRepositoryJPA repositoryJPA, EmployeeService employeeService,
                           SecurityService securityService, KanbanService kanbanService, WorkflowService workflowService,
                           ProjectService projectService) {
        this.repository = repository;
        this.repositoryJPA = repositoryJPA;
        this.employeeService = employeeService;
        this.securityService = securityService;
        this.kanbanService = kanbanService;
        this.workflowService = workflowService;
        this.projectService = projectService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TaskVO create(TaskVO taskVO) throws BaseException {
        try {
            Task taskToSave = TaskConverter.of().toModel(taskVO);
            taskToSave.setCreatedBy(securityService.getCurrentEmployeeId());
            taskToSave.setCode(generateNextCodTask(taskToSave));

            if (taskToSave.getStatus() == null) {
                taskToSave.setStatus(TaskStatus.OPEN);
            }

            taskToSave.addComment(I18nTranslate.toLocale("create.task.observation"), taskToSave.getCreatedBy());

            Long workflowStatusFirst = kanbanService.getKanbanWorkflowInitial(taskVO.getTeamId());
            taskToSave.setWorkflowStatus(workflowStatusFirst);

            Task taskSaved = repository.save(taskToSave);

            return TaskConverter.of().toVO(taskSaved);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tetar salvar a tarefa", e);
            throw new TaskException(I18nTranslate.toLocale("task.error.exception"), e);
        }
    }

    private String generateNextCodTask(Task task) throws BaseException {
        ProjectVO project = projectService.findById(task.getProjectId());
        int nextCode = repository.getNextCode();

        return project.getPrefix() + "-" + nextCode;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TaskVO update(TaskVO taskVO) throws BaseException {
        try {
            Task taskReturn = findTaskById(taskVO.getId());

            Task taskToSave = TaskConverter.of().toModel(taskVO);
            taskToSave.setDateCreated(taskReturn.getDateCreated());

            Task taskUpdate = repository.update(taskToSave);

            return TaskConverter.of().toVO(taskUpdate);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar a tarefa", e);
            throw new TaskException(I18nTranslate.toLocale("task.error.update"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TaskVO findById(Long id) throws BaseException {
        try {
            Task taskToReturn = findTaskById(id);

            return TaskConverter.of().toVO(taskToReturn);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar a tarefa", e);
            throw new TaskException(I18nTranslate.toLocale("task.error.update"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<TaskFilterResponseVO> findByFilter(TaskFilter filter) throws BaseException {
        try {
            Page<Task> taskPageResult = repositoryJPA.findAll(filter.getSpecification(), filter.getPageable());

            return taskPageResult.map(task -> {
                TaskFilterResponseVO responseVO = new TaskFilterResponseVO();
                responseVO.setId(task.getId());
                responseVO.setDescription(task.getDescription());
                responseVO.setObservation(task.getObservation());
                responseVO.setDateCreate(task.getDateCreated());
                responseVO.setDateUpdate(task.getDateUpdated());

                EmployeeVO employeeCreate = employeeService.findById(task.getCreatedBy());
                responseVO.setCreateByName(employeeCreate.getName());

                if (task.getAccountable() != null) {
                    EmployeeVO employeeAccountable = employeeService.findById(task.getAccountable());
                    responseVO.setAccountableName(employeeAccountable.getName());
                }

                if (task.getWorkflowStatus() != null) {
                    StatusVO status = workflowService.findStatusById(task.getWorkflowStatus());
                    responseVO.setWorkFlowStatusName(status.getName());
                }

                return responseVO;
            });

        } catch (Exception e) {
            throw new TaskException(I18nTranslate.toLocale("task.find.filter.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addAccountable(Long accountableId, Long taskId) throws BaseException {
        try {
            Task task = findTaskById(taskId);
            task.setAccountable(accountableId);

            repository.update(task);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar adicionar o responsavel na tarefa", e);
            throw new TaskException(I18nTranslate.toLocale("task.update.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addComment(String content, Long taskId) throws BaseException {
        try {
            Task task = findTaskById(taskId);

            task.addComment(content, securityService.getCurrentEmployeeId());

            repository.update(task);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar adicionar o comentario", e);
            throw new TaskException(I18nTranslate.toLocale("error.add.comment"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelTask(Long id, String content) throws BaseException {
        try {
            Task task = findTaskById(id);
            task.setStatus(TaskStatus.CANCELED);
            task.addComment(content, securityService.getCurrentEmployeeId());

            repository.update(task);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar cancelar a tarefa", e);
            throw new TaskException(I18nTranslate.toLocale("task.cancel.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateComment(String content, Long taskId, Long commentId) throws BaseException {
        Task task = findTaskById(taskId);

        TaskComment taskComment = task.getListComments().stream()
                .filter(item -> item.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new TaskException(I18nTranslate.toLocale("comment.not.found")));

        taskComment.setContent(content);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeComment(Long taskCommentId, Long taskId) throws BaseException {
        try {
            Task task = findTaskById(taskId);

            TaskComment taskComment = task.getListComments().stream()
                    .filter(item -> item.getId().equals(taskCommentId))
                    .findFirst()
                    .orElseThrow(() -> new TaskException(I18nTranslate.toLocale("observation.not.found")));

            task.getListComments().remove(taskComment);

            repository.update(task);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar adicionar o comentario", e);
            throw new TaskException(I18nTranslate.toLocale("error.remove.comment"), e);
        }
    }

    private Task findTaskById(Long taskId) throws BaseException {
        return repository.findById(taskId).orElseThrow(() ->
                new TaskException(I18nTranslate.toLocale("task.not.found")));
    }
}
