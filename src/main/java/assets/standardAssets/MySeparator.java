package assets.standardAssets;

import javax.swing.*;

/**
 * creates a blue separator
 */
public class MySeparator extends JSeparator {

    /**
     * creates a blue separator
     */
    public MySeparator() {
        super(JSeparator.HORIZONTAL);
        this.setForeground(StandardAssetFields.ROLLOVER_COLOR);
        this.setBackground(StandardAssetFields.ROLLOVER_COLOR);
    }
}
