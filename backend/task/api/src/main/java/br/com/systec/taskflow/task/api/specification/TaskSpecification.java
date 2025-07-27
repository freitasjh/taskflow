package br.com.systec.taskflow.task.api.specification;

import br.com.systec.taskflow.commons.specification.TaskFlowSpecification;
import br.com.systec.taskflow.task.api.filter.TaskFilter;
import br.com.systec.taskflow.task.api.model.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification extends TaskFlowSpecification<Task, TaskFilter> {

    @Override
    public Specification<Task> createFilter(TaskFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
