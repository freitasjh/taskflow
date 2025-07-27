package br.com.systec.taskflow.user.impl.repository;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.commons.repository.AbstractRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends AbstractRepository<User, Long> {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select obj from User obj where obj.username = :username", User.class);
        query.setParameter("username", username);

        List<User> listOfResult = query.getResultList();

        if(listOfResult.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfResult.get(0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select obj from User obj where obj.email = :email", User.class);
        query.setParameter("username", email);

        List<User> listOfResult = query.getResultList();

        if(listOfResult.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfResult.get(0));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        TypedQuery<User> query = entityManager.createQuery("select obj from User obj where obj.username = :username or obj.email = :email", User.class);
        query.setParameter("username", username);
        query.setParameter("email", email);
        query.setMaxResults(1);

        List<User> listOfUser = query.getResultList();

        if (listOfUser.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(listOfUser.get(0));
    }
}
