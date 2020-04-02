package UI;

import Model.IModel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class MainForm {
    private JPanel panelMainForm;
    private JPanel panelStatusBar;
    private JPanel panelApp;
    private JTextField textFieldInputFolder;
    private JButton buttonInputFolderChoose;
    private JTextField textFieldOutputFolder;
    private JButton buttonOutputFolderChoose;
    private JButton buttonStart;
    private JButton buttonExit;
    private JButton buttonCancel;
    private JProgressBar progressBar;
    private JPanel panelInputFolder;
    private JPanel panelOutputFolder;
    private JPanel panelControls;
    private JLabel labelStatusBarStatusText;
    private JLabel labelStatusBarCopyright;
    private JPanel panelIntermediateActions;
    private JButton buttonCountAudioFiles;
    private JPanel panelExtensions;
    private JTextField textFieldAudioExtensions;
    private JButton buttonSetAudioExtensions;
    private JButton buttonValidate;


    //################CUSTOM VARIABLES

    // logger
    private static final Logger logger = LoggerFactory.getLogger(MainForm.class);

    // Swings-JFrame represents main application window
    private final JFrame swingFrame = new JFrame("Audio-Files Processing");

    // IModel
    private final IModel model;

    //################END CUSTOM VARIABLES

    public MainForm(@NotNull IModel model) {
        this.model = model;

        buttonInputFolderChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Optional<File> optionalFile = showFileChooserAndGetFolder(model.getInputFolder().toFile());
                optionalFile.ifPresent(
                        file -> setInputFolder(file.toString())
                );
            }
        });
        buttonOutputFolderChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Optional<File> optionalFile = showFileChooserAndGetFolder(model.getOutputFolder().toFile());
                if (optionalFile.isPresent()) {
                    setOutputFolder(optionalFile.get().toString());
                }
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        buttonValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateAllFields();
                String validateText = model.validate();
                JOptionPane.showMessageDialog(swingFrame, validateText, "Validation result", JOptionPane.PLAIN_MESSAGE);
                setStatusBarMessage("Validation Performed.");
            }
        });
        buttonCountAudioFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateAllFields();
                int count = model.countRelevantAudioFiles();
                setStatusBarMessage("There are around: " + count + " audio files.");
            }
        });
        buttonSetAudioExtensions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setAudioExtensions(textFieldAudioExtensions.getText());
            }
        });
    }

    /**
     * Starts UI of the application
     */
    public void startSwingApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingFrame.setContentPane(panelMainForm);
                swingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // disables frame's maximization button
                // swingFrame.setResizable(false);
                setDefaultState();

                swingFrame.pack();
                setCenteredToGoldenRatio(swingFrame);
                swingFrame.setVisible(true);

            }
        });
    }

    private Optional<File> showFileChooserAndGetFolder(File currentDirectory) {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(currentDirectory);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnVal = fc.showOpenDialog(swingFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return Optional.of(file);
        }
        return Optional.empty();
    }

    private void setStatusBarMessage(String message) {
        labelStatusBarStatusText.setText(message);
    }

    private void setInputFolder(String inputFolder) {
        textFieldInputFolder.setText(inputFolder);
        model.setInputFolder(Path.of(inputFolder));
    }

    private void setOutputFolder(String outputFolder) {
        textFieldOutputFolder.setText(outputFolder);
        model.setOutputFolder(Path.of(outputFolder));
    }

    private void setAudioExtensions(String audioExtensions) {
        textFieldAudioExtensions.setText(audioExtensions);
        model.setAudioExtensions(audioExtensions);
    }

    private void updateAllFields() {
        model.setInputFolder(Path.of(textFieldInputFolder.getText()));
        model.setOutputFolder(Path.of(textFieldOutputFolder.getText()));
        model.setAudioExtensions(textFieldAudioExtensions.getText());
    }

    /**
     * Sets App to default state
     */
    private void setDefaultState() {
        this.setInputFolder(model.getDefaultInputFolder().toString());
        this.setOutputFolder(model.getDefaultOutputFolder().toString());
        this.setAudioExtensions(model.getDefaultAudioExtensions());
    }

    /**
     * Callable after swing frame.pack() function to center application window.
     *
     * @param frame
     */
    private static void setCenteredToGoldenRatio(JFrame frame) {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int) screenDimension.getHeight();
        int screenWidth = (int) screenDimension.getWidth();

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        int goldenRatioHeight = (int) ((screenHeight - frameHeight) * 0.38);

        frame.setLocation((screenWidth / 2) - (frameWidth / 2), goldenRatioHeight);
    }
}
