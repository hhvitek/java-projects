package gui_swing;

import actions.ShutDownAction;
import model.Presenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import time.Time;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainForm {

    // Auto-generated variables
    private JPanel panelMainForm;
    private JPanel panelAppControl;
    private JPanel panelStatusBar;
    private JPanel panelUserCountDownInput;
    private JPanel panelControls;
    private JPanel panelCountDown;
    private JButton buttonSubmit;
    private JButton buttonCancel;
    private JButton buttonExit;
    private JSpinner spinnerDelay;
    private JLabel labelDelay;
    private JLabel labelExactlyWhen;
    private JLabel labelCountDown;
    private JLabel labelStatusBarLeft;
    private JLabel labelStatusBarRight;

    // logger
    private static final Logger logger = LoggerFactory.getLogger(MainForm.class);

    // Swings-JFrame represents main application window
    private final JFrame swingFrame = new JFrame("Automatické vypnutí");
    // backend - re-sends - user request for the processing
    private final Presenter presenter;
    // Application timer, ticks every (tickPeriod) = 1 second, it shows count down to zero
    private final Timer timer;
    private final int tickPeriod = 1000;
    // Default value to show in the user's input JSpinner
    private final String defaultSpinnerValue = "01:00";
    // Default step in the user's input JSpinner 01:00 -> 01:30 -> 02:00 ...
    private final int defaultSpinnerStep = 30;

    public MainForm(Presenter presenter) {
        this.presenter = presenter;

        // every tick update the count down
        this.timer = new Timer(
                tickPeriod, // defaults to every second
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Duration countDownRemaining = presenter.getCountDownRemaining();
                        if (countDownRemaining.isNegative() || countDownRemaining.isZero()) {
                            timer.stop();
                            presenter.performAction();

                            setConfigurationState();

                            stopSwingApplication();
                        }
                        updateCountDown();
                    }
                }
        );

        setConfigurationState();

        spinnerDelay.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                String delayHHMM = (String) spinnerDelay.getValue();
                // convert string to duration
                Duration delayDuration = Duration.between(LocalTime.MIN, LocalTime.parse(delayHHMM));
                presenter.setCountDownGoal(delayDuration);
                updateCountDownGoal();
                updateCountDown();
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                stopSwingApplication();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                    setConfigurationState();
                }
            }
        });
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (timer != null && !timer.isRunning()) {
                    presenter.setAction(new ShutDownAction());
                    String delayHHMM = (String) spinnerDelay.getValue();
                    // convert string to Duration
                    Duration delayDuration = Duration.between(LocalTime.MIN, LocalTime.parse(delayHHMM));
                    presenter.setCountDownGoal(delayDuration);

                    timer.start();
                    setCountingDownState();
                }
            }
        });
        panelCountDown.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                int width = panelCountDown.getWidth() / 2; // width 150 equals 16 font size ... 9.375
                int height = panelCountDown.getHeight(); // height: 55 equals 16 font size ... 3.4375

                int newFontSize = (int) (height / 3);

                Font newFont = new Font(labelExactlyWhen.getFont().getName(), labelExactlyWhen.getFont().getStyle(), newFontSize);

                labelExactlyWhen.setFont(newFont);
                labelCountDown.setFont(newFont);
            }
        });
    }

    /**
     * Updates UI's label representing CountDown final time.
     */
    private void updateCountDownGoal() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LocalTime countDownGoalTime = presenter.getCountDownGoal();
                String countDownGoal = countDownGoalTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                labelExactlyWhen.setText(countDownGoal);
            }
        });
    }

    /**
     * Updates UI's label representing CountDown.
     * Asks for updated/remaining value of CountDown
     * Should be called every timer's tick...(every second)
     */
    private void updateCountDown() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Duration countDownRemaining = presenter.getCountDownRemaining();
                labelCountDown.setText(convertDurationToHHMMSSString(countDownRemaining));
            }
        });
    }

    /**
     * Converts Javas Duration parameter into Javas String in the format HH:mm:SS (16:30:30)
     * @param duration
     * @return
     */
    private String convertDurationToHHMMSSString(Duration duration) {
        return String.format("%02d:%02d:%02d",
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart()
        );
    }

    /**
     * State of the application
     * Expecting users input
     */
    private void setConfigurationState() {
        spinnerDelay.setEnabled(true);
        spinnerDelay.setValue(defaultSpinnerValue);

        // sets the default value of count down to 01:00
        Time defaultDelay = Time.parse(defaultSpinnerValue);
        presenter.setCountDownGoal(defaultDelay.getDuration());

        buttonSubmit.setEnabled(true);
        buttonCancel.setEnabled(false);

        labelStatusBarLeft.setText("Načasujte a spusťte");

        updateCountDownGoal();
        updateCountDown();
    }

    /**
     * State of the application
     * Timer and countdown is running
     * Users can either exit application or cancel the timer (countdown)
     */
    private void setCountingDownState() {
        spinnerDelay.setEnabled(false);

        buttonSubmit.setEnabled(false);
        buttonCancel.setEnabled(true);

        labelStatusBarLeft.setText("Odpočet běží");
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

                swingFrame.pack();
                setCenteredToGoldenRatio(swingFrame);
                swingFrame.setVisible(true);
            }
        });
    }

    private void stopSwingApplication() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        swingFrame.setVisible(false);
        swingFrame.dispose();
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

    /**
     * Generates List sequence of Strings
     * @param step in minutes determines step expected the value of 30
     * @return
     */
    private java.util.List<String> generateTimeSequence(int step) {
        java.util.List<String> sequence = new java.util.ArrayList<>();
        int onehour=60;

        for(int i=0; i<60*12; i+=step) {
            String hourPart = String.format("%02d", i/onehour);
            String minutePart = String.format("%02d", i%onehour);
            sequence.add(hourPart + ':' + minutePart);
        }
        return sequence;
    }

    /**
     * Creates JSpinner component with data-model as a List of Strings:
     * 00:00, 00:30, 01:00, 01:30, .... 11:30
     * Default value is 01:00
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        SpinnerListModel slm = new SpinnerListModel(generateTimeSequence(defaultSpinnerStep));
        spinnerDelay = new javax.swing.JSpinner(slm);
        spinnerDelay.setValue(defaultSpinnerValue);
    }
}
