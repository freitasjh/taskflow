package br.com.systec.taskflow.project.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.project.api.converter.ProjectStatusConverter;
import br.com.systec.taskflow.project.api.filter.ProjectStatusFilter;
import br.com.systec.taskflow.project.api.model.ProjectStatus;
import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import br.com.systec.taskflow.project.impl.fake.ProjectFake;
import br.com.systec.taskflow.project.impl.repository.ProjectStatusRepository;
import br.com.systec.taskflow.project.impl.repository.jpa.ProjectStatusRepositoryJpa;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class ProjectStatusServiceImplTest {

    @Mock
    private ProjectStatusRepository repository;

    @Mock
    private ProjectStatusRepositoryJpa repositoryJpa;

    @InjectMocks
    private ProjectStatusServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreate_thenReturnProjectStatusVO() throws BaseException {
        // Arrange
        ProjectStatusVO projectStatusVO = new ProjectStatusVO();
        projectStatusVO.setName("Test Status");

        ProjectStatus savedProjectStatus = new ProjectStatus();
        savedProjectStatus.setId(1L);
        savedProjectStatus.setName("Test Status");

        Mockito.when(repository.save(any(ProjectStatus.class))).thenReturn(savedProjectStatus);

        // Act
        ProjectStatusVO result = service.create(projectStatusVO);

        // Assert
        assertEquals(savedProjectStatus.getId(), result.getId());
        assertEquals(savedProjectStatus.getName(), result.getName());
        Mockito.verify(repository).save(any(ProjectStatus.class));
    }

    @Test
    void whenUpdate_thenReturnUpdatedProjectStatusVO() throws BaseException {
        // Arrange
        ProjectStatusVO projectStatusVO = ProjectFake.toFakeProjectStatusVO();

        ProjectStatus existingProjectStatus = new ProjectStatus();
        existingProjectStatus.setId(1L);
        existingProjectStatus.setName("Old Status");

        ProjectStatus updatedProjectStatus = ProjectStatusConverter.toModel(projectStatusVO);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of(existingProjectStatus));
        Mockito.when(repository.update(any(ProjectStatus.class))).thenReturn(updatedProjectStatus);

        // Act
        ProjectStatusVO result = service.update(projectStatusVO);

        // Assert
        assertEquals(updatedProjectStatus.getId(), result.getId());
        assertEquals(updatedProjectStatus.getName(), result.getName());
        Mockito.verify(repository).findById(1L);
        Mockito.verify(repository).update(any(ProjectStatus.class));
    }

    @Test
    void whenFindById_thenReturnProjectStatusVO() throws BaseException {
        // Arrange
        Long id = 1L;
        ProjectStatus projectStatus = ProjectFake.toFakeProjectStatus();
        projectStatus.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.of(projectStatus));

        // Act
        ProjectStatusVO result = service.findById(id);

        // Assert
        assertEquals(projectStatus.getId(), result.getId());
        assertEquals(projectStatus.getName(), result.getName());
        Mockito.verify(repository).findById(id);
    }

    @Test
    void whenDelete_thenVerifyDeletion() throws BaseException {
        // Arrange
        Long id = 1L;
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(java.util.Optional.of(projectStatus));

        // Act
        service.delete(id);

        // Assert
        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).delete(projectStatus);
    }

    @Test
    void whenFindPaginatedProjectStatus() {
        Page<ProjectStatus> listOfStatusToReturn = new PageImpl<>(List.of(ProjectFake.toFakeProjectStatus()));

        Mockito.doReturn(listOfStatusToReturn).when(repositoryJpa).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

        ProjectStatusFilter filter = new ProjectStatusFilter();

        Page<ProjectStatusVO> listOfStatusReturn = service.findByFilter(filter);

        Assertions.assertThat(listOfStatusReturn).isNotEmpty();
        Assertions.assertThat(listOfStatusReturn.getTotalElements()).isEqualTo(listOfStatusToReturn.getTotalElements());

        Mockito.verify(repositoryJpa).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }
}