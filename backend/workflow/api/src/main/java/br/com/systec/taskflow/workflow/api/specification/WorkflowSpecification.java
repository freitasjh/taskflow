package br.com.systec.taskflow.workflow.api.specification;

import br.com.systec.taskflow.commons.specification.TaskFlowSpecification;
import br.com.systec.taskflow.workflow.api.filter.WorkflowFilter;
import br.com.systec.taskflow.workflow.api.model.Workflow;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WorkflowSpecification extends TaskFlowSpecification<Workflow, WorkflowFilter> {

    @Override
    public Specification<Workflow> createFilter(WorkflowFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
