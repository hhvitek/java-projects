package App;

import controller.IController;
import controller.simplecontroller.SimpleController;
import model.IModel;
import model.simplemodel.SimpleModel;
import view.MainForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String INPUT_FOLDER = ".";
    private static final String OUTPUT_FOLDER = "./OUTPUT";

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        logger.info("Application has started.");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setUIFont(new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 14));

        IModel model = new SimpleModel();
        IController controller = new SimpleController(model);

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
