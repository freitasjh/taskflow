package br.com.systec.taskflow.project.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.project.api.model.Project;
import br.com.systec.taskflow.project.api.specification.ProjectSpecification;
import org.springframework.data.jpa.domain.Specification;

public class ProjectFilter extends FilterPageParam<Project> {

    public ProjectFilter() {
    }

    public ProjectFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<Project> getSpecification() {
        return new ProjectSpecification().createFilter(this);
    }
}
