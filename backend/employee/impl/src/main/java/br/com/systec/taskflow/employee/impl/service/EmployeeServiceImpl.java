package br.com.systec.taskflow.employee.impl.service;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.api.model.UserType;
import br.com.systec.taskflow.api.service.UserService;
import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.employee.api.converter.EmployeeConverter;
import br.com.systec.taskflow.employee.api.exceptions.EmployeeFindException;
import br.com.systec.taskflow.employee.api.exceptions.EmployeeNotFoundException;
import br.com.systec.taskflow.employee.api.exceptions.EmployeeSavedException;
import br.com.systec.taskflow.employee.api.filter.EmployeeFilter;
import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.employee.api.service.EmployeeService;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.employee.impl.repository.EmployeeRepository;
import br.com.systec.taskflow.employee.impl.repository.EmployeeRepositoryJpa;
import br.com.systec.taskflow.i18n.I18nTranslate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository repository;
    private final EmployeeRepositoryJpa repositoryJpa;
    private final UserService userService;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeRepositoryJpa repositoryJpa, UserService userService) {
        this.repository = repository;
        this.userService = userService;
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EmployeeVO save(EmployeeVO employeeVO) throws EmployeeSavedException {
        try {
            Employee employeeToSave = EmployeeConverter.toEntity(employeeVO);

            User userSaved = saveUser(employeeVO);
            employeeToSave.setUserId(userSaved.getId());

            Employee employeeSaved = repository.save(employeeToSave);

            return EmployeeConverter.toVO(employeeSaved, userSaved.getUsername());
        } catch (BaseException e) {
            log.error("Error saving employee: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error saving employee: ", e);
            throw new EmployeeSavedException(e);
        }
    }

    private User saveUser(EmployeeVO employeeVO) throws BaseException {
        User user = new User();
        user.setUsername(employeeVO.getUsername());
        user.setEmail(employeeVO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(employeeVO.getPassword()));
        user.setUserType(UserType.EMPLOYEE);

        return userService.save(user, employeeVO.getDepartament().name());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EmployeeVO update(EmployeeVO employeeVO) throws EmployeeSavedException {
        try {
            Employee existingEmployee = repository.findById(employeeVO.getId())
                    .orElseThrow(EmployeeNotFoundException::new);

            Employee employeeToSave = EmployeeConverter.toEntity(employeeVO, existingEmployee);

            User user = userService.findById(existingEmployee.getUserId());

            user.setEmail(employeeToSave.getEmail());
            if (employeeVO.getPassword() != null && !employeeVO.getPassword().isEmpty()) {
                user.setPassword(new BCryptPasswordEncoder().encode(employeeVO.getPassword()));
            } else {
                user.setPassword(user.getPassword());
            }

            userService.update(user);

            Employee employeeUpdated = repository.update(employeeToSave);

            return EmployeeConverter.toVO(employeeUpdated, user.getUsername());
        } catch (BaseException e) {
            log.error("Error updating employee: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error updating employee: ", e);
            throw new EmployeeSavedException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public EmployeeVO findById(Long id) throws BaseException {
        try {
            Employee employeeReturned = repository.findById(id).orElseThrow(EmployeeNotFoundException::new);
            User user = userService.findById(employeeReturned.getUserId());

            return EmployeeConverter.toVO(employeeReturned, user.getUsername());
        } catch (BaseException e) {
            log.error("Error finding employee by ID: {}", id, e);
            throw e;
        } catch (Exception e) {
            log.error("Error finding employee by ID: {}", id, e);
            throw new EmployeeFindException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<EmployeeVO> findByFilter(EmployeeFilter filter) throws EmployeeFindException {
        try {
            Page<Employee> employeePage = repositoryJpa.findAll(filter.getSpecification(), filter.getPageable());

            return employeePage.map(EmployeeConverter::toVO);
        } catch (Exception e) {
            log.error("Error finding employees by filter: {}", filter, e);
            throw new EmployeeFindException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public EmployeeVO findByUserId(Long userId) throws EmployeeFindException {
        Employee employee = repository.findByUserId(userId).orElseThrow(() -> new EmployeeFindException());

        return EmployeeConverter.toVO(employee);
    }
}
