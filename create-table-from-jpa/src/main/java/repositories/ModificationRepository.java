package repositories;

import entities.ModificationEntity;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class ModificationRepository extends AbstractRepository<ModificationEntity> {

    public ModificationRepository(@NotNull EntityManager entityManager) {
        super(entityManager, ModificationEntity.class);
    }
}
