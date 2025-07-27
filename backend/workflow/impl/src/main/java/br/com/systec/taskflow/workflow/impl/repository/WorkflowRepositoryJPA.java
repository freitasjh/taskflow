package br.com.systec.taskflow.workflow.impl.repository;

import br.com.systec.taskflow.workflow.api.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepositoryJPA extends JpaRepository<Workflow, Long>, JpaSpecificationExecutor<Workflow> {
}
