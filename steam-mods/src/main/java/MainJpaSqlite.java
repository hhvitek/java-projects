import gui.MainWindow;
import gui.SwingViewUtils;
import model.AppModel;
import model.JavaAppModelImpl;
import model.JavaModificationModelImpl;
import model.ModificationModel;
import model.persistent.PersistentStorageManager;
import model.persistent.db.AbstractEntityManagerFactory;
import model.persistent.db.SqliteConfigurationManager;
import model.persistent.db.SqliteEntityManagerFactoryImpl;
import model.persistent.json.JsonConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.nio.file.Path;

public final class MainJpaSqlite {

    private static final Logger logger = LoggerFactory.getLogger(MainJpaSqlite.class);

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("my-sqlite");

    private static final AbstractEntityManagerFactory entityManagerFactory = new SqliteEntityManagerFactoryImpl(ENTITY_MANAGER_FACTORY);

    private static final EntityManager storageEntityManager = entityManagerFactory.createEntityManager();

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        logger.info("STARTING JPA");

        SwingViewUtils.setLookAndFeelToSystemDefault();
        SwingViewUtils.setDefaultFont();

        PersistentStorageManager storageManager = new SqliteConfigurationManager(storageEntityManager);
        ModificationModel modificationModel = new JavaModificationModelImpl(storageManager);
        AppModel appModel = new JavaAppModelImpl(storageManager);

        MainWindow mainWindow = new MainWindow(appModel, modificationModel);
        mainWindow.startView();

        addShutdownHooks();
        logger.info("FINISHED JPA initialization");
    }

    public static void addShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run()
            {
                logger.info("EXITING application");
                storageEntityManager.close();
                ENTITY_MANAGER_FACTORY.close();
            }
        });
    }


}
