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
            setLabelColor((JLabel) c,isSelected);
        } else {
            c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
        return c;
    }

    /**
     * sets the fore and background color for the labels of the combo box
     *
     * @param label label inside the combo box
     * @param isSelected if true, the label is currently selected
     */
    private void setLabelColor(JLabel label, boolean isSelected) {
        label.setForeground(StandardAssetFields.FOREGROUND_COLOR);
        if (isSelected) {
            label.setBackground(StandardAssetFields.ROLLOVER_COLOR);
        } else {
            label.setBackground(StandardAssetFields.NORMAL_COLOR);
        }
    }
}