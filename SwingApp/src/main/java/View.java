import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class View extends JFrame implements IObserver {
    private JPanel         viewPanel;
    private JPanel         innerWorkerPanel;
    private JTextField     localFolderTextField;
    private JButton        localFolderButton;
    private JTextField     configurationFileTextField;
    private JButton        configurationFileButton;
    private JButton        startButton;
    private JButton        stopButton;
    private JProgressBar   progressBarWorker;
    private JLabel         localFolderLabel;
    private JLabel         configurationFileLabel;
    private JButton        exitButton;
    private JLabel         statusLabel;
    private JLabel         labelStatusMiddle;
    private JLabel         labelStatusRight;
    private JLabel         labelStatusLeft;
    private JPanel         panelWorker;
    private JTabbedPane    tabbedPane;
    private JPanel         panelEmail;
    private JPanel         innerEmailPanel;
    private JTextField     textFieldSubject;
    private JButton        buttonSendEmail;
    private JButton        buttonExitEmail;
    private JTextField     textFieldTo;
    private JTextField     textFieldFrom;
    private JTextField     textFieldUsername;
    private JTextField     textFieldSMTPhost;
    private JTextField     textFieldSMTPport;
    private JPasswordField passwordFieldPassword;
    private JLabel         labelHost;
    private JLabel         labelPort;
    private JPanel         panelStatusEmail;
    private JPanel         panelConfigurationEmail;
    private JPanel         panelConfigurationEmailLeft;
    private JPanel         panelConfigurationEmailRight;
    private JTextArea      textAreaEmailBody;
    private JButton        buttonEmailValidate;
    private JPanel         panelStatusBar;

    private Controller controller;


    public View() {
        setContentPane(viewPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitProgram();
            }
        });
        localFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int retVal = fileChooser.showOpenDialog(View.this);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    localFolderTextField.setText(file.getAbsolutePath());
                }
            }
        });
        configurationFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int retVal = fileChooser.showOpenDialog(View.this);
                if (retVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    configurationFileTextField.setText(file.getAbsolutePath());
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startWorkerThread();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.worker_stopWorker();
            }
        });
        buttonExitEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitProgram();
            }
        });
        buttonEmailValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validated = controller.email_validate(
                        textFieldUsername.getText(),
                        passwordFieldPassword.getPassword(),
                        textFieldSMTPhost.getText(),
                        textFieldSMTPport.getText(),
                        textFieldFrom.getText(),
                        textFieldTo.getText(),
                        textFieldSubject.getText(),
                        textAreaEmailBody.getText()
                );
                if (validated) {
                    setStatusInfo(null, null, "Validated");
                } else {
                    setStatusInfo(null, null, "Invalid");
                }
            }
        });
        buttonSendEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.email_sendEmail(
                        textFieldUsername.getText(),
                        passwordFieldPassword.getPassword(),
                        textFieldSMTPhost.getText(),
                        textFieldSMTPport.getText(),
                        textFieldFrom.getText(),
                        textFieldTo.getText(),
                        textFieldSubject.getText(),
                        textAreaEmailBody.getText()
                );
            }
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void startWorkerThread() {
        int cycleNo = new Random().nextInt(10) + 10;
        controller.worker_startWorker(cycleNo);
    }

    @Override
    public void updateObserver(int count, int total, Status status, String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(
                        String.format("%d/%d, status: <%s>, <%s>", count, total, status.toString(),
                                      message));

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

    @Override
    public void observableFinished() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


            }
        });
    }

    public void setStatusInfo(String statusLeft, String statusMiddle, String statusRight) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                labelStatusLeft.setText(statusLeft);
                labelStatusMiddle.setText(statusMiddle);
                labelStatusRight.setText(statusRight);
            }
        });
    }


    private void exitProgram() {
        System.out.println("Exiting program.");
        System.exit(0);
    }
}
