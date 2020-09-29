import gui.MainWindow;
import gui.SwingViewUtils;
import model.ModModel;
import model.stellaris.StellarisModModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static final void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        logger.info("Starting...");

        SwingViewUtils.setLookAndFeelToSystemDefault();
        SwingViewUtils.setDefaultFont();

        ModModel modModel = new StellarisModModel();
        MainWindow mainWindow = new MainWindow(modModel);
        mainWindow.startView();

        System.out.println(System.getProperty("os.name"));

        logger.info("Finished...");
    }
}
