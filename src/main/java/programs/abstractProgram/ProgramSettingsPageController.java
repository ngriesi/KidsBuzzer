package programs.abstractProgram;

import assets.settings.general.SettingsEvent;

public abstract class ProgramSettingsPageController<C extends ProgramController>{

    protected C mainSettingsController;

    public ProgramSettingsPageController(C mainSettingsController) {
        this.mainSettingsController = mainSettingsController;
    }

    public abstract void settingChangedAction(SettingsEvent event);

    public abstract void updateView();
}
