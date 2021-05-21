package programs.scoreBoard.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.*;
import assets.standardAssets.MyPanel;
import programs.abstractProgram.ProgramSettingsView;
import programs.scoreBoard.settings.pages.GeneralSettingsPage;
import programs.scoreBoard.settings.pages.ImageSettingsPage;
import programs.scoreBoard.settings.pages.MidiSettingsPage;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * view of the settings of the score board program
 */
public class ScoreBoardSettingsView extends ProgramSettingsView {

    /**
     * Settings page for the general settings
     */
    private GeneralSettingsPage generalSettingsPage;

    /**
     * Settings page for the images
     */
    private ImageSettingsPage imageSettingsPage;

    /**
     * Settings page for the midi controls
     */
    private MidiSettingsPage midiSettingsPage;

    /**
     * creates a new view
     *
     * @param actionListener         sets the actionListener of the view
     * @param settingsChangeListener change listener of the settings
     * @param panelNames             names of the panels
     */
    ScoreBoardSettingsView(ActionListener actionListener, SettingsChangeListener settingsChangeListener, String[] panelNames) {
        super(actionListener, settingsChangeListener, panelNames);
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
        generalSettingsPage = new GeneralSettingsPage(settingsChangeListener);
        imageSettingsPage = new ImageSettingsPage(settingsChangeListener);
        midiSettingsPage = new MidiSettingsPage(settingsChangeListener);
        return new JPanel[]{generalSettingsPage, imageSettingsPage, midiSettingsPage};
    }

    /**
     * @return returns the image settings page containing the settings rows to select
     * the images of the program
     */
    public ImageSettingsPage getImageSettingsPage() {
        return imageSettingsPage;
    }

    /**
     * @return returns the general settings page containing the settings rows to select
     * the general settings of the program
     */
    public GeneralSettingsPage getGeneralSettingsPage() {
        return generalSettingsPage;
    }

    /**
     * @return returns the midi settings page containing the settings rows to select
     * the midi settings of the program
     */
    public MidiSettingsPage getMidiSettingsPage() {
        return midiSettingsPage;
    }

}
