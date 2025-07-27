package br.com.systec.taskflow.security.impl.service;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.employee.api.service.EmployeeService;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final EmployeeService employeeService;

    public SecurityServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public EmployeeVO getCurrentEmployee() {
        log.debug("@@@ Retorna os dados do Funcionadio logado @@@");
        return findEmployeeBYUserId();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Long getCurrentUserId() {
        log.debug("@@@ Retornando o id do usuario logado @@@");
       return currentUserId();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Long getCurrentEmployeeId() {
        log.debug("@@@ Retornando o id do funcionario logado @@@");
        return findEmployeeBYUserId().getId();
    }

    private Long currentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new SecurityException("Nenhum usuario logado");
        }

        User loggedUser = (User) authentication.getPrincipal();

        return loggedUser.getId();
    }

    private EmployeeVO findEmployeeBYUserId() {
        log.debug("@@@ Pesquisando o Funcionario pelo id do usuario logado @@@");
        return employeeService.findByUserId(currentUserId());
    }
}
