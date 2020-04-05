import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args)
            throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException,
                   IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        View view = new View();
        Controller controller = new Controller();
        WorkerLibrary worker = new WorkerLibrary();
        worker.attachObserver(view);

        view.setController(controller);
        controller.setView(view);
        controller.setWorker(worker);

        view.pack();
        setCenteredToGoldenRatio(view);
        view.setVisible(true);
    }

    private static void setCenteredToGoldenRatio(JFrame frame) {
        Dimension screenDimension = Toolkit.getDefaultToolkit()
                                           .getScreenSize();
        int screenHeight = (int) screenDimension.getHeight();
        int screenWidth = (int) screenDimension.getWidth();

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        int goldenRatioHeight = (int) ((screenHeight - frameHeight) * 0.38);

        frame.setLocation(
                (screenWidth / 2) - (frameWidth / 2),
                goldenRatioHeight
        );
    }
}
