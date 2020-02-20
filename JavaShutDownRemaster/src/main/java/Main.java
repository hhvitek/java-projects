
import actions.ActionAbstract;
import dynamic_classloader.ClassLoadingException;
import dynamic_classloader.LoaderByClassNames;
import gui_swing.MainForm;
import ini.InvalidConfigFileFormatException;
import model.ConfigIni;
import model.IModel;
import model.ModelImpl;
import model.Presenter;
import model.sql.DbConnectionErrorException;
import model.sql.ISqlDbDao;
import model.sql.jdbc_dao.JdbcSqlImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static String CONFIGURATION_FILEPATH = "configuration.ini";
    private static String DB_DEFAULT_PATH = "jdbc:sqlite:sqlite.sqlite3";

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        logger.info("Application has started.");

        //UI's looks
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN,16));

        //Let's load the configuration file
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resourceUrl = classLoader.getResource(CONFIGURATION_FILEPATH);
        if (resourceUrl == null) {
            String errorMessage = "Configuration file has not been found: " + CONFIGURATION_FILEPATH;
            logger.error(errorMessage);
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File configurationFile = new File(resourceUrl.getFile());

        ConfigIni ini = new ConfigIni();
        try {
            ini.load(configurationFile);
        } catch (IOException e) {
            String errorMessage = "Error working with the configuration file: " + CONFIGURATION_FILEPATH;
            logger.error(errorMessage, e);
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (InvalidConfigFileFormatException e) {
            String errorMessage = "Configuration file is invalid. Please check for any format errors: " + CONFIGURATION_FILEPATH;
            logger.error(errorMessage, e);
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //get actual values from the configuration file
        List<String> actionClassNames = ini.getClassNames();

        //instantiate ActionAbstract actions
        List<ActionAbstract> actions;
        try {
            actions = LoaderByClassNames.loadAll(actionClassNames);
        } catch (ClassLoadingException e) {
            String errorMessage = "Actions class names defined in the configuration file are invalid. "
                    + actionClassNames.toString();
            logger.error(errorMessage, e);
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ISqlDbDao db = new JdbcSqlImpl();
        try {
            db.initDbConnection(DB_DEFAULT_PATH);
            db.saveAllActions(actions);
        } catch (DbConnectionErrorException e) {
            String errorMessage = "Failed to save actions into database. "
                    + e.getLocalizedMessage();
            logger.error(errorMessage, e);
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        IModel model = new ModelImpl(db);

        MainForm mainForm = new MainForm(actions, model);
        mainForm.startSwingApplication();

    }

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }


}
