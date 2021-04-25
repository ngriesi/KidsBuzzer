package assets.control;

import assets.standardAssets.MyLabel;
import assets.standardAssets.MySeparator;
import assets.standardAssets.StandardAssetFields;
import programs.mouseClicker.control.view.MouseClickerProgramControlView;

import javax.swing.*;
import java.awt.*;

public abstract class ControlViewRow extends JPanel {

    public static int spacing = 10;

    public ControlViewRow(String description) {
        super(new BorderLayout());
        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        MySeparator separator = new MySeparator();
        this.add(separator, BorderLayout.PAGE_END);

        MyLabel label = new MyLabel(description);
        label.setBorder(BorderFactory.createLineBorder(StandardAssetFields.PANEL_BACKGROUND_COLOR, spacing));
        this.add(label, BorderLayout.LINE_START);
    }
}
