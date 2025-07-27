package br.com.systec.taskflow.workflow.impl.repository;

import br.com.systec.taskflow.commons.repository.AbstractRepository;
import br.com.systec.taskflow.workflow.api.model.Status;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StatusRepository extends AbstractRepository<Status, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Status> findAllActive() {
        String sql = "SELECT obj FROM Status obj where obj.active = :active";

        TypedQuery<Status> query = entityManager.createQuery(sql, Status.class);
        query.setParameter("active", true);

        return query.getResultList();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Status> findByWorkflowId(Long workflowId) {
        String sql = "SELECT obj Status obj where obj.workflowId = :workflowId order by obj.order asc";

        TypedQuery<Status> query = entityManager.createQuery(sql, Status.class);
        query.setParameter("workflowId", workflowId);

        return query.getResultList();
    }
}
