package gui_swing;

import actions.ActionAbstract;
import model.ScheduledAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduledActionsUI {

    private JPanel panel;
    private ButtonGroup buttonGroup;
    private GridBagConstraints c;
    private Map<Integer, ScheduledActionsUIContainer> scheduledActionsUIContainerMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ScheduledActionsUI.class);


    public ScheduledActionsUI() {
        this.panel = new JPanel(new GridBagLayout());
        this.c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0 , 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
    }

    public JPanel createScheduledActionsUI(@NotNull List<ScheduledAction> scheduledActions) {

        panel.removeAll();

        this.buttonGroup = new ButtonGroup();

        for (ScheduledAction scheduledAction: scheduledActions) {
            String actionName = scheduledAction.getAction().getName();
            int id = scheduledAction.getId();

            String goalTime = scheduledAction.getGoalTimeAsFormattedString(null);
            String remainingTime = scheduledAction.getRemainingTimeAsHHmmssString();
            String result = scheduledAction.getResult();
            String status = scheduledAction.getStatus();
            List<String> parameters = scheduledAction.getParameters();

            JRadioButton radioButton = new JRadioButton(actionName);
            radioButton.setActionCommand(String.valueOf(id));
            radioButton.setToolTipText(String.format(
                    "%s | %s", actionName, parameters.toString()
            ));

            buttonGroup.add(radioButton);
            //ScheduledActionsUIContainer actionUIContainer = new ScheduledActionsUIContainer(radioButton);

            c.gridx = 0;
            panel.add(radioButton, c);
            c.gridx = 1;
            panel.add(new JLabel(status), c);
            c.gridx = 2;
            panel.add(new JLabel(goalTime), c);
            c.gridx = 3;
            panel.add(new JLabel(remainingTime), c);

            c.gridy = c.gridy + 1;
        }

        selectFirst();
        return panel;
    }

    public void updateScheduledActionsUI(@NotNull List<ScheduledAction> scheduledActions) {
        int selectedActionId = getSelectedScheduledActionId();
        if (selectedActionId > 0) {
            createScheduledActionsUI(scheduledActions);
            selectScheduledActionId(selectedActionId);
        }
    }

    private void selectFirst() {
        AbstractButton button = buttonGroup.getElements().nextElement();
        if (button != null) {
            button.setSelected(true);
        }
    }

    private void selectScheduledActionId(int scheduledActionId) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            String actualId = button.getActionCommand();
            if (actualId != null) {
                try {
                    int id = Integer.parseInt(actualId);
                    if (id == scheduledActionId) {
                        button.setSelected(true);
                        return;
                    }
                } catch (NumberFormatException e) {
                    logger.warn("Unexpected. Button Action Command is not a valid number! {}", actualId);
                }
            }
        }
    }

    public int getSelectedScheduledActionId() {
        if (buttonGroup == null) {
            logger.error("The UI panel has not been generated yet!");
            return Integer.MIN_VALUE;
        }

        if (buttonGroup.getSelection() == null) {
            logger.error("No button selected!");
            return Integer.MIN_VALUE;
        }

        String selectedScheduledActionId = buttonGroup.getSelection().getActionCommand();

        try {
            return Integer.valueOf(selectedScheduledActionId);
        } catch(NumberFormatException e) {
            logger.error("Programmer's error... " +
                    "Selected RadioButton doesn't return integer value of scheduled_action id." +
                    "Instead returns the following value: {}.", selectedScheduledActionId, e);
        }

        return Integer.MIN_VALUE;
    }



}
