package app.view;

import app.photos.downloaders.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SwingView implements IView {

    private JPanel       panelInner;
    private JPanel       panelWorker;
    private JPanel       panelConfiguration;
    private JTextField   textFieldFolder;
    private JButton      buttonLoadConfig;
    private JTextField   textFieldConfigurationFile;
    private JButton      buttonLoadFolder;
    private JButton      buttonStart;
    private JButton      buttonStop;
    private JButton      buttonExit;
    private JProgressBar progressBarWorker;
    private JLabel       localFolderLabel;
    private JLabel       configurationFileLabel;
    private JLabel       labelStatus;
    private JLabel       labelStatusMiddle;
    private JLabel       labelStatusRight;
    private JLabel       labelStatusLeft;
    private JPanel       panelInnerWorker;
    private JTabbedPane  tabbedPanel;
    private JLabel       labelConfigurationFile;
    private JLabel       labelFolder;
    private JPanel       panelStatusBar;
    private JPanel       panelControl;
    private JButton      buttonOpen;
    private JPanel       panelOutput;
    private JPanel       panelInnerOutput;
    private JEditorPane  editorPanelOutput;

    private final        JFrame     swingFrame = new JFrame("GooglePhotos Downloader");
    private              Controller controller;
    private static final Logger     logger     = LoggerFactory.getLogger(SwingView.class);

    public SwingView() {

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitProgram();
            }
        });
        buttonLoadFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int retVal = fileChooser.showOpenDialog(swingFrame);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    textFieldFolder.setText(file.getAbsolutePath());
                }
            }
        });
        buttonLoadConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int retVal = fileChooser.showOpenDialog(swingFrame);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    textFieldConfigurationFile.setText(file.getAbsolutePath());
                }
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.startDownloadingPhotos(textFieldConfigurationFile.getText(),
                                                  textFieldFolder.getText()
                );
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stopDownloadingPhotos();
            }
        });

        buttonOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String configFilePath = textFieldConfigurationFile.getText();
                if (configFilePath == null || configFilePath.isBlank()) {
                    setStatusInfo(null, "Please fill in the configuration file path",
                                  Status.Error.toString()
                    );
                } else {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.EDIT)) {
                            try {
                                desktop.edit(new File(configFilePath));
                            } catch (IOException | IllegalArgumentException ex) {
                                setStatusInfo(null, "Error opening the configuration file" +
                                                    configFilePath, Status.Error.toString());
                            }
                        }
                    } else {
                        setStatusInfo(null,
                                      "Opening the file in an external editor is not supported.",
                                      Status.Error.toString()
                        );
                    }
                }
            }
        });

        swingFrame.setContentPane(panelInner);
        swingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        swingFrame.pack();
        setCenteredToGoldenRatio(swingFrame);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void setLocalFolder(String folder) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textFieldFolder.setText(folder);
            }
        });

    }

    @Override
    public void setConfigurationFilePath(String path) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textFieldConfigurationFile.setText(path);
            }
        });

    }

    @Override
    public void setStatus(Status status, String message) {
        setStatusInfo(null, message, status.toString());
    }

    @Override
    public void startView() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingFrame.setVisible(true);
            }
        });
    }

    @Override
    public void updateObserver(int count, int total, String fileName, String message,
                               Status status) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                int percentDone = (int) ((count / (double) total) * 100);

                progressBarWorker.setValue(percentDone);
                progressBarWorker.setString(null);
                progressBarWorker.setStringPainted(true);

                if (status != Status.Ok) {
                    progressBarWorker.setString(status.toString());
                }

                setStatusInfo(count + "/" + total, message, status.toString());

            }

        });
    }

    private void setStatusInfo(String statusLeft, String statusMiddle, String statusRight) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                labelStatusLeft.setText(statusLeft);
                labelStatusMiddle.setText(statusMiddle);
                labelStatusRight.setText(statusRight);

                logger.info("{} {} {}", statusLeft, statusMiddle, statusRight);

            }
        });
    }

    private void exitProgram() {
        System.out.println("Exiting program.");
        System.exit(0);
    }

    private static void setCenteredToGoldenRatio(JFrame frame) {
        Dimension screenDimension = Toolkit.getDefaultToolkit()
                                           .getScreenSize();
        int screenHeight = (int) screenDimension.getHeight();
        int screenWidth = (int) screenDimension.getWidth();

        int frameHeight = frame.getHeight();
        int frameWidth = frame.getWidth();

        int goldenRatioHeight = (int) ((screenHeight - frameHeight) * 0.38);

        frame.setLocation((screenWidth / 2) - (frameWidth / 2), goldenRatioHeight);
    }

}
