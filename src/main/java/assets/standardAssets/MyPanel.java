package assets.standardAssets;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

/**
 * JPanel with methods to add components to a grid bag layout
 */
@SuppressWarnings({"unused", "SameParameterValue", "WeakerAccess"})
public class MyPanel extends JPanel {

    /**
     * creates Panel with Layout
     *
     * @param layoutManager layout for the panel
     */
    public MyPanel(LayoutManager layoutManager) {
        super(layoutManager);
        setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
    }

    /**
     * adds a component with a grid bag constraint
     *
     * @param parent parent the component gets added to
     * @param child child that gets added to the component
     * @param gridHeight grid height of the child in rows
     * @param gridWidth grid width of the child in columns
     * @param gridX grid x position of the child (in rows, starts with 0)
     * @param gridY grid y position of the child (in columns, starts with 0)
     * @param weightX weight of the child in x direction
     * @param weightY weight of the child in y direction
     * @param fill filling behaviour of the component
     * @param anchor anchor of the component
     */
    protected void addComponent(JPanel parent, JComponent child, int gridHeight, int gridWidth, int gridX, int gridY, float weightX, float weightY, int fill, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.anchor = CENTER;
        c.weighty = weightY;
        c.weightx = weightX;
        c.gridwidth = gridWidth;
        c.gridheight = gridHeight;
        c.gridy = gridY;
        c.gridx = gridX;
        parent.add(child, c);
    }

    /**
     * adds a component with a grid bag constraint
     *
     * @param parent parent the component gets added to
     * @param child child that gets added to the component
     * @param gridHeight grid height of the child in rows
     * @param gridWidth grid width of the child in columns
     * @param gridX grid x position of the child (in rows, starts with 0)
     * @param gridY grid y position of the child (in columns, starts with 0)
     * @param weightX weight of the child in x direction
     * @param weightY weight of the child in y direction
     * @param fill filling behaviour of the component
     */
    protected void addComponent(JPanel parent, JComponent child, int gridHeight, int gridWidth, int gridX, int gridY, float weightX, float weightY, int fill) {
        addComponent(parent, child, gridHeight, gridWidth, gridX, gridY, weightX, weightY,fill,CENTER);
    }

    /**
     * adds a component with a grid bag constraint
     *
     * @param parent parent the component gets added to
     * @param child child that gets added to the component
     * @param gridHeight grid height of the child in rows
     * @param gridWidth grid width of the child in columns
     * @param gridX grid x position of the child (in rows, starts with 0)
     * @param gridY grid y position of the child (in columns, starts with 0)
     * @param weightX weight of the child in x direction
     * @param weightY weight of the child in y direction
     */
    public void addComponent(JPanel parent, JComponent child, int gridHeight, int gridWidth, int gridX, int gridY, float weightX, float weightY) {
        addComponent(parent, child, gridHeight, gridWidth, gridX, gridY, weightX, weightY, BOTH);
    }

    /**
     * adds a component with a grid bag constraint
     *
     * @param parent parent the component gets added to
     * @param child child that gets added to the component
     * @param gridX grid x position of the child (in rows, starts with 0)
     * @param gridY grid y position of the child (in columns, starts with 0)
     * @param weightX weight of the child in x direction
     * @param weightY weight of the child in y direction
     */
    public void addComponent(JPanel parent, JComponent child, int gridX, int gridY, float weightX, float weightY) {
        addComponent(parent, child, 1, 1, gridX, gridY, weightX, weightY, BOTH);
    }

    /**
     * adds a component with a grid bag constraint
     *
     * @param parent parent the component gets added to
     * @param child child that gets added to the component
     * @param gridX grid x position of the child (in rows, starts with 0)
     * @param gridY grid y position of the child (in columns, starts with 0)
     * @param weightX weight of the child in x direction
     * @param weightY weight of the child in y direction
     * @param fill filling behaviour of the component
     */
    protected void addComponent(JPanel parent, JComponent child, int gridX, int gridY, float weightX, float weightY,int fill) {
        addComponent(parent, child, 1, 1, gridX, gridY, weightX, weightY, fill);
    }

    /**
     * adds a component with a grid bag constraint
     *
     * @param parent parent the component gets added to
     * @param child child that gets added to the component
     * @param gridX grid x position of the child (in rows, starts with 0)
     * @param gridY grid y position of the child (in columns, starts with 0)
     * @param weightX weight of the child in x direction
     * @param weightY weight of the child in y direction
     * @param fill filling behaviour of the component
     * @param anchor of the component
     */
    protected void addComponent(JPanel parent, JComponent child, int gridX, int gridY, float weightX, float weightY,int fill,int anchor) {
        addComponent(parent, child, 1, 1, gridX, gridY, weightX, weightY, fill,anchor);
    }
}
