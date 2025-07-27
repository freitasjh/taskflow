package br.com.systec.taskflow.kanban.api.specification;

import br.com.systec.taskflow.commons.specification.TaskFlowSpecification;
import br.com.systec.taskflow.kanban.api.filter.KanbanFilter;
import br.com.systec.taskflow.kanban.api.model.Kanban;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class KanbanBoardSpecification extends TaskFlowSpecification<Kanban, KanbanFilter> {

    @Override
    public Specification<Kanban> createFilter(KanbanFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
