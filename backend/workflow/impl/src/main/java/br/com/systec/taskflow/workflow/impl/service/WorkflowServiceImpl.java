package br.com.systec.taskflow.workflow.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.workflow.api.converter.StatusConverter;
import br.com.systec.taskflow.workflow.api.converter.WorkflowConverter;
import br.com.systec.taskflow.workflow.api.exceptions.WorkflowException;
import br.com.systec.taskflow.workflow.api.filter.WorkflowFilter;
import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.model.Workflow;
import br.com.systec.taskflow.workflow.api.service.WorkflowService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import br.com.systec.taskflow.workflow.impl.repository.WorkflowRepository;
import br.com.systec.taskflow.workflow.impl.repository.WorkflowRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    private static final Logger log = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    private final WorkflowRepository repository;
    private final WorkflowRepositoryJPA repositoryJPA;

    public WorkflowServiceImpl(WorkflowRepository repository, WorkflowRepositoryJPA repositoryJPA) {
        this.repository = repository;
        this.repositoryJPA = repositoryJPA;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WorkflowVO create(WorkflowVO workflow) throws BaseException {
        try {
            Workflow workflowToSave = WorkflowConverter.toModel(workflow);
            Workflow workflowSaved = repository.save(workflowToSave);

            return WorkflowConverter.toVO(workflowSaved);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o workflow", e);
            throw new WorkflowException(I18nTranslate.toLocale("workflow.save.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WorkflowVO update(WorkflowVO workflowVO) throws BaseException {
        try {
            Workflow workflowReturn = findByIdInternal(workflowVO.getId());

            Workflow workflowToSave = WorkflowConverter.toModel(workflowVO);
            workflowToSave.setDateCreated(workflowReturn.getDateCreated());

            Workflow workflowUpdate = repository.update(workflowToSave);

            return WorkflowConverter.toVO(workflowUpdate);
        } catch (BaseException e) {
            log.error("Ocorreu um erro ao tentar atualizar o workflow", e);
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar atualizar o workflow", e);
            throw new WorkflowException(I18nTranslate.toLocale("workflow.save.update"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public WorkflowVO findById(Long id) throws BaseException {
        try {
            Workflow workflow = findByIdInternal(id);

            return WorkflowConverter.toVO(workflow);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar listar os dados do workflow", e);
            throw e;
        }
    }

    private Workflow findByIdInternal(Long id) throws BaseException {
        try {
            return repository.findById(id).orElseThrow(() -> new WorkflowException(I18nTranslate.toLocale("workflow.not.found")));
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new WorkflowException("Ocorreu um erro ao retornar os dados do workflow",e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<WorkflowVO> findByFilter(WorkflowFilter filter) throws BaseException {
        try {
            Page<Workflow> pageWorkflow = repositoryJPA.findAll(filter.getSpecification(), filter.getPageable());

            return pageWorkflow.map(WorkflowConverter::toVO);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar listar os dados do workflow", e);
            throw new WorkflowException(I18nTranslate.toLocale("workflow.find.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public StatusVO findStatusById(Long statusId) throws BaseException {
        Status status = repository.findStatusById(statusId)
                .orElseThrow(() -> new WorkflowException(I18nTranslate.toLocale("workflow.status.not.found")));

        return StatusConverter.toVO(status);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public StatusVO findInitialStatusByWorkflowId(Long workflowId) throws BaseException {
        Status status = repository.findStatusByInitialAndWorkflowId(workflowId)
                .orElseThrow(() -> new WorkflowException(I18nTranslate.toLocale("workflow.status.initial.not.found")));

        return StatusConverter.toVO(status);
    }
}
