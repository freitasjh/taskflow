package br.com.systec.taskflow.kanban.impl.repository;

import br.com.systec.taskflow.kanban.api.model.Kanban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanRepositoryJPA extends JpaRepository<Kanban, Long>, JpaSpecificationExecutor<Kanban> {
}
