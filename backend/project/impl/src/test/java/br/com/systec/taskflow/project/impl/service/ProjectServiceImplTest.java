package br.com.systec.taskflow.project.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.project.api.converter.ProjectConverter;
import br.com.systec.taskflow.project.api.filter.ProjectFilter;
import br.com.systec.taskflow.project.api.model.Project;
import br.com.systec.taskflow.project.api.vo.ProjectVO;
import br.com.systec.taskflow.project.impl.repository.ProjectRepository;
import br.com.systec.taskflow.project.impl.repository.jpa.ProjectRepositoryJpa;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository repository;
    @Mock
    private ProjectRepositoryJpa repositoryJpa;
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ProjectServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreateProject_thenReturnProjectVO() throws BaseException {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setName("New Project");

        Project project = ProjectConverter.toEntity(projectVO);

        Project savedProject = new Project();
        savedProject.setId(1L);
        savedProject.setName("New Project");

        Mockito.when(repository.save(Mockito.any(Project.class))).thenReturn(savedProject);

        ProjectVO result = service.create(projectVO);

        assertEquals(savedProject.getId(), result.getId());
        assertEquals(savedProject.getName(), result.getName());

        Mockito.verify(repository).save(Mockito.any(Project.class));
    }

    @Test
    void whenCreateProjectThrowsException_thenThrowBaseException() {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setName("Invalid Project");

        Mockito.when(repository.save(Mockito.any(Project.class))).thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() -> service.create(projectVO))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).save(Mockito.any(Project.class));
    }

    @Test
    void whenUpdateProject_thenReturnUpdatedProjectVO() throws BaseException {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(1L);
        projectVO.setName("Updated Project");

        Project project = ProjectConverter.toEntity(projectVO);
        Project updatedProject = new Project();
        updatedProject.setId(1L);
        updatedProject.setName("Updated Project");

        Mockito.when(repository.update(Mockito.any(Project.class))).thenReturn(updatedProject);

        ProjectVO result = service.update(projectVO);

        assertEquals(updatedProject.getId(), result.getId());
        assertEquals(updatedProject.getName(), result.getName());
        Mockito.verify(repository).update(Mockito.any(Project.class));
    }

    @Test
    void whenUpdateProjectThrowsException_thenThrowBaseException() {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(1L);
        projectVO.setName("Invalid Update");

        Mockito.when(repository.update(Mockito.any(Project.class))).thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() -> service.update(projectVO))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repository).update(Mockito.any(Project.class));
    }

    @Test
    void whenFindByIdWithValidId_thenReturnProjectVO() throws BaseException {
        Long id = 1L;
        Project project = new Project();
        project.setId(id);
        project.setName("Existing Project");

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.of(project));

        ProjectVO result = service.findById(id);

        assertEquals(project.getId(), result.getId());
        assertEquals(project.getName(), result.getName());
        Mockito.verify(repository).findById(id);
    }

    @Test
    void whenFindByIdWithInvalidId_thenThrowObjectNotFoundException() {
        Long id = 999L;

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ObjectNotFoundException.class);

        Mockito.verify(repository).findById(id);
    }

    @Test
    void whenFindByFilterWithValidFilter_thenReturnPagedProjectVOs() throws BaseException {
        ProjectFilter filter = new ProjectFilter();
        Page<Project> projectPage = new PageImpl<>(List.of(new Project()));

        Mockito.when(repositoryJpa.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(projectPage);

        Page<ProjectVO> result = service.findByFilter(filter);

        Assertions.assertThat(result).isNotEmpty();
        Mockito.verify(repositoryJpa).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    void whenFindByFilterThrowsException_thenThrowBaseException() {
        ProjectFilter filter = new ProjectFilter();

        Mockito.when(repositoryJpa.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenThrow(RuntimeException.class);

        Assertions.assertThatThrownBy(() -> service.findByFilter(filter))
                .isInstanceOf(BaseException.class);

        Mockito.verify(repositoryJpa).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }
}
