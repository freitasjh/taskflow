package br.com.systec.taskflow.project.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.project.api.exceptions.validation.ProjectCategoryNotFoundException;
import br.com.systec.taskflow.project.api.filter.ProjectCategoryFilter;
import br.com.systec.taskflow.project.api.model.ProjectCategory;
import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import br.com.systec.taskflow.project.impl.fake.ProjectFake;
import br.com.systec.taskflow.project.impl.repository.ProjectCategoryRepository;
import br.com.systec.taskflow.project.impl.repository.jpa.ProjectCategoryRepositoryJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;

class ProjectCategoryServiceImplTest {

    @Mock
    private ProjectCategoryRepository repository;

    @Mock
    private ProjectCategoryRepositoryJpa repositoryJpa;

    @InjectMocks
    private ProjectCategoryServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreateProjectCategory() throws BaseException {
        ProjectCategoryVO projectCategoryVOToSave = ProjectFake.toFakeProjectCategoryVO();
        projectCategoryVOToSave.setId(null);

        ProjectCategory projectCategorySaveReturn = ProjectFake.toFakeProjectCategory();

        Mockito.doReturn(projectCategorySaveReturn).when(repository).save(Mockito.any(ProjectCategory.class));

        ProjectCategoryVO projectCategorySaved = service.create(projectCategoryVOToSave);

        Assertions.assertThat(projectCategorySaved).isNotNull();

        Mockito.verify(repository).save(Mockito.any(ProjectCategory.class));
    }

    @Test
    void whenUpdateProjectCategory() {
        ProjectCategoryVO projectCategoryVOToSave = ProjectFake.toFakeProjectCategoryVO();

        ProjectCategory projectCategoryFindReturn = ProjectFake.toFakeProjectCategory();

        ProjectCategory projectCategorySaveReturn = ProjectFake.toFakeProjectCategory();

        Mockito.doReturn(projectCategorySaveReturn).when(repository).update(Mockito.any(ProjectCategory.class));
        Mockito.doReturn(Optional.of(projectCategoryFindReturn)).when(repository).findById(Mockito.anyLong());

        ProjectCategoryVO projectCategorySaved = service.update(projectCategoryVOToSave);

        Assertions.assertThat(projectCategorySaved).isNotNull();

        Mockito.verify(repository).update(Mockito.any(ProjectCategory.class));
        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void whenUpdateProject_thenObjectNotFoundException() {
        ProjectCategoryVO projectCategoryVOToSave = ProjectFake.toFakeProjectCategoryVO();

        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.update(projectCategoryVOToSave))
                .isInstanceOf(ProjectCategoryNotFoundException.class);
    }

    @Test
    void whenFindCategoryById() {
        ProjectCategory projectCategoryFindReturn = ProjectFake.toFakeProjectCategory();

        Mockito.doReturn(Optional.of(projectCategoryFindReturn)).when(repository)
                .findById(Mockito.anyLong());

        ProjectCategoryVO projectCategoryReturn = service.findById(1L);

        Assertions.assertThat(projectCategoryReturn).isNotNull();

        Mockito.verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void whenFindCategoryById_thenObjectNotFoundException() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(ProjectCategoryNotFoundException.class);
    }

    @Test
    void whenFindCategoryByFilter() {
        Page<ProjectCategory> listCategoryToReturn = new PageImpl<>(Arrays.asList(ProjectFake.toFakeProjectCategory()));

        Mockito.doReturn(listCategoryToReturn).when(repositoryJpa).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        Page<ProjectCategoryVO> listCategoryReturn = service.findByFilter(new ProjectCategoryFilter());

        Assertions.assertThat(listCategoryReturn).isNotNull();
        Assertions.assertThat(listCategoryReturn).isNotEmpty();
        Assertions.assertThat(listCategoryReturn.getTotalElements()).isEqualTo(listCategoryToReturn.getTotalElements());

        Mockito.verify(repositoryJpa).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

}
