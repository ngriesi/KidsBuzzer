package assets.standardAssets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyColorSelector extends CleanButton {

    public MyColorSelector(Color start) {
        super(start,start,start);
        this.setPreferredSize(new Dimension(40,40));

        this.addActionListener((e) -> {
            Color newColor = JColorChooser.showDialog(null, "Farbe Wählen", getPressedColor());
            if (newColor != null) {
                setColor(newColor);
                for (ItemListener itemListener : getItemListeners()) {
                    itemListener.itemStateChanged(new ItemEvent(MyColorSelector.this,0,null, ItemEvent.SELECTED));
                }
            }
        });
    }

    public void setColor(Color color) {
        super.setPressedColor(color);
        super.setNormalColor(color);
        super.setRolloverColor(color);
        setBackground(color);
    }
}
