package gui;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Optional;

public final class SwingViewUtils {

    private SwingViewUtils() {
    }

    public static void setLookAndFeelToSystemDefault() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    public static void setDefaultFont() {
        setUIFont(new FontUIResource(null, Font.PLAIN, 14));
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, f);
        }
    }

    public static void runAndShowWindow(JFrame frame) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.pack();
                setCenteredToGoldenRatio(frame);
                frame.setVisible(true);
            }
        });
    }

    /**
     * Callable after swing frame.pack() function to center application window.
     *
     * @param frame Swing's JFrame to be centered.
     */
    public static void setCenteredToGoldenRatio(JFrame frame) {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int) screenDimension.getHeight();
        int screenWidth = (int) screenDimension.getWidth();

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        int goldenRatioHeight = (int) ((screenHeight - frameHeight) * 0.38);

        frame.setLocation((screenWidth / 2) - (frameWidth / 2), goldenRatioHeight);
    }

    public static void showErrorMessageDialog(@NotNull JFrame parentFrame, @NotNull String message) {
        JOptionPane.showMessageDialog(parentFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void addChangeListenerToJTextComponentOnChange(@NotNull JTextComponent text, @NotNull Runnable runnable) {

        text.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void insertUpdate(DocumentEvent e) {
                runnable.run();
            }
        });
    }

    /**
     * Show folder choosing standard dialog and returns the chosen folder...
     * @param currentDirectory default directory to show
     * @return the chosen folder
     */
    public static @NotNull Optional<Path> showFileChooserAndGetFolder(@NotNull Path currentDirectory) {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(currentDirectory.toFile());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return Optional.of(file.toPath());
        }
        return Optional.empty();
    }

}
