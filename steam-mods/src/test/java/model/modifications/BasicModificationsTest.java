package model.modifications;

import model.modifications.operations.directory.AbstractDirectoryOperation;
import model.modifications.operations.directory.ExtractAllDirOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicModificationsTest {

    private Modification modification;

    public BasicModificationsTest() {
        AbstractDirectoryOperation extractAll = new ExtractAllDirOperation();
        modification = new BasicModification("id1000", ExtractAllDirOperation.DEFAULT_NAME, extractAll, ExtractAllDirOperation.class, ExtractAllDirOperation.DEFAULT_DESC);
    }

    @Test
    public void defaultNameAndDescTest() {
        String actualName = modification.getName();
        String expectedName = ExtractAllDirOperation.DEFAULT_NAME;

        String actualDesc = modification.getDescription();
        String expectedDesc = ExtractAllDirOperation.DEFAULT_DESC;

        assertEquals(expectedName, actualName);
        assertEquals(expectedDesc, expectedDesc);
    }

    @Test
    public void packageAndClassTest() {
        String expectedPackageAndClass = "model.modifications.operations.directory.ExtractAllDirOperation";
        String actualPackageAndClass = modification.getClazz().getCanonicalName();

        Assertions.assertEquals(expectedPackageAndClass, actualPackageAndClass);
    }
}
