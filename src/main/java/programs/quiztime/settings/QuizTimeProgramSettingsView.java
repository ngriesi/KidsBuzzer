package programs.quiztime.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.*;
import assets.standardAssets.MyPanel;
import assets.standardAssets.StandardAssetFields;
import programs.abstractProgram.ProgramSettingsView;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * view of the settings of the quiz time program
 */
class QuizTimeProgramSettingsView extends ProgramSettingsView {

    /**
     * Settings row to select the image for the background
     */
    private FileChooserSettingsRow presentationBackground;

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
    private AudioSettingRow introSound, questionSound, rightSound, buzzerSound, wrongSound;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    QuizTimeProgramSettingsView(QuizTimeProgramSettingsController programController) {
        super(programController, programController, new String[]{Text.IMAGES, Text.FONT, Text.AUDIO});
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

        introSound = new AudioSettingRow(settingsChangeListener, "soundIntro", Text.INTRO_SOUND);

        panel.addComponent(panel, introSound, 0, 0, 1, 1);

        questionSound = new AudioSettingRow(settingsChangeListener, "soundQuestion", Text.QUESTION_SOUND);

        panel.addComponent(panel, questionSound, 0, 1, 1, 1);

        rightSound = new AudioSettingRow(settingsChangeListener, "soundRight", Text.RIGHT_SOUND);

        panel.addComponent(panel, rightSound, 0, 2, 1, 1);

        buzzerSound = new AudioSettingRow(settingsChangeListener, "soundBuzzer", Text.BUZZER_SOUND);

        panel.addComponent(panel, buzzerSound, 0, 3, 1, 1);

        wrongSound = new AudioSettingRow(settingsChangeListener, "soundWrong", Text.WRONG_SOUND);

        panel.addComponent(panel, wrongSound, 0, 4, 1, 1);

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

        mainFontChooserRow = new FontChooserRow(settingsChangeListener, "fontmain", Text.MAIN_FONT, new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        panel.addComponent(panel, mainFontChooserRow, 0, 0, 1, 1);

        buzzerFontChooserRow = new FontChooserRow(settingsChangeListener, "fontbuzzer", Text.BUZZER_FONT, new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

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

        presentationBackground = new FileChooserSettingsRow(settingsChangeListener, "background", Text.CHOOSE_BACKGROUND, new File("default"), Text.IMAGES, "png", "jpg");

        panel.addComponent(panel, presentationBackground, 0, 0, 1, 1);

        icons = new FileChooserSettingsRow[SaveDataHandler.BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            icons[i] = new FileChooserSettingsRow(settingsChangeListener, "icon" + i,  Text.SELECT_ICON_BUZZER+ " " + (i + 1), new File("default"), Text.IMAGES, "png", "jpg");

            panel.addComponent(panel, icons[i], 0, i + 1, 1, 1);
        }

        panel.addComponent(panel, new EmptySettingsRow(), 0, SaveDataHandler.BUZZER_COUNT + 1, 1, 1);

        return panel;
    }

    /**
     * @return returns the settings row for the background
     */
    FileChooserSettingsRow getPresentationBackground() {
        return presentationBackground;
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

    /**
     * @return returns the settings row for the intro sound
     */
    AudioSettingRow getIntroSound() {
        return introSound;
    }

}
