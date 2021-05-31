package programs.instantButton.control;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import programs.abstractProgram.ProgramController;
import programs.instantButton.data.InstantButtonModel;
import programs.instantButton.main.InstantButtonProgram;
import savedataHandler.SaveDataHandler;
import utils.audioSystem.AudioClip;

import java.awt.event.ActionEvent;
import java.io.File;

import static programs.instantButton.data.InstantButtonModel.BUZZER_SOUND;

/**
 * Controller of the control view of the instant button program used to set the settings of this program
 * (change the audio files or the volume)
 */
public class InstantButtonController extends ProgramController<InstantButtonProgram, InstantButtonControlView, InstantButtonModel> implements SettingsChangeListener {

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public InstantButtonController(InstantButtonProgram program, InstantButtonModel programModel) {
        super(program, programModel);
    }

    /**
     * creates the view of this controller
     *
     * @return returns a new <code>InstantButtonControlView</code>
     */
    @Override
    protected InstantButtonControlView createView() {
        return new InstantButtonControlView(this);
    }

    /**
     * updates the view with the data from the programs save file
     */
    @Override
    protected void updateView() {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            getProgramView().getAudioSettingRows()[i].setSetting(getProgramModel().getSaveFile().getAudioData(BUZZER_SOUND + i));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * method called when a setting has changed
     *
     * @param se settings event created by the settings view element, containing a name to identify the setting
     *           and the new value of the setting
     */
    @Override
    public void settingChanged(SettingsEvent se) {
        AudioSettingRow.AudioData audioData = (AudioSettingRow.AudioData) se.getValue();
        getProgramModel().getSaveFile().putAudioData(BUZZER_SOUND + Integer.parseInt(se.getName()), audioData);
        if (se.getComponentName().equals("File")) {
            new Thread(() -> getProgramModel().getAudioClips()[Integer.parseInt(se.getName())] = (AudioClip.load(audioData.getFile().getAbsoluteFile()))).start();
        } else if (se.getComponentName().equals("Volume")) {
            if (getProgramModel().getAudioClips()[Integer.parseInt(se.getName())] != null) {
                getProgramModel().getAudioClips()[Integer.parseInt(se.getName())].setGain(audioData.getVolume());
            }
        }
    }
}
