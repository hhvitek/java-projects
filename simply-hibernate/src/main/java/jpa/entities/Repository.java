package jpa.entities;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public abstract class Repository<T> {

    protected static final Logger logger = LoggerFactory.getLogger(Repository.class);

    protected final EntityManager entityManager;

    public Repository(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract Class<T> getEntityClass();

    public Optional<T> findOneById(@NotNull Object id) {
        T entity = entityManager.find(getEntityClass(), id);
        if (entity != null) {
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }

    public List<T> findAll() {
        String query = String.format("SELECT a FROM %s a", getEntityClass().getName());
        return entityManager.createQuery(query).getResultList();
    }

    public void delete(@NotNull T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            logger.error("Failed to remove {} from DB. {}.", getEntityClass(), entity);
        }
    }

    public void create(@NotNull T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            logger.error("Failed to persist/create {} into DB. {}.", getEntityClass(), entity);
        }
    }

    public void update(@NotNull T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            logger.error("Failed to merge/update {} into DB. {}.", getEntityClass(), entity);
        }
    }
}
