package gui_swing;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChooseActionUIContainer {

    private List<JTextField> parameterTextFields = new ArrayList<>();
    private JRadioButton radioButton;
    private String actionName;

    public ChooseActionUIContainer(JRadioButton radioButton) {

        this.radioButton = radioButton;
        this.actionName = radioButton.getActionCommand();
    }

    public void addParameterTextField(JTextField textField) {
        parameterTextFields.add(textField);
    }

    public void clearTextFields() {
        parameterTextFields.clear();
    }

    public List<String> getParametersValues() {
        return parameterTextFields.stream()
                .map(JTextField::getText)
                .collect(Collectors.toList());
    }
}
