package model.stellaris;

import model.ModModel;
import model.ModModificationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StellarisModModel implements ModModel {

    private static final Logger logger = LoggerFactory.getLogger(StellarisModModel.class);

    public static final Path standardModFolder;

    static {
        String systemHome = System.getProperty("user.home");
        if (isOSWindows10()) {
            Path stellarisConfigFolder = Path.of(systemHome, "Documents/Paradox Interactive/Stellaris");
            Path stellarisModFolder = stellarisConfigFolder.resolve("mod");
            standardModFolder = stellarisModFolder;
        } else {
            standardModFolder = Path.of(systemHome);
        }
    }

    private static boolean isOSWindows10() {
        String osName = System.getProperty("os.name");
        return osName.startsWith("Windows 10");
    }

    @Override
    public @NotNull Path getStandardModFolder() {
        return standardModFolder;
    }

    @Override
    public void performModificationsInStandardModFolder() throws ModModificationException, IOException {
        performModifications(standardModFolder);
    }

    @Override
    public void performModifications(@NotNull Path modFolder) throws ModModificationException, IOException {
        List<Path> descriptorModFiles = findDescriptorModFiles(modFolder);

        for (Path descriptorModFile: descriptorModFiles) {
            StellarisModFileActions descriptorModFileActions = new StellarisModFileActions(descriptorModFile);
            modifyDescriptorModFile(descriptorModFileActions);
            descriptorModFileActions.copyTo(modFolder);
        }
    }

    private @NotNull List<Path> findDescriptorModFiles(@NotNull Path modFolder) throws IOException {
        try (Stream<Path> descriptorModFiles = Files.find(
                modFolder,
                1,
                ((path, basicFileAttributes) -> basicFileAttributes.isRegularFile()
                        && path.endsWith(".mod")),
                null
        )
        ){
            return descriptorModFiles.collect(Collectors.toList());
        }
    }


    private void modifyDescriptorModFile(@NotNull StellarisModFileActions descriptorModFileActions) {
        logger.info("Modifying file: <{}>", descriptorModFileActions.getDescriptorModFile());

        descriptorModFileActions.removeArchiveItem();
        descriptorModFileActions.removePathItem();
        descriptorModFileActions.addOrUpdatePathItem();
        descriptorModFileActions.removeBlankLines();
    }
}
