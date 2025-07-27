package br.com.systec.taskflow.task.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.task.api.model.Task;
import br.com.systec.taskflow.task.api.specification.TaskSpecification;
import org.springframework.data.jpa.domain.Specification;

public class TaskFilter extends FilterPageParam<Task> {

    public TaskFilter() {
    }

    public TaskFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<Task> getSpecification() {
        return new TaskSpecification().createFilter(this);
    }
}
