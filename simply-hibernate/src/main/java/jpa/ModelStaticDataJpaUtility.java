package jpa;

import jpa.entities.CurrentDataEntity;
import jpa.entities.CurrentDataRepository;
import jpa.entities.SuffixesCollectionRepository;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class ModelStaticDataJpaUtility {

    private final EntityManager entityManager;

    private final CurrentDataRepository currentDataRepository;
    private final SuffixesCollectionRepository suffixesCollectionRepository;

    public ModelStaticDataJpaUtility(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
        currentDataRepository = new CurrentDataRepository(entityManager);
        suffixesCollectionRepository = new SuffixesCollectionRepository(entityManager);
    }

    public void saveCurrentDataEntity(@NotNull String name, @NotNull String value) {
        CurrentDataEntity currentDataEntity = createCurrentDataEntity(
                name,
                value
        );
        currentDataRepository.update(currentDataEntity);
    }

    private CurrentDataEntity createCurrentDataEntity(@NotNull String name, @NotNull String value) {
        CurrentDataEntity currentDataEntity = new CurrentDataEntity(name, value);
        return currentDataEntity;
    }
}
