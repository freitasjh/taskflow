package br.com.systec.taskflow.task.impl.repository;

import br.com.systec.taskflow.commons.repository.AbstractRepository;
import br.com.systec.taskflow.task.api.model.Task;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TaskRepository extends AbstractRepository<Task, Long> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int getNextCode() {
        Long nextVal = (Long) entityManager
                .createNativeQuery("SELECT nextval('task_cod_seq')")
                .getSingleResult();

        return nextVal.intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addAccountable(Long accountableId, Long taskId) {
        String sql = "UPDATE Task set accountable = :accountableId where id = :taskId";

        Query query = entityManager.createQuery(sql);
        query.setParameter("accountableId", accountableId);
        query.setParameter("taskId", taskId);

        query.executeUpdate();
    }
}
