package br.com.systec.taskflow.commons.repository;


import br.com.systec.taskflow.commons.query.PaginatedList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoRepositoryBean
public abstract class AbstractRepository<T, ID> implements CrudRepository<T, ID> {

    @SuppressWarnings("unchecked")
	private Class<T> entityClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @NonNull
    public <S extends T> S save(@NonNull S entity) {
        return savePrivate(entity);
    }

    private <S extends T> S savePrivate(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S update(S entity){
        return updatePrivate(entity);
    }

    private <S extends T> S updatePrivate(S entity){
        return entityManager.merge(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @NonNull
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(entity -> result.add(savePrivate(entity)));
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> Iterable<S> updateAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(entity -> result.add(updatePrivate(entity)));
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    private Optional<T> findByIdPrivate(ID id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean existsById(ID id) {
        return findByIdPrivate(id).isPresent();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<T> findAll() {
        return findAllPrivate();
    }

    private Iterable<T> findAllPrivate() {
        return entityManager.createQuery("SELECT o FROM "+entityClass.getSimpleName()+" o", entityClass).getResultList();
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @NonNull
    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> result = new ArrayList<>();

        ids.forEach(id -> {
            Optional<T> obj = findByIdPrivate(id);
            obj.ifPresent(result::add);
        });

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public T getOne(ID id){
        return entityManager.getReference(entityClass, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public long count() {
        return (long) (entityManager.createQuery("SELECT COUNT(o) FROM "+entityClass.getSimpleName()+" o")).getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(ID id) {
        Optional<T> entity = findByIdPrivate(id);
        T entityToDelete = entity.orElseThrow(() -> new IllegalArgumentException("Entity not found"));

        entityManager.remove(entityToDelete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> iterable) {

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAll() {
        Iterable<T> listAll = findAllPrivate();
        listAll.forEach(this::delete);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void flush(){ entityManager.flush();}

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S savedAndFlush(S entity){
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <S extends T> S updateAndFlush(S entity){
        entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public T findByIdOrNull(ID id){
        Optional<T> oEntitu = findByIdPrivate(id);
        return oEntitu.orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveNativeQuery(String nativeQuery) {
        entityManager.createNativeQuery(nativeQuery).executeUpdate();
    }

    protected void setParametersOnQuery(Query query, Map<String, Object> setParams){
        if((setParams != null) && !setParams.isEmpty()){
            for (Map.Entry<String, Object> entry : setParams.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void appendOrderBy(StringBuilder sb, List<String> fields, Map<String, String> validFields){
        if(fields != null && !fields.isEmpty() && validFields != null && !validFields.isEmpty()){
            boolean firstOrder = true;

            for(String s : fields){
                boolean desc = s.startsWith("-");
                if(s.startsWith("+") || s.startsWith("-")){
                    s = s.substring(1);
                }
                String value = validFields.get(s);
                if(value != null){
                    if(firstOrder){
                        sb.append(" ORDER BY ").append(value);
                        firstOrder = false;
                    } else {
                        sb.append(", ").append(value);
                    }
                    if(desc){
                        sb.append(" DESC ");
                    }
                }
            }
        }
    }
}
