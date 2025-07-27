package br.com.systec.taskflow.workflow.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.workflow.api.exceptions.StatusException;
import br.com.systec.taskflow.workflow.api.filter.StatusFilter;
import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.impl.repository.StatusRepository;
import br.com.systec.taskflow.workflow.impl.repository.StatusRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusServiceImplTest {

    @Mock
    private StatusRepository repository;

    @Mock
    private StatusRepositoryJpa repositoryJpa;

    @InjectMocks
    private I18nTranslate i18nTranslate;

    @Mock
    private ResourceBundleMessageSource messageSource;

    @InjectMocks
    private StatusServiceImpl statusService;

    private StatusVO statusVO;
    private Status status;

    @BeforeEach
    void setUp() {
        statusVO = new StatusVO();
        statusVO.setId(1L);
        statusVO.setName("In Progress");
        statusVO.setDescription("Task in progress");
        statusVO.setOrder(1);

        status = new Status();
        status.setId(1L);
        status.setName("In Progress");
        status.setDescription("Task in progress");
        status.setOrder(1);
    }

    @Test
    void create_Success_ReturnsStatusVO() throws BaseException {
        // Arrange
        when(repository.save(any(Status.class))).thenReturn(status);

        // Act
        StatusVO result = statusService.create(statusVO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("In Progress");
        verify(repository).save(any(Status.class));
    }

    @Test
    void create_RepositoryThrowsException_ThrowsStatusException() {
        // Arrange
        when(repository.save(any(Status.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThatThrownBy(() -> statusService.create(statusVO))
                .isInstanceOf(StatusException.class);
    }

    @Test
    void update_Success_ReturnsStatusVO() throws BaseException {
        // Arrange
        when(repository.update(any(Status.class))).thenReturn(status);

        // Act
        StatusVO result = statusService.update(statusVO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("In Progress");
        verify(repository).update(any(Status.class));
    }

    @Test
    void update_RepositoryThrowsException_ThrowsStatusException() {
        // Arrange
        when(repository.update(any(Status.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThatThrownBy(() -> statusService.update(statusVO))
                .isInstanceOf(StatusException.class);
    }

    @Test
    void delete_Success_DeletesStatus() throws BaseException {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(status));
        doNothing().when(repository).delete(any(Status.class));

        // Act
        statusService.delete(statusVO);

        // Assert
        verify(repository).findById(1L);
        verify(repository).delete(status);
    }

    @Test
    void delete_StatusNotFound_ThrowsStatusException() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> statusService.delete(statusVO))
                .isInstanceOf(StatusException.class);
        verify(repository).findById(1L);
        verify(repository, never()).delete(any(Status.class));
    }

    @Test
    void delete_RepositoryThrowsException_ThrowsStatusException() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(status));
        doThrow(new RuntimeException("Database error")).when(repository).delete(any(Status.class));

        // Act & Assert
        assertThatThrownBy(() -> statusService.delete(statusVO))
                .isInstanceOf(StatusException.class);
    }

    @Test
    void findAll_Success_ReturnsListOfStatusVO() throws BaseException {
        // Arrange
        Iterable<Status> statusList = List.of(status);
        when(repository.findAll()).thenReturn(statusList);

        // Act
        List<StatusVO> result = statusService.findAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("In Progress");
        verify(repository).findAll();
    }

    @Test
    void findAll_RepositoryThrowsException_ThrowsStatusException() {
        // Arrange
        when(repository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThatThrownBy(() -> statusService.findAll())
                .isInstanceOf(StatusException.class);
    }

    @Test
    void whenFindByFilter() {
        Page<Status> pageStatusToReturn = new PageImpl<>(List.of(status));

        when(repositoryJpa.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(pageStatusToReturn);

        Page<StatusVO> pageReturn = statusService.findByFilter(new StatusFilter());

        assertThat(pageReturn).isNotEmpty();
        assertThat(pageReturn.getTotalElements()).isEqualTo(pageStatusToReturn.getTotalElements());

        verify(repositoryJpa).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    void whenFindByFilter_thenReturnException() {
        when(repositoryJpa.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenThrow(new RuntimeException("Error"));

        assertThatThrownBy(() -> statusService.findByFilter(new StatusFilter())).isInstanceOf(StatusException.class);
    }

}
