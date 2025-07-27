package br.com.systec.taskflow.kanban.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.kanban.api.exceptions.KanbanException;
import br.com.systec.taskflow.kanban.api.filter.KanbanFilter;
import br.com.systec.taskflow.kanban.api.model.Kanban;
import br.com.systec.taskflow.kanban.api.service.KanbanService;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.kanban.impl.converter.KanbanConverter;
import br.com.systec.taskflow.kanban.impl.repository.KanbanRepository;
import br.com.systec.taskflow.kanban.impl.repository.KanbanRepositoryJPA;
import br.com.systec.taskflow.workflow.api.service.WorkflowService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class KanbanServiceImpl implements KanbanService {
    private static final Logger log = LoggerFactory.getLogger(KanbanServiceImpl.class);

    private final KanbanRepository repository;
    private final KanbanRepositoryJPA repositoryJPA;
    private final WorkflowService workflowService;

    public KanbanServiceImpl(KanbanRepository repository, KanbanRepositoryJPA repositoryJPA, WorkflowService workflowService) {
        this.repository = repository;
        this.repositoryJPA = repositoryJPA;
        this.workflowService = workflowService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public KanbanVO create(KanbanVO kanbanBoard) throws BaseException {
        try {
            Kanban kanbanToSave = KanbanConverter.of().toModel(kanbanBoard);
            Kanban kanbanSaved = repository.save(kanbanToSave);

            return KanbanConverter.of().toVO(kanbanSaved);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o Kanban", e);
            throw new KanbanException(I18nTranslate.toLocale("kanban.create.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public KanbanVO update(KanbanVO kanbanBoard) throws BaseException {
        try {
            Kanban kanbanReturn = findKanbanById(kanbanBoard.getId());

            Kanban kanbanToUpdate = KanbanConverter.of().toModel(kanbanBoard);
            kanbanToUpdate.setDateCreated(kanbanReturn.getDateCreated());

            Kanban kanbanUpdated = repository.update(kanbanToUpdate);

            return KanbanConverter.of().toVO(kanbanUpdated);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar atualizar os dados do kanban", e);
            throw new KanbanException(I18nTranslate.toLocale("kanban.update.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public KanbanVO findById(Long id) throws BaseException {
        try {
            Kanban kanbanBoardReturn = findKanbanById(id);

            return KanbanConverter.of().toVO(kanbanBoardReturn);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
          throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar retornar os dados do kanban", e);
            throw new KanbanException(I18nTranslate.toLocale("kanban.find.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<KanbanVO> findByFilter(KanbanFilter filter) throws BaseException {
        try {
            Page<Kanban> pageKanban = repositoryJPA.findAll(filter.getSpecification(), filter.getPageable());

            return pageKanban.map(item -> KanbanConverter.of().toVO(item));
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar listar os kanban", e);
            throw new KanbanException(I18nTranslate.toLocale("kanban.filter.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Long getKanbanWorkflowInitial(Long projectId) throws BaseException {
        try {
            Kanban kanban = repository.findByProjectId(projectId).orElseThrow(() -> new KanbanException(I18nTranslate.toLocale("kanban.not.found")));

            StatusVO status = workflowService.findInitialStatusByWorkflowId(kanban.getWorkflowId());

            return status.getId();
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Erro ao tentar pesquisar os dados para o workflow", e);
            throw new BaseException(e);
        }
    }

    private Kanban findKanbanById(Long id) throws BaseException {
        return repository.findById(id)
                .orElseThrow(() -> new KanbanException(I18nTranslate.toLocale("kanban.find.error")));
    }
}
