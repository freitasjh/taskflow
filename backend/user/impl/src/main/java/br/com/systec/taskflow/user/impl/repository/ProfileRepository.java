package br.com.systec.taskflow.user.impl.repository;

import br.com.systec.taskflow.api.model.Profile;
import br.com.systec.taskflow.commons.repository.AbstractRepository;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepository extends AbstractRepository<Profile, Long> {

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Profile> findByName(String name) {
        TypedQuery<Profile> query = entityManager.createQuery(
                "SELECT p FROM Profile p WHERE p.name = :name", Profile.class);
        query.setParameter("name", name);

        List<Profile> profiles = query.getResultList();

        if(profiles.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(profiles.get(0));
    }
}
