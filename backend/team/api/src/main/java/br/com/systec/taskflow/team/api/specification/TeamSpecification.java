package br.com.systec.taskflow.team.api.specification;

import br.com.systec.taskflow.commons.specification.TaskFlowSpecification;
import br.com.systec.taskflow.team.api.filter.TeamFilter;
import br.com.systec.taskflow.team.api.model.Team;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TeamSpecification extends TaskFlowSpecification<Team, TeamFilter> {


    @Override
    public Specification<Team> createFilter(TeamFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
