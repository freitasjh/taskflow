package br.com.systec.taskflow.project.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.project.api.model.ProjectStatus;
import br.com.systec.taskflow.project.api.specification.ProjectStatusSpecification;
import org.springframework.data.jpa.domain.Specification;

public class ProjectStatusFilter extends FilterPageParam<ProjectStatus> {

    public ProjectStatusFilter() {
    }

    public ProjectStatusFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<ProjectStatus> getSpecification() {
        return ProjectStatusSpecification.createFilter(this);
    }
}
