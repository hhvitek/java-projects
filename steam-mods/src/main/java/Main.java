import gui.MainWindow;
import gui.SwingViewUtils;
import model.AppModel;
import model.JavaAppModelImpl;
import model.JavaModificationModelImpl;
import model.ModificationModel;
import model.persistent.PersistentStorageManager;
import model.persistent.json.JsonConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.nio.file.Path;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static final void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        logger.info("Starting...");

        SwingViewUtils.setLookAndFeelToSystemDefault();
        SwingViewUtils.setDefaultFont();

        Path storagePath = Path.of("src/main/resources/configuration.json");
        PersistentStorageManager storageManager = new JsonConfigurationManager(storagePath);
        ModificationModel modificationModel = new JavaModificationModelImpl(storageManager);
        AppModel appModel = new JavaAppModelImpl(storageManager);

        MainWindow mainWindow = new MainWindow(appModel, modificationModel);
        mainWindow.startView();

        logger.info("Finished...");
    }
}
