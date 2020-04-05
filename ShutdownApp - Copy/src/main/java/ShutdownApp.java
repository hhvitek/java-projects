import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShutdownApp extends JFrame {
    private JPanel panelInner;
    private JPanel panelStatusBar;
    private JPanel panelApp;
    private JPanel panelTimers;
    private JPanel panelControls;
    private JLabel labelStatusBarMain;
    private JRadioButton radioButtonExactTime;
    private JRadioButton radioButtonAfterDelay;
    private JSpinner spinnerAfterDelay;
    private JButton buttonSubmit;
    private JButton buttonStop;
    private JButton buttonExit;
    private JSpinner spinnerExactTime;
    private JPanel panelClock;
    private JLabel labelCountDown;
    private JLabel labelCountDownGoal;

    private final JFrame swingFrame = new JFrame("Automatické vypnutí");
    private final Presenter presenter;

    public ShutdownApp(Presenter presenter) {
        this.presenter = presenter;


        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        spinnerExactTime.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                radioButtonExactTime.setSelected(true);

            }
        });
        spinnerAfterDelay.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                radioButtonAfterDelay.setSelected(true);
            }
        });
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                presenter.setShutdownAction();

                if (radioButtonAfterDelay.isSelected()) {
                    try {
                        spinnerAfterDelay.commitEdit();
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(swingFrame, "Neplatná hodnota: " + e.getLocalizedMessage(), "Chybka", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // "01:00"
                    String afterDelay = (String) spinnerAfterDelay.getValue();
                    presenter.scheduleAfterDelay(afterDelay);

                } else if (radioButtonExactTime.isSelected()) {
                    try {
                        spinnerExactTime.commitEdit();
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(swingFrame, "Neplatná hodnota: " + e.getLocalizedMessage(), "Chybka", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Date() since 1970
                    Date exactDate = (Date) spinnerExactTime.getValue();
                    presenter.scheduleExactTime(exactDate);

                } else {
                    JOptionPane.showMessageDialog(swingFrame, "Žádná akce nebyla vybrána.", "Chybka", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                setCountDownRunning();


            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                presenter.cancelTimer();
                setCountDownConfiguring();
            }
        });


        spinnerExactTime.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                Date exactDate = (Date) spinnerExactTime.getValue();
                presenter.setExactTime(exactDate);
                updateCountDown();
                updateCountDownGoal();
            }
        });
        spinnerAfterDelay.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                String afterDelay = (String) spinnerAfterDelay.getValue();
                presenter.setAfterDelay(afterDelay);
                updateCountDown();
                updateCountDownGoal();
            }
        });
        radioButtonExactTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                spinnerAfterDelay.setEnabled(false);
                spinnerExactTime.setEnabled(true);
            }
        });
        radioButtonAfterDelay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                spinnerExactTime.setEnabled(false);
                spinnerAfterDelay.setEnabled(true);
            }
        });
    }

    /**
     * Callable after swing frame.pack() function to center into monitor
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

    private void setCountDownRunning() {
        panelTimers.setEnabled(false);
        buttonSubmit.setEnabled(false);
        for(Component component: panelTimers.getComponents()) {
            component.setEnabled(false);
        }
        labelStatusBarMain.setText("Odpočet běží");
    }

    private void setCountDownConfiguring() {
        panelTimers.setEnabled(true);
        buttonSubmit.setEnabled(true);
        for(Component component: panelTimers.getComponents()) {
            component.setEnabled(true);
        }
        labelStatusBarMain.setText("Upravte časování a spusťte");
    }

    public void startSwing() {
        swingFrame.setContentPane(panelInner);
        swingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        swingFrame.pack();
        setCenteredToGoldenRatio(swingFrame);
        swingFrame.setVisible(true);
    }

    private void updateCountDownGoal() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String currentCountDownGoal = presenter.getCountDownGoal();
                labelCountDownGoal.setText(currentCountDownGoal);
            }
        });
    }


    public void updateCountDown() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String currentCountDown = presenter.getCurrentCountDown();
                labelCountDown.setText(currentCountDown);
            }
        });
    }

    private java.util.List<String> generateTimeSequence(int step) {
        java.util.List<String> sequence = new java.util.ArrayList<>();
        int onehour=60;

        for(int i=0; i<60*24; i+=step) {
            sequence.add(String.format("%02d", i/onehour) + ':' + String.format("%02d", i%onehour));
        }
        return sequence;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        spinnerExactTime = new javax.swing.JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor de1 = new JSpinner.DateEditor(spinnerExactTime, "HH:mm");
        spinnerExactTime.setEditor(de1);

        SpinnerListModel slm = new SpinnerListModel(generateTimeSequence(30));
        spinnerAfterDelay = new javax.swing.JSpinner(slm);
        spinnerAfterDelay.setValue("01:00");
    }
}
