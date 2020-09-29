package gui;

import model.ModModel;
import model.ModModificationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Pattern;

public class MainWindow {
    private JPanel panelForm;
    private JPanel panelProgram;
    private JPanel panelStatusBar;
    private JScrollPane scrollPaneStatusBar;
    private JLabel scrollPaneLabel;
    private JPanel panelChooseModFolder;
    private JTextField textFieldChooseModFolder;
    private JButton buttonChooseModFolder;
    private JButton buttonStart;
    private JButton buttonExit;
    private JPanel panelActions;

    private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);

    // main frame contains gui-generated panel
    private final JFrame frameMain;

    private final ModModel modModel;

    public MainWindow(@NotNull ModModel modModel) {
        this.modModel = modModel;

        frameMain = new JFrame("Program");
        frameMain.setContentPane(panelForm);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
            Swing if any text contained in JLabel overflow its border... it looks bad
            JLabel has to be contained in JScrollPane and this should be contained in JPanel...
            JScrollPanel disable all borders...
         */
        scrollPaneStatusBar.setViewportBorder(null);
        scrollPaneStatusBar.setBorder(null);

        buttonChooseModFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Path standardModFolder = modModel.getStandardModFolder();

                Optional<Path> optionalFile = SwingViewUtils.showFileChooserAndGetFolder(standardModFolder);
                optionalFile.ifPresent(
                        chosenFile -> textFieldChooseModFolder.setText(chosenFile.toString())
                );
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Path chosenModFolder = Path.of(textFieldChooseModFolder.getText());
                    modModel.performModifications(chosenModFolder);
                } catch (InvalidPathException | ModModificationException | IOException ex) {
                    SwingViewUtils.showErrorMessageDialog(frameMain, ex.getLocalizedMessage());
                }
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void startView() {
        SwingViewUtils.runAndShowWindow(frameMain);
    }
}
