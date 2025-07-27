package br.com.systec.taskflow.project.impl.repository.jpa;

import br.com.systec.taskflow.project.api.model.ProjectCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCategoryRepositoryJpa extends JpaRepository<ProjectCategory, Long>, JpaSpecificationExecutor<ProjectCategory> {
}
