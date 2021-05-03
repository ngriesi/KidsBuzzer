package programs.quiztime.settings;

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
class QuizTimeProgramSettingsView extends ProgramSettingsView {

    private FileChooserSettingsRow presentationBackground;

    private FileChooserSettingsRow[] icons;

    private FontChooserRow mainFontChooserRow;

    private FontChooserRow buzzerFontChooserRow;

    private AudioSettingRow introSound, questionSound, rightSound, buzzerSound, wrongSound;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    QuizTimeProgramSettingsView(QuizTimeProgramSettingsController programController) {
        super(programController, programController, new String[] { "Bilder" , "Schrift", "Audio"});
    }

    @Override
    protected JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener) {
        return new JPanel[]{createImageSelectionView(settingsChangeListener), createFontSelectionView(settingsChangeListener), createAudioSelectionView(settingsChangeListener)};
    }

    private JPanel createAudioSelectionView(SettingsChangeListener settingsChangeListener) {

        MyPanel panel = new MyPanel(new GridBagLayout());

        introSound = new AudioSettingRow(settingsChangeListener, "soundIntro", "Intro Sound");

        panel.addComponent(panel, introSound, 0, 0,1,1);

        questionSound = new AudioSettingRow(settingsChangeListener, "soundQuestion", "Frage Sound");

        panel.addComponent(panel, questionSound, 0, 1,1,1);

        rightSound = new AudioSettingRow(settingsChangeListener, "soundRight", "Richtig Sound");

        panel.addComponent(panel, rightSound, 0, 2,1,1);

        buzzerSound = new AudioSettingRow(settingsChangeListener, "soundBuzzer", "Buzzer Sound");

        panel.addComponent(panel, buzzerSound, 0, 3, 1, 1);

        wrongSound = new AudioSettingRow(settingsChangeListener, "soundWrong", "Falsch Sound");

        panel.addComponent(panel, wrongSound, 0, 4, 1, 1);

        return panel;
    }

    private JPanel createFontSelectionView(SettingsChangeListener settingsChangeListener) {

        MyPanel panel = new MyPanel(new GridBagLayout());

        mainFontChooserRow = new FontChooserRow(settingsChangeListener, "fontmain", "Haupschriftart", new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        panel.addComponent(panel, mainFontChooserRow, 0, 0,1,1);

        buzzerFontChooserRow = new FontChooserRow(settingsChangeListener, "fontbuzzer", "Buzzerschriftart", new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));
        
        panel.addComponent(panel, buzzerFontChooserRow, 0, 1,1,1);

        for (int i = 2; i < 5; i++) {
            panel.addComponent(panel,new EmptySettingsRow(),0,i,1,1);
        }

        return panel;
    }

    private JPanel createImageSelectionView(SettingsChangeListener settingsChangeListener) {
        MyPanel panel = new MyPanel(new GridBagLayout());

        panel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        presentationBackground = new FileChooserSettingsRow(settingsChangeListener,"background","Hintergrung Auswählen", new File("default"),"Bilder","png","jpg");

        panel.addComponent(panel, presentationBackground,0,0,1,1);

        icons = new FileChooserSettingsRow[SaveDataHandler.BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            icons[i] = new FileChooserSettingsRow(settingsChangeListener, "icon" + i, "Icon für Buzzer " + (i + 1) + " auswählen", new File("default"), "Bilder", "png", "jpg");

            panel.addComponent(panel,icons[i],0,i + 1,1,1);
        }

        panel.addComponent(panel,new EmptySettingsRow(),0,SaveDataHandler.BUZZER_COUNT + 1,1,1);

        return panel;
    }

    FileChooserSettingsRow getPresentationBackground() {
        return presentationBackground;
    }

    FileChooserSettingsRow[] getIcons() {
        return icons;
    }

    FontChooserRow getMainFontChooserRow() {
        return mainFontChooserRow;
    }

    FontChooserRow getBuzzerFontChooserRow() {
        return buzzerFontChooserRow;
    }

    AudioSettingRow getIntroSound() {
        return introSound;
    }

    AudioSettingRow getQuestionSound() {
        return questionSound;
    }

    AudioSettingRow getRightSound() {
        return rightSound;
    }

    AudioSettingRow getBuzzerSound() {
        return buzzerSound;
    }

    AudioSettingRow getWrongSound() {
        return wrongSound;
    }
}
