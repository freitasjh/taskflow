package br.com.systec.taskflow.task.impl.service;

import br.com.systec.taskflow.employee.api.service.EmployeeService;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.project.api.service.ProjectService;
import br.com.systec.taskflow.project.api.vo.ProjectVO;
import br.com.systec.taskflow.security.service.SecurityService;
import br.com.systec.taskflow.task.api.exceptions.TaskException;
import br.com.systec.taskflow.task.api.filter.TaskFilter;
import br.com.systec.taskflow.task.api.model.Task;
import br.com.systec.taskflow.task.api.vo.TaskFilterResponseVO;
import br.com.systec.taskflow.task.api.vo.TaskVO;
import br.com.systec.taskflow.task.fake.TaskFake;
import br.com.systec.taskflow.task.impl.repository.TaskRepository;
import br.com.systec.taskflow.task.impl.repository.TaskRepositoryJPA;
import br.com.systec.taskflow.workflow.api.service.WorkflowService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;
    @Mock
    private TaskRepositoryJPA repositoryJPA;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private SecurityService securityService;
    @Mock
    private ProjectService projectService;
    @Mock
    private WorkflowService workflowService;
    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private TaskServiceImpl service;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @Test
    void whenTaskCreate_thenReturnTaskVOSuccess() {
        TaskVO taskToSave = TaskFake.fakeVO();
        taskToSave.setCode(null);
        taskToSave.setId(null);

        Task taskToReturn = TaskFake.fake();
        taskToReturn.setCode("FTG-1");

        ProjectVO projectVO = new ProjectVO();
        projectVO.setPrefix("FTG");

        doReturn(taskToReturn).when(repository).save(any(Task.class));
        doReturn(1).when(repository).getNextCode();
        doReturn(1L).when(securityService).getCurrentEmployeeId();
        doReturn(projectVO).when(projectService).findById(anyLong());

        TaskVO taskSaved = service.create(taskToSave);

        assertNotNull(taskSaved);
        assertNotNull(taskSaved.getId());
        assertEquals(taskToReturn.getId(), taskSaved.getId());
        assertNotNull(taskSaved.getCode());

        verify(repository).save(any(Task.class));
        verify(repository).getNextCode();
        verify(securityService).getCurrentEmployeeId();
        verify(projectService).findById(anyLong());
    }

    @Test
    void whenTaskCreate_thenReturnTaskException() {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setPrefix("FLOW");

        doReturn(projectVO).when(projectService).findById(anyLong());
        doReturn(2L).when(securityService).getCurrentEmployeeId();
        doThrow(new RuntimeException("Erro banco de dados")).when(repository).save(any(Task.class));

        assertThatThrownBy(() -> service.create(TaskFake.fakeVO())).isInstanceOf(TaskException.class);

        verify(securityService).getCurrentEmployeeId();
        verify(projectService).findById(anyLong());
    }

    @Test
    void whenTaskUpdate_thenReturnTaskVOSuccess() {
        TaskVO taskToUpdate = TaskFake.fakeVO();

        Task taskToFindReturn = TaskFake.fake();
        Task taskToReturnUpdate = TaskFake.fake();

        doReturn(Optional.of(taskToFindReturn)).when(repository).findById(anyLong());
        doReturn(taskToReturnUpdate).when(repository).update(any(Task.class));

        TaskVO taskUpdate = service.update(taskToUpdate);

        assertNotNull(taskUpdate);
        assertEquals(taskToUpdate.getId(), taskUpdate.getId());

        verify(repository).findById(anyLong());
        verify(repository).update(any(Task.class));
    }

    @Test
    void whenTaskUpdate_thenReturnDatabaseTaskException() {
        doReturn(Optional.of(TaskFake.fake())).when(repository).findById(anyLong());

        doThrow(new RuntimeException("Erro banco de dados")).when(repository).update(any(Task.class));

        assertThatThrownBy(() -> service.update(TaskFake.fakeVO())).isInstanceOf(TaskException.class);

        verify(repository).findById(anyLong());
        verify(repository).update(any(Task.class));
    }

    @Test
    void whenTaskUpdate_thenReturnFindByIdTaskException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.update(TaskFake.fakeVO())).isInstanceOf(TaskException.class);
        verify(repository).findById(anyLong());
        verify(repository, times(0)).update(any(Task.class));
    }

    @Test
    void whenFindById_thenReturnTaskVO() {
        Task taskToReturn = TaskFake.fake();

        doReturn(Optional.of(taskToReturn)).when(repository).findById(anyLong());

        TaskVO taskReturn = service.findById(1L);

        assertNotNull(taskReturn);
        assertNotNull(taskReturn.getId());

        verify(repository).findById(anyLong());
    }

    @Test
    void whenFindById_thenDatabaseExceptionTaskException() {
        doThrow(new RuntimeException("Erro banco de dados")).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(TaskException.class);

        verify(repository).findById(anyLong());
    }

    @Test
    void whenFindById_thenReturnFindByIdTaskException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(TaskException.class);
        verify(repository).findById(anyLong());
    }

    @Test
    void whenFindByFilter_thenReturnPageResultTaskVO() {
        Page<Task> pageTaskToReturn = new PageImpl<>(List.of(TaskFake.fake()));

        EmployeeVO employee = new EmployeeVO();
        employee.setName("Usuario 1");

        StatusVO statusVO = new StatusVO();
        statusVO.setName("Backlog");

        when(repositoryJPA.findAll(any(Specification.class), any(Pageable.class))).thenReturn(pageTaskToReturn);
        when(employeeService.findById(anyLong())).thenReturn(employee);
        when(workflowService.findStatusById(anyLong())).thenReturn(statusVO);

        Page<TaskFilterResponseVO> pageTaskVOReturn = service.findByFilter(new TaskFilter());

        assertNotNull(pageTaskVOReturn);
        assertThat(pageTaskVOReturn.getContent()).isNotEmpty();
        assertThat(pageTaskVOReturn.getTotalElements()).isEqualTo(1);
        assertThat(pageTaskVOReturn.getContent().get(0).getCreateByName()).isEqualTo(employee.getName());

        verify(repositoryJPA).findAll(any(Specification.class), any(Pageable.class));
        verify(employeeService).findById(anyLong());
        verify(workflowService).findStatusById(anyLong());
    }

    @Test
    void whenFindByFilter_thenReturnDatabaseException() {
        doThrow(new RuntimeException("Erro banco de dados")).when(repositoryJPA).findAll(any(Specification.class), any(Pageable.class));

        assertThrows(TaskException.class, () -> service.findByFilter(new TaskFilter()));
    }
}
