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

    @Override
    protected InstantButtonControlView createView() {
        return new InstantButtonControlView(this);
    }

    @Override
    protected void updateView() {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            getProgramView().getAudioSettingRows()[i].setSetting(new AudioSettingRow.AudioData(new File(getProgramModel().getSaveFile().getBuzzerSounds()[i]), getProgramModel().getSaveFile().getVolume()[i]/100f));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void settingChanged(SettingsEvent se) {
        if(se.getName().contains("File")) {
            fileSettingsChanged(se);
        } else if (se.getName().contains("Volume")) {
            volumeSettingsChanged(se);
        }
    }

    private void volumeSettingsChanged(SettingsEvent se) {
        int volume = (int) ((float)se.getValue() * 100);
        getProgramModel().getSaveFile().getVolume()[Integer.parseInt(se.getName().split(":")[0])] = volume;
        if (getProgramModel().getAudioClips()[Integer.parseInt(se.getName().split(":")[0])] != null) {
            getProgramModel().getAudioClips()[Integer.parseInt(se.getName().split(":")[0])].setGain(volume / 100.0);
        }


    }

    private void fileSettingsChanged(SettingsEvent se) {
        getProgramModel().getSaveFile().getBuzzerSounds()[Integer.parseInt(se.getName().split(":")[0])] = ((File) se.getValue()).getAbsolutePath();
        new Thread(() -> getProgramModel().getAudioClips()[Integer.parseInt(se.getName().split(":")[0])] = (AudioClip.load((File) se.getValue()))).start();
    }
}
