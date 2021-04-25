package assets.combobox;

import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;

/**
 * custom combo box
 *
 * @param <S> type of the objects in the item array
 */
public class MyComboBox<S> extends JComboBox<S> {

    /**
     * creates a custom combo box from an array of items
     *
     * @param items items in the combo box
     */
    public MyComboBox(S[] items) {
        super(items);

        this.setBackground(StandardAssetFields.NORMAL_COLOR);
        this.setPreferredSize(new Dimension(200,40));
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setForeground(new Color(200,200,200));
        this.setFocusable(false);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setRenderer(new MyComboBoxListRenderer(this.getRenderer()));
        this.setOpaque(false);

        this.setUI(new MyComboBoxUi());

    }


}
