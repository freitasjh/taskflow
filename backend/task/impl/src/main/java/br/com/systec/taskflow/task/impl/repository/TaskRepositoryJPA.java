package br.com.systec.taskflow.task.impl.repository;

import br.com.systec.taskflow.task.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepositoryJPA extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}
