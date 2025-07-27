package br.com.systec.taskflow.project.api.specification;

import br.com.systec.taskflow.project.api.filter.ProjectCategoryFilter;
import br.com.systec.taskflow.project.api.model.ProjectCategory;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectCategorySpecification {

    private ProjectCategorySpecification() {}

    public static Specification<ProjectCategory> createFilter(ProjectCategoryFilter projectCategoryFilter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(projectCategoryFilter.getKeyword() != null && !projectCategoryFilter.getKeyword().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + projectCategoryFilter.getKeyword().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
