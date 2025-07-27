package br.com.systec.taskflow.workflow.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.workflow.api.model.Workflow;
import br.com.systec.taskflow.workflow.api.specification.WorkflowSpecification;
import org.springframework.data.jpa.domain.Specification;

public class WorkflowFilter extends FilterPageParam<Workflow> {

    public WorkflowFilter() {
    }

    public WorkflowFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<Workflow> getSpecification() {
        return new WorkflowSpecification().createFilter(this);
    }
}
