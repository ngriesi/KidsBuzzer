package programs.instantButton.control;

import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.EmptySettingsRow;
import programs.abstractProgram.ProgramControllerView;
import savedataHandler.SaveDataHandler;

/**
 * view of the instant button controls consisting out of three <code>AudioSettingRows</code>
 */
class InstantButtonControlView extends ProgramControllerView<InstantButtonController> {

    /**
     * settings rows to change the audio files and their volumes
     */
    private AudioSettingRow[] audioSettingRows;

    /**
     * creates a new view
     *
     * @param controller sets the actionListener of the view
     */
    InstantButtonControlView(InstantButtonController controller) {
        super(controller);

        audioSettingRows = new AudioSettingRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            audioSettingRows[i] = new AudioSettingRow(controller,i+":","Sound von Buzzer " + (i + 1));
            this.addComponent(this, audioSettingRows[i],0,i,1,1);
        }

        this.addComponent(this, new EmptySettingsRow(),0,SaveDataHandler.MAX_BUZZER_COUNT, 1,1);
        this.addComponent(this, new EmptySettingsRow(),0,SaveDataHandler.MAX_BUZZER_COUNT + 1, 1,1);


    }

    /**
     * @return returns the audio settings rows of this view, used to update the view
     */
    AudioSettingRow[] getAudioSettingRows() {
        return audioSettingRows;
    }
}
