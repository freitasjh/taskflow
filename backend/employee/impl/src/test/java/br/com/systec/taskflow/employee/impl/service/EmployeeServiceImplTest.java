package br.com.systec.taskflow.employee.impl.service;

import br.com.systec.taskflow.api.exceptions.validation.UserEmailExistException;
import br.com.systec.taskflow.api.exceptions.validation.UserUsernameExistException;
import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.api.service.UserService;
import br.com.systec.taskflow.employee.api.exceptions.EmployeeNotFoundException;
import br.com.systec.taskflow.employee.api.exceptions.EmployeeSavedException;
import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.employee.impl.fake.EmployeeFake;
import br.com.systec.taskflow.employee.impl.repository.EmployeeRepository;
import br.com.systec.taskflow.i18n.I18nTranslate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Optional;


class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
    @Mock
    private UserService userService;
    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void whenTestSaveEmployee_thenReturnEmployeeVO() {
        User userSaved = EmployeeFake.toFakeUser();

        Employee employeeToReturn = EmployeeFake.toFake();

        EmployeeVO employeeToSave = EmployeeFake.toFakeVO();
        employeeToSave.setId(null);
        employeeToSave.setUserId(null);
        employeeToSave.setPassword("123456");

        Mockito.when(userService.save(Mockito.any(User.class), Mockito.anyString())).thenReturn(userSaved);

        Mockito.doReturn(employeeToReturn).when(repository).save(Mockito.any(Employee.class));

        EmployeeVO employeeSaved = employeeService.save(employeeToSave);
        Assertions.assertThat(employeeSaved).isNotNull();
        Assertions.assertThat(employeeSaved.getId()).isEqualTo(employeeToReturn.getId());
        Assertions.assertThat(employeeSaved.getName()).isEqualTo(employeeToReturn.getName());
        Assertions.assertThat(employeeSaved.getEmail()).isEqualTo(employeeToReturn.getEmail());
        Assertions.assertThat(employeeSaved.getUsername()).isEqualTo(userSaved.getUsername());
        Assertions.assertThat(employeeSaved.getDepartament()).isNotNull();
        Assertions.assertThat(employeeSaved.getDepartament()).isEqualTo(employeeToReturn.getDepartment());
        Assertions.assertThat(employeeSaved.getUserId()).isNotNull();

        Mockito.verify(userService).save(Mockito.any(User.class), Mockito.anyString());
        Mockito.verify(repository).save(Mockito.any(Employee.class));
    }

    @Test
    void whenSaveEmployeeUserUsernameAlreadyExists_thenThrowException() {
        User userSaved = EmployeeFake.toFakeUser();
        userSaved.setUsername("existingUsername");

        EmployeeVO employeeToSave = EmployeeFake.toFakeVO();
        employeeToSave.setUsername("existingUsername");
        employeeToSave.setPassword("123456");

        Mockito.doThrow(new UserUsernameExistException(employeeToSave.getUsername())).when(userService)
                .save(Mockito.any(User.class), Mockito.anyString());

        Assertions.assertThatThrownBy(() -> employeeService.save(employeeToSave))
                .isInstanceOf(UserUsernameExistException.class);
    }

    @Test
    void whenSaveEmployeeUserEmailAlreadyExists_thenThrowException() {
        User userSaved = EmployeeFake.toFakeUser();
        userSaved.setEmail("existingEmail@email.com");

        EmployeeVO employeeToSave = EmployeeFake.toFakeVO();
        employeeToSave.setUsername("existingEmail@email.com");
        employeeToSave.setPassword("123456");

        Mockito.doThrow(new UserEmailExistException(employeeToSave.getEmail())).when(userService)
                .save(Mockito.any(User.class), Mockito.anyString());

        Assertions.assertThatThrownBy(() -> employeeService.save(employeeToSave))
                .isInstanceOf(UserEmailExistException.class);
    }

    @Test
    void whenSaveEmployee_thenThrowException() {
        EmployeeVO employeeToSave = EmployeeFake.toFakeVO();
        employeeToSave.setId(null);
        employeeToSave.setUserId(null);
        employeeToSave.setPassword("123456");

        Assertions.assertThatThrownBy(() -> employeeService.save(employeeToSave))
                .isInstanceOf(EmployeeSavedException.class);
    }

    @Test
    void whenUpdateEmployee_thenReturnEmployeeVO() {
        User userSaved = EmployeeFake.toFakeUser();

        Employee employeeToReturn = EmployeeFake.toFake();
        employeeToReturn.setUserId(userSaved.getId());

        EmployeeVO employeeToUpdate = EmployeeFake.toFakeVO();
        employeeToUpdate.setPassword("123456");


        Mockito.doReturn(Optional.of(employeeToReturn)).when(repository).findById(Mockito.anyLong());
        Mockito.doReturn(userSaved).when(userService).findById(Mockito.anyLong());
        Mockito.doReturn(userSaved).when(userService).update(Mockito.any(User.class));
        Mockito.doReturn(employeeToReturn).when(repository).update(Mockito.any(Employee.class));

        EmployeeVO employeeUpdated = employeeService.update(employeeToUpdate);
        Assertions.assertThat(employeeUpdated).isNotNull();

        Mockito.verify(repository).findById(Mockito.anyLong());
        Mockito.verify(userService).findById(Mockito.anyLong());
        Mockito.verify(userService).update(Mockito.any(User.class));
        Mockito.verify(repository).update(Mockito.any(Employee.class));
    }

    @Test
    void whenUpdateEmployeeAndNotFound_thenThrowException() {
        EmployeeVO employeeToUpdate = EmployeeFake.toFakeVO();

        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.any());

        Assertions.assertThatThrownBy(() -> employeeService.update(employeeToUpdate)).isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void whenUpdateEmployee_thenThrowException() {
        EmployeeVO employeeToSave = EmployeeFake.toFakeVO();

        Employee employeeToReturn = EmployeeFake.toFake();
        User userToReturn = EmployeeFake.toFakeUser();

        Mockito.doReturn(Optional.of(employeeToReturn)).when(repository).findById(Mockito.anyLong());
        Mockito.doReturn(userToReturn).when(userService).findById(Mockito.anyLong());

        Mockito.doThrow(RuntimeException.class).when(repository).update(Mockito.any(Employee.class));

        Assertions.assertThatThrownBy(() -> employeeService.update(employeeToSave))
                .isInstanceOf(EmployeeSavedException.class);
    }

    @Test
    void whenFindEmployeeById_thenReturnEmployeeVO() {
        Employee employeeToReturn = EmployeeFake.toFake();
        employeeToReturn.setUserId(1L);

        Mockito.doReturn(Optional.of(employeeToReturn)).when(repository).findById(Mockito.anyLong());
        Mockito.doReturn(EmployeeFake.toFakeUser()).when(userService).findById(Mockito.anyLong());

        EmployeeVO employeeFound = employeeService.findById(1L);
        Assertions.assertThat(employeeFound).isNotNull();
        Assertions.assertThat(employeeFound.getId()).isEqualTo(employeeToReturn.getId());
        Assertions.assertThat(employeeFound.getName()).isEqualTo(employeeToReturn.getName());
        Assertions.assertThat(employeeFound.getEmail()).isEqualTo(employeeToReturn.getEmail());
        Assertions.assertThat(employeeFound.getUsername()).isEqualTo(EmployeeFake.toFakeUser().getUsername());
    }

    @Test
    void wheFindEpmloyeeByIdAndNotFound_thenThrowException() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(Mockito.anyLong());

        Assertions.assertThatThrownBy(() -> employeeService.update(EmployeeFake.toFakeVO()))
                .isInstanceOf(EmployeeNotFoundException.class);
    }
}
