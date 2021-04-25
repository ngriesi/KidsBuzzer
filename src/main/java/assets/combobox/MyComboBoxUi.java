package assets.combobox;

import assets.standardAssets.StandardAssetFields;
import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

/**
 * ui of the custom combo box to switch out the arrow button
 */
public class MyComboBoxUi extends BasicComboBoxUI {



    /**
     * @return returns a custom arrow button
     */
    @Override
    protected JButton createArrowButton() {
        return new MyComboBoxExpandButton();
    }

    /**
     * changes the drawing of the background of the combo box
     *
     * @param g graphic context
     * @param bounds bounds of the combo box
     * @param hasFocus focus of the combo box
     */
    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        Color t = g.getColor();
        if ( comboBox.isEnabled() )
            g.setColor(StandardAssetFields.NORMAL_COLOR);
        else
            g.setColor(DefaultLookup.getColor(comboBox, this,
                    "ComboBox.disabledBackground", null));
        g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
        g.setColor(t);
    }
}
