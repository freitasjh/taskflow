package br.com.systec.taskflow.team.impl.repository.jpa;

import br.com.systec.taskflow.team.api.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepositoryJpa extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {
}
