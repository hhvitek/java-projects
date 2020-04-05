package gui_swing;

import actions.ActionAbstract;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ChooseActionsUI {

    private JPanel panel = new JPanel();
    private ButtonGroup buttonGroup = new ButtonGroup();
    private Map<String, ChooseActionUIContainer> actionsUiContainer = new HashMap<>();

    public ChooseActionsUI() {
        panel.setLayout(
                new GridLayout(0, 1)
        );
    }

    public JPanel createActionsUI(List<ActionAbstract> actions) {

        for (ActionAbstract action: actions) {
            String actionName = action.getName();
            String actionDescription = action.getDescription();
            int parametersCount = action.parametersCount();

            JRadioButton radioButton = new JRadioButton(actionName);
            radioButton.setActionCommand(actionName);
            radioButton.setToolTipText(actionDescription);

            buttonGroup.add(radioButton);
            ChooseActionUIContainer actionContainer = new ChooseActionUIContainer(radioButton);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(
                    new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS)
            );
            buttonPanel.add(radioButton);

            for(int i = 0; i < parametersCount; i++) {
                JTextField textField = new JTextField();
                buttonPanel.add(textField);
                actionContainer.addParameterTextField(textField);
            }

            actionsUiContainer.put(actionName, actionContainer);
            panel.add(buttonPanel);
        }

        selectFirst();
        return panel;
    }

    public String getSelectedActionName() {
        String selectedActionName = buttonGroup.getSelection().getActionCommand();
        return selectedActionName;
}

    public List<String> getSelectedParameters() {
        String actionName = getSelectedActionName();
        if (actionsUiContainer.containsKey(actionName)) {
            ChooseActionUIContainer actionContainer = actionsUiContainer.get(actionName);
            return actionContainer.getParametersValues();
        }

        return Collections.emptyList();
    }

    private void selectFirst() {
        AbstractButton button = buttonGroup.getElements().nextElement();
        if (button != null) {
            button.setSelected(true);
        }
    }



}
