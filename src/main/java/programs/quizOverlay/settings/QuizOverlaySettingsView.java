package programs.quizOverlay.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.*;
import assets.standardAssets.MyPanel;
import assets.standardAssets.StandardAssetFields;
import programs.abstractProgram.ProgramSettingsView;
import savedataHandler.SaveDataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * view of the settings of the quiz time program
 */
class QuizOverlaySettingsView extends ProgramSettingsView {

    /**
     * Settings rows to select the images for the icons
     */
    private FileChooserSettingsRow[] icons;

    /**
     * Settings row to layout teh main font
     */
    private FontChooserRow mainFontChooserRow;

    /**
     * settings row to layout the buzzer font
     */
    private FontChooserRow buzzerFontChooserRow;

    /**
     * settings rows to select the audio file and change their volume
     */
    private AudioSettingRow questionSound, rightSound, buzzerSound, wrongSound;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    QuizOverlaySettingsView(QuizOverlaySettingsController programController) {
        super(programController, programController, new String[]{"Bilder", "Schrift", "Audio"});
    }

    /**
     * Method creates the panel that a re the different pages of the settings
     *
     * @param actionListener         <code>ActionListener</code> of the view
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return an array of panels representing the pages of the settings
     */
    @Override
    protected JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener) {
        return new JPanel[]{createImageSelectionView(settingsChangeListener), createFontSelectionView(settingsChangeListener), createAudioSelectionView(settingsChangeListener)};
    }

    /**
     * creates the audio settings rows
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return a panel containing the <code>AudioSettingsRows</code>
     */
    private JPanel createAudioSelectionView(SettingsChangeListener settingsChangeListener) {

        MyPanel panel = new MyPanel(new GridBagLayout());


        questionSound = new AudioSettingRow(settingsChangeListener, "soundQuestion", "Frage Sound");

        panel.addComponent(panel, questionSound, 0, 0, 1, 1);

        rightSound = new AudioSettingRow(settingsChangeListener, "soundRight", "Richtig Sound");

        panel.addComponent(panel, rightSound, 0, 1, 1, 1);

        buzzerSound = new AudioSettingRow(settingsChangeListener, "soundBuzzer", "Buzzer Sound");

        panel.addComponent(panel, buzzerSound, 0, 2, 1, 1);

        wrongSound = new AudioSettingRow(settingsChangeListener, "soundWrong", "Falsch Sound");

        panel.addComponent(panel, wrongSound, 0, 3, 1, 1);

        return panel;
    }

    /**
     * creates the <code>FontChooserRows</code>
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return a panel containing the <code>FontChooserRows</code>
     */
    private JPanel createFontSelectionView(SettingsChangeListener settingsChangeListener) {

        MyPanel panel = new MyPanel(new GridBagLayout());

        mainFontChooserRow = new FontChooserRow(settingsChangeListener, "fontmain", "Haupschriftart", new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        panel.addComponent(panel, mainFontChooserRow, 0, 0, 1, 1);

        buzzerFontChooserRow = new FontChooserRow(settingsChangeListener, "fontbuzzer", "Buzzerschriftart", new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        panel.addComponent(panel, buzzerFontChooserRow, 0, 1, 1, 1);

        for (int i = 2; i < 5; i++) {
            panel.addComponent(panel, new EmptySettingsRow(), 0, i, 1, 1);
        }

        return panel;
    }

    /**
     * creates the settings rows for the image settings
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return a panel containing the settings rows for the images
     */
    private JPanel createImageSelectionView(SettingsChangeListener settingsChangeListener) {
        MyPanel panel = new MyPanel(new GridBagLayout());

        panel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        icons = new FileChooserSettingsRow[SaveDataHandler.BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            icons[i] = new FileChooserSettingsRow(settingsChangeListener, "icon" + i, "Icon für Buzzer " + (i + 1) + " auswählen", new File("default"), "Bilder", "png", "jpg");

            panel.addComponent(panel, icons[i], 0, i, 1, 1);
        }

        panel.addComponent(panel, new EmptySettingsRow(), 0, SaveDataHandler.BUZZER_COUNT, 1, 1);
        panel.addComponent(panel, new EmptySettingsRow(), 0, SaveDataHandler.BUZZER_COUNT + 1, 1, 1);

        return panel;
    }

    /**
     * @return returns the array of <code>FileChooserSettingsRows</code> for the icons
     */
    FileChooserSettingsRow[] getIcons() {
        return icons;
    }

    /**
     * @return returns the settings row that sets up the main font
     */
    FontChooserRow getMainFontChooserRow() {
        return mainFontChooserRow;
    }

    /**
     * @return returns the settings row that sets up the buzzer font
     */
    FontChooserRow getBuzzerFontChooserRow() {
        return buzzerFontChooserRow;
    }

    /**
     * @return returns the settings row for the question sound
     */
    AudioSettingRow getQuestionSound() {
        return questionSound;
    }

    /**
     * @return returns the settings row for the right sound
     */
    AudioSettingRow getRightSound() {
        return rightSound;
    }

    /**
     * @return returns the settings row for the buzzer sound
     */
    AudioSettingRow getBuzzerSound() {
        return buzzerSound;
    }

    /**
     * @return returns the settings row for the wrong sound
     */
    AudioSettingRow getWrongSound() {
        return wrongSound;
    }
}
