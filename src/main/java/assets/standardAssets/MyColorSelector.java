package assets.standardAssets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * color selection button that is painted in the color that is selector has currently selected
 */
public class MyColorSelector extends CleanButton {

    /**
     * creates a new color selector and creates its action listener
     *
     * @param start start color of the color selector button
     */
    public MyColorSelector(Color start) {
        super(start, start, start);
        this.setPreferredSize(new Dimension(40, 40));

        this.addActionListener((e) -> buttonAction());
    }

    /**
     * action performed by the button
     * it opens a color selection dialog, sets the new color to itself and posts an item event to its item listeners
     */
    private void buttonAction() {
        //noinspection SpellCheckingInspection
        Color newColor = JColorChooser.showDialog(null, "Farbe Wählen", getPressedColor());
        if (newColor != null) {
            setColor(newColor);
            for (ItemListener itemListener : getItemListeners()) {
                itemListener.itemStateChanged(new ItemEvent(MyColorSelector.this, 0, null, ItemEvent.SELECTED));
            }
        }
    }

    /**
     * method sets all colors of the button
     *
     * @param color color that gets set to all color fields of the button
     */
    public void setColor(Color color) {
        super.setPressedColor(color);
        super.setNormalColor(color);
        super.setRolloverColor(color);
        setBackground(color);
    }
}
