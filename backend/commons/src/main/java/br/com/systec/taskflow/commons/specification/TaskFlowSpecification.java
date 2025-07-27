package br.com.systec.taskflow.commons.specification;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import org.springframework.data.jpa.domain.Specification;

public abstract class TaskFlowSpecification<T, F extends FilterPageParam<T>> {

    /**
     * Creates a specification based on the provided filter.
     *
     * @param filter the filter containing criteria for the specification
     * @return a Specification object that can be used to query the database
     */
    public abstract Specification<T> createFilter(F filter);
}
