
import gui_swing.MainForm;
import model.IModel;
import model.JavaModel;
import model.Presenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        logger.info("Application has started.");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN,16));

        IModel model = new JavaModel();
        Presenter presenter = new Presenter(model);
        MainForm mainForm = new MainForm(presenter);
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
