package repositories;

import entities.ModificationEntity;
import entities.ModificationsChainEntity;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class ModificationsChainRepository extends AbstractRepository<ModificationsChainEntity> {

    public ModificationsChainRepository(@NotNull EntityManager entityManager) {
        super(entityManager, ModificationsChainEntity.class);
    }
}
