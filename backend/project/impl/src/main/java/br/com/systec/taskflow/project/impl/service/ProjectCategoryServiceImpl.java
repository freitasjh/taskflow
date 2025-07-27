package br.com.systec.taskflow.project.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.commons.query.PaginatedList;
import br.com.systec.taskflow.project.api.converter.ProjectCategoryConverter;
import br.com.systec.taskflow.project.api.exceptions.validation.ProjectCategoryNotFoundException;
import br.com.systec.taskflow.project.api.filter.ProjectCategoryFilter;
import br.com.systec.taskflow.project.api.model.ProjectCategory;
import br.com.systec.taskflow.project.api.service.ProjectCategoryService;
import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import br.com.systec.taskflow.project.impl.repository.ProjectCategoryRepository;
import br.com.systec.taskflow.project.impl.repository.jpa.ProjectCategoryRepositoryJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectCategoryServiceImpl implements ProjectCategoryService {
    private static final Logger log = LoggerFactory.getLogger(ProjectCategoryServiceImpl.class);

    private final ProjectCategoryRepository repository;
    private final ProjectCategoryRepositoryJpa repositoryJpa;

    public ProjectCategoryServiceImpl(ProjectCategoryRepository repository, ProjectCategoryRepositoryJpa repositoryJpa) {
        this.repository = repository;
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProjectCategoryVO create(ProjectCategoryVO projectCategoryVO) throws BaseException {
        try {
            ProjectCategory projectCategory = ProjectCategoryConverter.toModel(projectCategoryVO);

            ProjectCategory projectCategoryAfterSave = repository.save(projectCategory);

            return ProjectCategoryConverter.toVO(projectCategoryAfterSave);
        } catch (Exception e) {
            log.error("Error creating project category", e);
            throw new BaseException("Ocorreu um erro ao tentar criar a categoria", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProjectCategoryVO update(ProjectCategoryVO projectCategoryVO) throws BaseException {
        try {
            ProjectCategory projectCategory = ProjectCategoryConverter.toModel(projectCategoryVO);

            ProjectCategory projectCategoryExisting = repository.findById(projectCategory.getId())
                    .orElseThrow(ProjectCategoryNotFoundException::new);

            projectCategory.setDateCreated(projectCategoryExisting.getDateCreated());

            ProjectCategory projectCategoryAfterUpdate = repository.update(projectCategory);

            return ProjectCategoryConverter.toVO(projectCategoryAfterUpdate);
        } catch (ProjectCategoryNotFoundException e) {
            log.error("Project Category not found {}",projectCategoryVO.getId(),  e);
            throw e;
        } catch (Exception e) {
            log.error("Error updating project category", e);
            throw new BaseException("Ocorreu um erro ao tentar salvar a categoria", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProjectCategoryVO findById(Long id) throws BaseException {
        try {
            ProjectCategory projectCategoryReturn = repository.findById(id)
                    .orElseThrow(ProjectCategoryNotFoundException::new);

            return ProjectCategoryConverter.toVO(projectCategoryReturn);
        } catch (ProjectCategoryNotFoundException e) {
            log.error("Project Category not found {}", id,  e);
            throw e;
        } catch (Exception e) {
            log.error("Error to find a category", e);
            throw new BaseException("Ocorreu um erro ao tentar pesquisa a categoria de projeto", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<ProjectCategoryVO> findByFilter(ProjectCategoryFilter filter) throws BaseException {
        try {
            Page<ProjectCategory> listOfCategory = repositoryJpa.findAll(
                    filter.getSpecification(),
                    filter.getPageable()
            );

            return ProjectCategoryConverter.toPageVO(listOfCategory);
        } catch (Exception e) {
            log.error("Error to find", e);
            throw new BaseException("Ocorreu um erro ao tentar pesqusiar as categorias",e);
        }
    }
}
