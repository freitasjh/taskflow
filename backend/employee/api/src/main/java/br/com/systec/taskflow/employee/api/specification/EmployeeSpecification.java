package br.com.systec.taskflow.employee.api.specification;

import br.com.systec.taskflow.commons.specification.TaskFlowSpecification;
import br.com.systec.taskflow.employee.api.filter.EmployeeFilter;
import br.com.systec.taskflow.employee.api.model.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification extends TaskFlowSpecification<Employee, EmployeeFilter> {

    @Override
    public Specification<Employee> createFilter(EmployeeFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
