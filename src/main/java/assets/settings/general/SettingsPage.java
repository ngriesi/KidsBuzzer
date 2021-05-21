package assets.settings.general;

import assets.settings.rows.EmptySettingsRow;
import assets.settings.rows.SettingsRow;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MySeparator;
import assets.standardAssets.MySlider;
import assets.standardAssets.StandardAssetFields;

import java.awt.*;
import java.util.ArrayList;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

/**
 * One page inside a settings view
 */
public abstract class SettingsPage extends MyPanel {

    /**
     * Displayed name of the page
     */
    private String pageName;

    /**
     * Identification name of the page
     */
    private String identificationName;

    /**
     * List of settings rows in this view
     */
    private ArrayList<SettingsRow> settingsRows;

    /**
     * creates Panel with Layout
     *
     * @param pageName           Displayed name of the page
     * @param identificationName Identification name of the page
     */
    public SettingsPage(String pageName, String identificationName) {
        super(new GridBagLayout());
        settingsRows = new ArrayList<>();
        this.pageName = pageName;
        this.identificationName = identificationName;
    }

    /**
     * Add a row to the page
     */
    public void addRow(SettingsRow settingsRow) {
        settingsRow.setPageIdentificationName(identificationName);
        if(settingsRows.size() > 0) {
            this.addComponent(this, new MySeparator(), 1, 1, 0, settingsRows.size() + (settingsRows.size() - 1), 1f, 0.05f, BOTH, CENTER);
        }
        this.addComponent(this, settingsRow, 1, 1, 0, settingsRows.size() + (settingsRows.size()), 1f, 1f, BOTH, CENTER);
        settingsRows.add(settingsRow);
    }

    /**
     * @return returns the displayed name of the page
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * @return returns the identification name of the page
     */
    public String getIdentificationName() {
        return identificationName;
    }

    /**
     * Adds an empty settings row
     */
    protected void addEmpty() {
        EmptySettingsRow settingsRow = new EmptySettingsRow();
        MySeparator separator = new MySeparator();
        separator.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        separator.setForeground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        this.addComponent(this, separator, 1, 1, 0, settingsRows.size() + (settingsRows.size() - 1), 1f, 0.05f, BOTH, CENTER);
        this.addComponent(this, settingsRow, 1, 1, 0, settingsRows.size() + (settingsRows.size()), 1f, 0.05f, BOTH, CENTER);
        settingsRows.add(settingsRow);
    }
}
