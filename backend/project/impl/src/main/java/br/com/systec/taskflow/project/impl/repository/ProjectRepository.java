package br.com.systec.taskflow.project.impl.repository;

import br.com.systec.taskflow.commons.repository.AbstractRepository;
import br.com.systec.taskflow.project.api.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository extends AbstractRepository<Project, Long> {
}
