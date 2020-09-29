package model;

import model.stellaris.StellarisModModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class StellarisModModelTest {

    ModModel modModel = new StellarisModModel();

    @Test
    public void standardModFolderTest() {
        Path defaultModPath = modModel.getStandardModFolder();
        Assertions.assertTrue(defaultModPath.endsWith("Documents/Paradox Interactive/Stellaris/mod"));
    }

}
