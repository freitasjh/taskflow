package br.com.systec.taskflow.kanban.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.kanban.api.model.Kanban;
import br.com.systec.taskflow.kanban.api.specification.KanbanBoardSpecification;
import org.springframework.data.jpa.domain.Specification;

public class KanbanFilter extends FilterPageParam<Kanban> {

    public KanbanFilter() {
    }

    public KanbanFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<Kanban> getSpecification() {
        return new KanbanBoardSpecification().createFilter(this);
    }
}
