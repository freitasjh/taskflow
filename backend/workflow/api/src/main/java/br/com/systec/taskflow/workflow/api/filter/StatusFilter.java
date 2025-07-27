package br.com.systec.taskflow.workflow.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.specification.StatusSpecification;
import org.springframework.data.jpa.domain.Specification;

public class StatusFilter extends FilterPageParam<Status> {

    @Override
    public Specification<Status> getSpecification() {
        return new StatusSpecification().createFilter(this);
    }
}
