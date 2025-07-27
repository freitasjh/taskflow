package br.com.systec.taskflow.project.impl.repository;

import br.com.systec.taskflow.commons.repository.AbstractRepository;
import br.com.systec.taskflow.project.api.model.ProjectStatus;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectStatusRepository extends AbstractRepository<ProjectStatus, Long> {
}
