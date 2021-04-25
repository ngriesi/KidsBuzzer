package assets.combobox;

import assets.standardAssets.CleanButton;
import assets.standardAssets.StandardAssetFields;

import java.awt.*;

/**
 * custom arrow button for the custom combo box
 */
public class MyComboBoxExpandButton extends CleanButton {

    /**
     * empty constructor
     */
    MyComboBoxExpandButton() {}

    /**
     * draws the triangle arrow
     *
     * @param g graphic context of the button
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(StandardAssetFields.FOREGROUND_COLOR);

        int[] xPoints = {width/3,width/3 * 2,width/2};
        int[] yPoints = {height / 8 * 3, height / 8 * 3, height / 8 * 5};

        g.fillPolygon(xPoints,yPoints,3);
    }
}
