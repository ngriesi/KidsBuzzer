package assets.combobox;

import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;

/**
 * renderer for the list of the custom combo box
 */
class MyComboBoxListRenderer extends DefaultListCellRenderer {

    /**
     * default renderer
     */
    private ListCellRenderer<Object> defaultRenderer;

    /**
     * creates the renderer
     *
     * @param defaultRenderer default renderer
     */
    MyComboBoxListRenderer(ListCellRenderer defaultRenderer) {
        //noinspection unchecked
        this.defaultRenderer = defaultRenderer;

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * changes the look of the fields of the list cells
     *
     * @param list list of components
     * @param value unused
     * @param index unused
     * @param isSelected true if component gets rolled over
     * @param cellHasFocus true if component has focus
     * @return one list item
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (c instanceof JLabel) {

            if (isSelected) {
                c.setBackground(StandardAssetFields.ROLLOVER_COLOR);
                c.setForeground(StandardAssetFields.FOREGROUND_COLOR);
            } else {
                c.setBackground(StandardAssetFields.NORMAL_COLOR);
            }
        } else {
            c = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
        }
        return c;
    }
}