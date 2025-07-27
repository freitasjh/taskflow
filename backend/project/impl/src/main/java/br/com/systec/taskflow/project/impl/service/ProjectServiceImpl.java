package br.com.systec.taskflow.project.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.project.api.converter.ProjectConverter;
import br.com.systec.taskflow.project.api.filter.ProjectFilter;
import br.com.systec.taskflow.project.api.model.Project;
import br.com.systec.taskflow.project.api.service.ProjectService;
import br.com.systec.taskflow.project.api.vo.ProjectVO;
import br.com.systec.taskflow.project.impl.repository.ProjectRepository;
import br.com.systec.taskflow.project.impl.repository.jpa.ProjectRepositoryJpa;
import br.com.systec.taskflow.rabbitmq.utils.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository repository;
    private final ProjectRepositoryJpa repositoryJpa;
    private final RabbitTemplate rabbitTemplate;

    public ProjectServiceImpl(ProjectRepository repository, ProjectRepositoryJpa repositoryJpa,
                              RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.repositoryJpa = repositoryJpa;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProjectVO create(ProjectVO projectVO) throws BaseException {
        try {
            Project project = ProjectConverter.toEntity(projectVO);

            Project projectSaved = repository.save(project);

            rabbitTemplate.convertAndSend(RabbitMQConstants.TASKFLOW_TEMPLATE_WORKFLOW_INSTALL, projectSaved.getId());
            return ProjectConverter.toVO(projectSaved);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar salvar o projeto", e);
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProjectVO update(ProjectVO projectVO) throws BaseException {
        try {
            Project project = ProjectConverter.toEntity(projectVO);

            Project projectUpdate = repository.update(project);

            return ProjectConverter.toVO(projectUpdate);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar atualizar o projeto", e);
            throw new BaseException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<ProjectVO> findByFilter(ProjectFilter filter) throws BaseException {
        try {
            Page<Project> projectsPage = repositoryJpa.findAll(filter.getSpecification(), filter.getPageable());

            return projectsPage.map(ProjectConverter::toVO);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao tentar buscar os projetos", e);
            throw new BaseException("Erro ao buscar projetos: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProjectVO findById(Long id) throws BaseException {
        Project projectReturn = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Projeto não encontrado"));

        return ProjectConverter.toVO(projectReturn);
    }
}
