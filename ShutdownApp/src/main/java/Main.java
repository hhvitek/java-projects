import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        System.out.println("hello world");

        Presenter presenter = new Presenter();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setUIFont (new javax.swing.plaf.FontUIResource("Segoe UI",Font.PLAIN,16));
        ShutdownApp app = new ShutdownApp(presenter);
        presenter.setView(app);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.startSwing();
            }
        });
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
