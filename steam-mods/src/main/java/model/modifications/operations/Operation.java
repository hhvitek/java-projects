package model.modifications.operations;

import model.exceptions.ModificationException;

import java.io.IOException;
import java.nio.file.Path;

public interface Operation {
    void execute(Path file) throws IOException, ModificationException;
}
