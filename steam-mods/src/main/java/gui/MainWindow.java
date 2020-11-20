package gui;

import model.AppModel;
import model.ModificationModel;
import model.exceptions.ModificationException;
import model.exceptions.UnknownChain;
import model.persistent.exceptions.ConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class MainWindow {
    private JPanel panelForm;
    private JPanel panelProgram;
    private JPanel panelStatusBar;
    private JScrollPane scrollPaneStatusBar;
    private JLabel labelStatusBar;
    private JPanel panelChooseModFolder;
    private JTextField textFieldChooseModFolder;
    private JButton buttonChooseModFolder;
    private JButton buttonStart;
    private JButton buttonExit;
    private JPanel panelActions;
    private JPanel panelChooseModificationChain;
    private JComboBox comboBoxChooseModificationChain;
    private JButton buttonSave;

    private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);

    // main frame contains gui-generated panel
    private final JFrame frameMain;

    private final ModificationModel modificationModel;
    private final AppModel appModel;


    public MainWindow(@NotNull AppModel appModel, @NotNull ModificationModel modificationModel) {
        this.modificationModel = modificationModel;
        this.appModel = appModel;

        frameMain = new JFrame("Program");
        frameMain.setContentPane(panelForm);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
            Swing: if any text contained in JLabel overflow its border... it looks bad...
            JLabel has to be contained in JScrollPane and this should be contained in JPanel...
            JScrollPanel disable all borders...
         */
        scrollPaneStatusBar.setViewportBorder(null);
        scrollPaneStatusBar.setBorder(null);

        buttonChooseModFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Optional<Path> optionalFile = SwingViewUtils.showFileChooserAndGetFolder(null);
                optionalFile.ifPresent(
                        chosenFile ->  {
                            appModel.setChosenModFolder(chosenFile);
                            setChosenModFolder();
                            updateStatusBar("The new folder selected!.");
                        }
                );
            }
        });
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedChainId = appModel.getSelectedModificationsChainId();
                    Path chosenModFolder = appModel.getChosenModFolder();

                    modificationModel.executeChain(selectedChainId, chosenModFolder);
                    updateStatusBar("Chain: <" + selectedChainId + "> executed successfully.");
                } catch (UnknownChain | ModificationException | IOException ex) {
                    SwingViewUtils.showErrorMessageDialog(frameMain, "Failed to execute chain. \n\n" + ex.toString());
                }
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    appModel.save();
                    modificationModel.save();
                    updateStatusBar("Save success!");
                } catch (IOException | ConfigurationException ex) {
                    SwingViewUtils.showErrorMessageDialog(
                            frameMain,
                            "Failed to save data!!!\n\n" + ex.toString()
                    );
                    updateStatusBar("Save failed!!!");
                }


            }
        });
        comboBoxChooseModificationChain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = comboBoxChooseModificationChain.getSelectedItem();
                if (Objects.nonNull(selectedItem)) {
                    appModel.setSelectedModificationsChain(selectedItem.toString());
                    updateStatusBar("The new chain selected!.");
                }
            }
        });
    }

    private void updateStatusBar(String message) {
        labelStatusBar.setText(message);
    }

    public void startView() {
        SwingViewUtils.runAndShowWindow(frameMain);

        try {
            setDefaultStateAfterInitialization();
        } catch (IOException | ConfigurationException e) {
            SwingViewUtils.showErrorMessageDialog(frameMain, "Error loading data from storage: " + e.toString());
        }
    }

    private void setDefaultStateAfterInitialization() throws IOException, ConfigurationException {
        modificationModel.load();
        appModel.load();

        fillUpdatedComboBoxChooseModificationsChainValues();
        selectComboBoxSelectedValue();
        setChosenModFolder();
    }

    private void fillUpdatedComboBoxChooseModificationsChainValues() {
        modificationModel.getAllModificationsChains().forEach(
                chain -> comboBoxChooseModificationChain.addItem(chain.getId())
        );
    }

    private void selectComboBoxSelectedValue() {
        String selectedModificationsChainId = appModel.getSelectedModificationsChainId();
        comboBoxChooseModificationChain.setSelectedItem(selectedModificationsChainId);
    }

    private void setChosenModFolder() {
        textFieldChooseModFolder.setText(appModel.getChosenModFolder().toString());

    }

}
