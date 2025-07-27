package br.com.systec.taskflow.team.api.filter;

import br.com.systec.taskflow.commons.filter.FilterPageParam;
import br.com.systec.taskflow.team.api.model.Team;
import br.com.systec.taskflow.team.api.specification.TeamSpecification;
import org.springframework.data.jpa.domain.Specification;

public class TeamFilter extends FilterPageParam<Team> {

    public TeamFilter() {
    }

    public TeamFilter(int limit, int page, String keyword) {
        super(limit, page, keyword);
    }

    @Override
    public Specification<Team> getSpecification() {
        return new TeamSpecification().createFilter(this);
    }
}
