package br.com.systec.taskflow.project.impl.repository.jpa;

import br.com.systec.taskflow.project.api.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatusRepositoryJpa extends JpaRepository<ProjectStatus, Long>, JpaSpecificationExecutor<ProjectStatus> {
}
