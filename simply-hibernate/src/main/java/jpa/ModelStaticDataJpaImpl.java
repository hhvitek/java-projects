package jpa;


import jpa.entities.CurrentDataEntity;
import jpa.entities.CurrentDataRepository;
import jpa.entities.SuffixesCollectionEntity;
import jpa.entities.SuffixesCollectionRepository;
import model.IModelStaticData;
import org.jetbrains.annotations.NotNull;
import suffixes.CollectionOfSuffixesCollections;
import suffixes.ISuffixesCollection;
import suffixes.SuffixesCollectionImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ModelStaticDataJpaImpl implements IModelStaticData {

    private static final String DEFAULT_OUTPUT_FOLDER_NAME = "OUTPUT";
    private static final String DB_SUFFIXES_DELIMITER = ",";
    private static final String DEFAULT_SUFFIXES_COLLECTION_NAME = "DEFAULT_SUFFIXES_COLLECTION";

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("my-sqlite");

    private final ModelStaticDataJpaUtility jpaUtility;


    public ModelStaticDataJpaImpl() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager(); // Retrieve an application managed entity manager
        jpaUtility = new ModelStaticDataJpaUtility(entityManager);

    }

    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {
        jpaUtility.saveCurrentDataEntity("input_folder", newInputFolder.toAbsolutePath().toString());
    }

    @Override
    public @NotNull Path getInputFolder() {
        return getPathFromDatabase(
                "input_folder",
                getCurrentWorkingFolder()
        );
    }

    private Path getPathFromDatabase(String name, Path returnIfNotFound) {
        Optional<CurrentDataEntity> currentDataEntityOpt = currentDataRepository.findOneById(name);
        if (currentDataEntityOpt.isPresent()) {
            String value = currentDataEntityOpt.get().getValue();
            return convertStringPathIntoPathIfFailedReturnDefaultPath(value, returnIfNotFound);
        } else {
            return returnIfNotFound;
        }
    }

    private Path convertStringPathIntoPathIfFailedReturnDefaultPath(String stringPath, Path defaultPath) {
        try {
            return Path.of(stringPath);
        } catch (InvalidPathException ex) {
            return defaultPath;
        }
    }

    private static Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {
        jpaUtility.saveCurrentDataEntity("output_folder", newOutputFolder.toAbsolutePath().toString());
    }

    @Override
    public @NotNull Path getOutputFolder() {
        return getPathFromDatabase(
                "output_folder",
                getCurrentWorkingFolder().resolve(DEFAULT_OUTPUT_FOLDER_NAME)
        );
    }

    @Override
    public void setCurrentSuffixesCollection(@NotNull ISuffixesCollection newSuffixesCollection) {
        SuffixesCollectionEntity suffixesCollectionEntity = fromISuffixesCollection(newSuffixesCollection);
        suffixesCollectionRepository.update(suffixesCollectionEntity);

        saveCurrentDataEntity("suffixes_collection", suffixesCollectionEntity.getName());
    }

    private SuffixesCollectionEntity fromISuffixesCollection(@NotNull ISuffixesCollection suffixesCollection) {
        String name = suffixesCollection.getName();
        String delimitedSuffixes = suffixesCollection.getSuffixesAsDelimitedString(DB_SUFFIXES_DELIMITER);
        SuffixesCollectionEntity suffixesCollectionEntity = new SuffixesCollectionEntity(name, delimitedSuffixes);
        return suffixesCollectionEntity;
    }

    private ISuffixesCollection toISuffixesCollection(@NotNull SuffixesCollectionEntity suffixesCollectionEntity) {

        String name = suffixesCollectionEntity.getName();
        String delimitedSuffixes = suffixesCollectionEntity.getSuffixes();

        ISuffixesCollection suffixesCollection = new SuffixesCollectionImpl(name);
        suffixesCollection.addSuffixes(delimitedSuffixes, DB_SUFFIXES_DELIMITER);

        return suffixesCollection;
    }


    @Override
    public @NotNull ISuffixesCollection getCurrentSuffixesCollection() {
        Optional<String> suffixesCollectionNameOpt = currentDataRepository.findValueByName("suffixes_collection");
        if (suffixesCollectionNameOpt.isPresent()) {
            String suffixesCollectionName = suffixesCollectionNameOpt.get();
            Optional<SuffixesCollectionEntity> suffixesCollectionEntityOpt = suffixesCollectionRepository.findOneById(suffixesCollectionName);
            if (suffixesCollectionEntityOpt.isPresent()) {
                return toISuffixesCollection(suffixesCollectionEntityOpt.get());
            }
        }
        return getDefaultSuffixesCollection();
    }

    private ISuffixesCollection getDefaultSuffixesCollection() {
        return new SuffixesCollectionImpl(DEFAULT_SUFFIXES_COLLECTION_NAME);
    }


    @Override
    public CollectionOfSuffixesCollections getSuffixesDb() {
        return null;
    }

    @Override
    public void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection) {

    }

    @Override
    public Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(String name) {
        return Optional.empty();
    }
}
