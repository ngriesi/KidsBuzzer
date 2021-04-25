package assets.control;

import assets.combobox.MyComboBox;
import assets.standardAssets.*;
import savedataHandler.SaveDataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static programs.mouseClicker.control.view.MouseClickerProgramControlView.*;
import static savedataHandler.SaveDataHandler.BUZZER_NAMES;

public class BlockingBehaviourRow extends ControlViewRow {

    public static final String BLOCKING_BEHAVIOUR = "blockingBehaviour", RESET = "reset", BLOCK_TIME = "blockTime",
            BUZZER_SELECTOR = "buzzerSelector", MAIN_SELECTOR = "mainSelector";

    private MyComboBox<String> mainSelector;

    private MyTextField blockTime;

    private MyCheckBox[] buzzer;

    private MyButton releaseButton;

    private JPanel buzzerChooserBack;

    public BlockingBehaviourRow(ActionListener actionListener) {
        super("Blocking behaviour");

        createLayoutElements(actionListener);

        this.add(createCenterPanel(), BorderLayout.LINE_START);
        this.add(createInteractionElementContainer(), BorderLayout.LINE_END);
    }

    private void createLayoutElements(ActionListener actionListener) {
        createMainSelector(actionListener);
        createReleaseButton(actionListener);
        createBlockTime(actionListener);
        createBuzzerChooserBack();
        createBuzzerButtons(actionListener);
    }

    private JPanel createCenterPanel() {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);


        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, spacing, 0, spacing * 3);
        c.gridx = 0;
        center.add(new MyLabel("Blocking Behaviour"), c);
        c.gridx = 1;
        center.add(mainSelector, c);

        return center;
    }

    private JPanel createInteractionElementContainer() {
        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, spacing);
        c.gridx = 0;
        interactionElementsContainer.add(releaseButton, c);
        interactionElementsContainer.add(blockTime, c);
        interactionElementsContainer.add(buzzerChooserBack, c);

        return interactionElementsContainer;
    }

    private void createBuzzerChooserBack() {
        buzzerChooserBack = new JPanel();
        buzzerChooserBack.setBackground(StandardAssetFields.NORMAL_COLOR);
        buzzerChooserBack.setVisible(false);
    }

    private void createBuzzerButtons(ActionListener actionListener) {
        buzzer = new MyCheckBox[3];
        for (int i = 0; i < 3; i++) {
            buzzer[i] = createBuzzerButton(i,actionListener);
            buzzerChooserBack.add(buzzer[i]);
        }
    }

    private MyCheckBox createBuzzerButton(int i, ActionListener actionListener) {
        MyCheckBox buzzer = new MyCheckBox();
        buzzer.setBorder(BorderFactory.createEmptyBorder());
        buzzer.setSelectedColor(SaveDataHandler.BUZZER_COLORS_PRESSED[i]);
        buzzer.setPressedColor(SaveDataHandler.BUZZER_COLORS_UNPRESSED[i]);
        buzzer.setActionCommand(BLOCKING_BEHAVIOUR + ":" + BUZZER_SELECTOR +":"+ BUZZER_NAMES[i]);
        buzzer.addActionListener(actionListener);
        return buzzer;
    }

    private void createBlockTime(ActionListener actionListener) {
        blockTime = new MyTextField("1");
        blockTime.setActionCommand(BLOCKING_BEHAVIOUR + ":" + BLOCK_TIME);
        blockTime.addActionListener(actionListener);
        blockTime.setVisible(false);
    }

    private void createReleaseButton(ActionListener actionListener) {
        releaseButton = new MyButton("Reset Buzzer");
        releaseButton.setActionCommand(BLOCKING_BEHAVIOUR + ":" + RESET);
        releaseButton.addActionListener(actionListener);
        releaseButton.setVisible(false);
    }

    private void createMainSelector(ActionListener actionListener) {
        mainSelector = new MyComboBox<>(new String[]{"Don't block", "Block until Released", "Block for Time", "Unblock with Buzzer"});
        mainSelector.setFont(new Font("arial", Font.PLAIN, 20));
        mainSelector.setPreferredSize(new Dimension(250, 40));
        mainSelector.setActionCommand(BLOCKING_BEHAVIOUR + ":" + MAIN_SELECTOR);
        mainSelector.addActionListener(actionListener);
    }

    public void buzzerAction(String buzzerName) {
        for (int i = 0; i < 3; i++) {
            buzzer[i].setSelected(buzzerName.equals(BUZZER_NAMES[i]));
            buzzer[i].setEnabled(!buzzerName.equals(BUZZER_NAMES[i]));
        }
    }

    public void setMainSelectorItem(String item) {
        mainSelector.setSelectedItem(item);
    }

    public int getBlockTime() {
        try {
            return Integer.parseInt(blockTime.getText());
        } catch (NumberFormatException n) {
            return 0;
        }
    }

    public void setBlockTime(int blockTime) {
        this.blockTime.setText(String.valueOf(blockTime));
    }

    public void setUnblockBuzzer(int buzzerNumber) {
        buzzer[buzzerNumber - 1].setSelected(true);
    }

    public void changeBlockingBehaviour(String behaviour) {
        releaseButton.setVisible(behaviour.equals("Block until Released"));
        blockTime.setVisible(behaviour.equals("Block for Time"));
        buzzerChooserBack.setVisible(behaviour.equals("Unblock with Buzzer"));
    }
}
