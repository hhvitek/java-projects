package jpa.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CurrentDataRepository extends Repository<CurrentDataEntity> {

    public CurrentDataRepository(@NotNull EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<CurrentDataEntity> getEntityClass() {
        return CurrentDataEntity.class;
    }

    public Optional<String> findValueByName(@NotNull String name) {
        Optional<CurrentDataEntity> currentDataEntityOpt = findOneById(name);
        if (currentDataEntityOpt.isPresent()) {
            return Optional.of(currentDataEntityOpt.get().getValue());
        } else {
            return Optional.empty();
        }
    }
}
