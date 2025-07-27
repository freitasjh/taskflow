package br.com.systec.taskflow.project.api.specification;

import br.com.systec.taskflow.project.api.filter.ProjectStatusFilter;
import br.com.systec.taskflow.project.api.model.ProjectStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectStatusSpecification {

    private ProjectStatusSpecification() {}

    public static Specification<ProjectStatus> createFilter(ProjectStatusFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getKeyword().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
