package br.com.systec.taskflow.workflow.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.workflow.api.exceptions.StatusException;
import br.com.systec.taskflow.workflow.api.filter.StatusFilter;
import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.service.StatusService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.converter.StatusConverter;
import br.com.systec.taskflow.workflow.impl.repository.StatusRepository;
import br.com.systec.taskflow.workflow.impl.repository.StatusRepositoryJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class StatusServiceImpl implements StatusService {
    private static final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository repository;
    private final StatusRepositoryJpa repositoryJpa;

    public StatusServiceImpl(StatusRepository repository, StatusRepositoryJpa repositoryJpa) {
        this.repository = repository;
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StatusVO create(StatusVO statusVO) throws BaseException {
        try {
            Status statusToSave = StatusConverter.toModel(statusVO);
            Status statusSaved = repository.save(statusToSave);

            return StatusConverter.toVO(statusSaved);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o status", e);
            throw new StatusException(I18nTranslate.toLocale("status.create.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StatusVO update(StatusVO statusVO) throws BaseException {
        try {
            Status statusToUpdate = StatusConverter.toModel(statusVO);
            Status statusUpdate = repository.update(statusToUpdate);

            return StatusConverter.toVO(statusUpdate);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar atualziar o status", e);
            throw new StatusException(I18nTranslate.toLocale("status.update.error"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(StatusVO statusVO) throws BaseException {
        try {
            Status status = repository.findById(statusVO.getId())
                    .orElseThrow(() -> new StatusException(I18nTranslate
                            .toLocale("status.not.found.exception", statusVO.getId()))
                    );

            repository.delete(status);

        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar excluir o status", e);
            throw new StatusException(I18nTranslate.toLocale("status.delete.error"));
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<StatusVO> findAll() throws BaseException {
        try {
            List<Status> listStatus = StreamSupport.stream(repository.findAll().spliterator(), false).toList();

            return StatusConverter.toVOList(listStatus);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar listar os dados do status", e);
            throw new StatusException(I18nTranslate.toLocale("status.error.find"), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<StatusVO> findByFilter(StatusFilter filter) throws BaseException {
        try {
            Page<Status> pageStatus = repositoryJpa.findAll(filter.getSpecification(), filter.getPageable());

            return pageStatus.map(StatusConverter::toVO);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar pesquisar com filtros os status", e);
            throw new StatusException(I18nTranslate.toLocale("status.error.find"));
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Status> findByWorkflowId(Long workflowId) throws BaseException {
        try {
            return repository.findByWorkflowId(workflowId);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar listar os staus pelo id do workflow", e);
            throw new StatusException(I18nTranslate.toLocale("status.error.find"));
        }
    }
}
