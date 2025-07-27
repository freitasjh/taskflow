package br.com.systec.taskflow.employee.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.employee.api.specification.EmployeeSpecification;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeFilter extends FilterPageParam<Employee> {

    public EmployeeFilter() {
    }

    public EmployeeFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<Employee> getSpecification() {
        return new EmployeeSpecification().createFilter(this);
    }
}
