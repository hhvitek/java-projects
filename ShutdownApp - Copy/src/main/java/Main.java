import javax.swing.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        System.out.println("hello world");

        Presenter presenter = new Presenter();

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        ShutdownApp app = new ShutdownApp(presenter);
        presenter.setView(app);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                app.startSwing();
            }
        });
    }
}
