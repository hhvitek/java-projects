package model.modifications;

import model.exceptions.ModificationException;
import model.modifications.operations.Operation;
import model.modifications.operations.directory.AbstractDirectoryOperation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class BasicModification implements Modification {

    private static final Logger logger = LoggerFactory.getLogger(BasicModification.class);

    private String id;
    private String name;
    private String description;
    private Class<?> clazz;

    private Operation executableOperation;

    public BasicModification(@NotNull String id,
                             @NotNull String name,
                             @NotNull AbstractDirectoryOperation executableOperation,
                             @NotNull Class<?> clazz) {
        this.id = id;
        this.name = name;
        this.executableOperation = executableOperation;
        this.clazz = clazz;
    }

    public BasicModification(@NotNull String id,
                             @NotNull String name,
                             @NotNull AbstractDirectoryOperation executableOperation,
                             @NotNull Class<?> clazz,
                             @NotNull String description) {
        this(id, name, executableOperation, clazz);

        this.description = description;
    }

    @Override
    public @NotNull String getId() {
        return id;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @Override
    public @NotNull Class<?> getClazz() {
        return clazz;
    }

    @Override
    public void execute(@NotNull Path pathToFolder) throws IOException, ModificationException {
        logger.info("Executing <{}> modification.", name);
        executableOperation.execute(pathToFolder);
    }
}
