package br.com.systec.taskflow.workflow.impl.repository;

import br.com.systec.taskflow.commons.repository.AbstractRepository;
import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.model.Workflow;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkflowRepository extends AbstractRepository<Workflow, Long> {

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Status> findStatusById(Long statusId) {
        String sql = "SELECT s FROM Status s WHERE s.id = :statusId";

        TypedQuery<Status> query = entityManager.createQuery(sql, Status.class);
        query.setParameter("statusId", statusId);

        List<Status> statusResultList = query.getResultList();

        if (statusResultList.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(statusResultList.get(0));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Status> findStatusByInitialAndWorkflowId(Long workflowId) {
        String sql = "SELECT s FROM Status s WHERE s.initial = :initial AND s.workflow.id = :workflowId";

        TypedQuery<Status> query = entityManager.createQuery(sql, Status.class);
        query.setParameter("initial", true);
        query.setParameter("workflowId", workflowId);

        List<Status> statusResultList = query.getResultList();

        if (statusResultList.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(statusResultList.get(0));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Workflow> findByProjectId(Long projectId) {
        String sql = "SELECT w FROM Workflow w WHERE w.projectId = :projectId";
        TypedQuery<Workflow> query = entityManager.createQuery(sql, Workflow.class);
        query.setParameter("projectId", projectId);

        List<Workflow> workflowResultList = query.getResultList();
        if (workflowResultList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(workflowResultList.get(0));
    }
}
