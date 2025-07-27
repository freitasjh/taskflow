package br.com.systec.taskflow.project.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.project.api.converter.ProjectStatusConverter;
import br.com.systec.taskflow.project.api.exceptions.ProjectStatusException;
import br.com.systec.taskflow.project.api.exceptions.validation.ProjectNotFoundException;
import br.com.systec.taskflow.project.api.exceptions.validation.ProjectStatusNotFoundException;
import br.com.systec.taskflow.project.api.filter.ProjectStatusFilter;
import br.com.systec.taskflow.project.api.model.ProjectStatus;
import br.com.systec.taskflow.project.api.service.ProjectStatusService;
import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import br.com.systec.taskflow.project.impl.repository.ProjectStatusRepository;
import br.com.systec.taskflow.project.impl.repository.jpa.ProjectStatusRepositoryJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService {
    private static final Logger log = LoggerFactory.getLogger(ProjectStatusServiceImpl.class);

    private final ProjectStatusRepository repository;
    private final ProjectStatusRepositoryJpa repositoryJpa;

    public ProjectStatusServiceImpl(ProjectStatusRepository repository, ProjectStatusRepositoryJpa repositoryJpa) {
        this.repository = repository;
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProjectStatusVO create(ProjectStatusVO projectStatusVO) throws BaseException {
        try {
            ProjectStatus projectStatus = ProjectStatusConverter.toModel(projectStatusVO);

            ProjectStatus projectStatusAfterSave = repository.save(projectStatus);

            return ProjectStatusConverter.toVO(projectStatusAfterSave);
        } catch (Exception e) {
            log.error("Error creating ProjectStatus: {}", e.getMessage(), e);
            throw new BaseException("Error creating ProjectStatus", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProjectStatusVO update(ProjectStatusVO projectStatusVO) throws BaseException {
        try {
            ProjectStatus projectStatus = ProjectStatusConverter.toModel(projectStatusVO);

            ProjectStatus projectStatusSaved = repository.findById(projectStatus.getId())
                    .orElseThrow(ProjectNotFoundException::new);

            projectStatus.setDateCreated(projectStatusSaved.getDateCreated());

            projectStatus = repository.update(projectStatus);

            return ProjectStatusConverter.toVO(projectStatus);
        } catch (ProjectNotFoundException e) {
            log.error("Error finding ProjectStatus by id: {}", projectStatusVO.getId(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error updating ProjectStatus: {}", e.getMessage(), e);
            throw new ProjectStatusException("Error updating ProjectStatus", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProjectStatusVO findById(Long id) throws BaseException {
        try {
            ProjectStatus projectStatus = repository.findById(id)
                    .orElseThrow(ProjectStatusNotFoundException::new);

            return ProjectStatusConverter.toVO(projectStatus);
        } catch (ProjectStatusNotFoundException e) {
            log.error("Error finding ProjectStatus by id: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error finding ProjectStatus by id: {} - {}", id, e.getMessage(), e);
            throw new ProjectStatusException("Error finding ProjectStatus by id: " + id, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) throws BaseException {
        try {
            log.debug("Deleting ProjectStatus with id: {}", id);
            ProjectStatus projectStatus = repository.findById(id)
                    .orElseThrow(ProjectStatusNotFoundException::new);

            repository.delete(projectStatus);
            log.debug("ProjectStatus with id: {} deleted successfully", id);
        } catch (ProjectStatusNotFoundException e) {
            log.error("Error deleting ProjectStatus with id: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error deleting ProjectStatus with id: {} - {}", id, e.getMessage(), e);
            throw new ProjectStatusException("Error deleting ProjectStatus with id: " + id, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProjectStatusVO> findAll() throws BaseException {
        try {
            List<ProjectStatus> listProjectStatus = StreamSupport.stream(repository.findAll().spliterator(), false).toList();
            return ProjectStatusConverter.toListVO(listProjectStatus);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar listar os status de projeto", e);
            throw new ProjectStatusException("Ocorreu um erro ao tentar listar os status de projeto ", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<ProjectStatusVO> findByFilter(ProjectStatusFilter filter) throws BaseException {
        try {
            Page<ProjectStatus> listOfStatus = repositoryJpa.findAll(filter.getSpecification(), filter.getPageable());

            return ProjectStatusConverter.toPageVO(listOfStatus);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar listar os Status", e);
            throw new ProjectStatusException("Ocorreu um erro ao tentar listar o status", e);
        }
    }
}
