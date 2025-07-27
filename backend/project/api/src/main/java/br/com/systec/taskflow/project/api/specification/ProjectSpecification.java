package br.com.systec.taskflow.project.api.specification;

import br.com.systec.taskflow.commons.specification.TaskFlowSpecification;
import br.com.systec.taskflow.project.api.filter.ProjectFilter;
import br.com.systec.taskflow.project.api.model.Project;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ProjectSpecification extends TaskFlowSpecification<Project, ProjectFilter> {


    @Override
    public Specification<Project> createFilter(ProjectFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
