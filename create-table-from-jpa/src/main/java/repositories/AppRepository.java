package repositories;

import entities.AppEntity;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class AppRepository extends AbstractRepository<AppEntity> {

    public AppRepository(@NotNull EntityManager entityManager) {
        super(entityManager, AppEntity.class);
    }
}
