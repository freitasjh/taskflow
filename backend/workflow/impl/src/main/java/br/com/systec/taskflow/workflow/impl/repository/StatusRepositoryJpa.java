package br.com.systec.taskflow.workflow.impl.repository;

import br.com.systec.taskflow.workflow.api.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepositoryJpa extends JpaRepository<Status, Long>, JpaSpecificationExecutor<Status> {
}
