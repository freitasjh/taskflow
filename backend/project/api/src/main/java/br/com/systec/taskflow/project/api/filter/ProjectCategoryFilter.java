package br.com.systec.taskflow.project.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.project.api.model.ProjectCategory;
import br.com.systec.taskflow.project.api.specification.ProjectCategorySpecification;
import org.springframework.data.jpa.domain.Specification;

public class ProjectCategoryFilter extends FilterPageParam<ProjectCategory> {

    public ProjectCategoryFilter() {
    }

    public ProjectCategoryFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<ProjectCategory> getSpecification() {
        return ProjectCategorySpecification.createFilter(this);
    }
}
