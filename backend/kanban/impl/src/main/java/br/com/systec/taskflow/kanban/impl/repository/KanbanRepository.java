package br.com.systec.taskflow.kanban.impl.repository;

import br.com.systec.taskflow.commons.repository.AbstractRepository;
import br.com.systec.taskflow.kanban.api.model.Kanban;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class KanbanRepository extends AbstractRepository<Kanban, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<Kanban> findByProjectId(Long projectId) {
        String sql = "SELECT obj from Kanban obj where obj.projectId = :projectId";

        TypedQuery<Kanban> query = entityManager.createQuery(sql, Kanban.class);
        query.setParameter("projectId", projectId);

        List<Kanban> results = query.getResultList();

        if(results.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(results.get(0));
    }
}
