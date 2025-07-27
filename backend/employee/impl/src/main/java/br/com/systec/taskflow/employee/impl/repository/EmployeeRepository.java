package br.com.systec.taskflow.employee.impl.repository;

import br.com.systec.taskflow.commons.repository.AbstractRepository;
import br.com.systec.taskflow.employee.api.model.Employee;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class EmployeeRepository extends AbstractRepository<Employee, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Employee> findByUserId(Long userId) {
        String sql = "SELECT obj FROM Employee obj where obj.userId = :userId";

        TypedQuery<Employee> query = entityManager.createQuery(sql, Employee.class);
        query.setParameter("userId", userId);

        if(query.getResultList().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(query.getResultList().get(0));
    }
}
