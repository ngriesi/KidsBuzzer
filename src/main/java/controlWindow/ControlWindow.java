package controlWindow;

import assets.standardAssets.StandardAssetFields;
import assets.standardAssets.UnoptimizedPanel;
import assets.customWindow.MyJFrame;
import org.joml.Vector4i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.GridBagConstraints.*;

/**
 * Window to control the Presentation and to Monitor the Buzzers
 */
public class ControlWindow {

    /**
     * unoptimized panel containing the overlay layout
     */
    private UnoptimizedPanel mainLayout;

    /**
     * program panel containing the control view of the current program
     */
    private JPanel programPanel;

    /**
     * background panel when the program chooser is collapsed
     */
    private JPanel chooserCollapsed;

    /**
     * custom JFrame
     */
    private MyJFrame myJFrame;

    /**
     * Constructor creates the Window and sets it up for its first look
     *
     * @param controlModel reference to the model of this assets.View
     */
    ControlWindow(final ControlModel controlModel) {

        Vector4i storedWindowBounds = getSavedWindowBounds(controlModel);

        myJFrame = new MyJFrame(storedWindowBounds.x, storedWindowBounds.y, storedWindowBounds.z, storedWindowBounds.w);
        myJFrame.getFrame().addWindowListener(createWindowListener(controlModel));

        setupWindow(controlModel);
    }

    /**
     * creates a window listener for the frame to catch the close event
     *
     * @param controlModel model of the control frame
     * @return returns the listener
     */
    private WindowListener createWindowListener(ControlModel controlModel) {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controlModel.applicationClosing();
            }
        };
    }

    /**
     * gets the bounds of the window from the save file
     *
     * @param controlModel model of the frame
     * @return returns a Vector4i with the window bounds
     */
    private Vector4i getSavedWindowBounds(ControlModel controlModel) {
        Vector4i result = new Vector4i();
        result.x = controlModel.getSettingsController().getSettingsSaveFile().getWindowPositionX();
        result.y = controlModel.getSettingsController().getSettingsSaveFile().getWindowPositionY();
        result.z = controlModel.getSettingsController().getSettingsSaveFile().getWindowWidth();
        result.w = controlModel.getSettingsController().getSettingsSaveFile().getWindowHeight();
        return result;
    }

    /**
     * creates the main layout of the window
     *
     * @param controlModel main model of the control window
     */
    private void setupWindow(ControlModel controlModel) {
        mainLayout = new UnoptimizedPanel(new GridBagLayout());
        mainLayout.setLayout(new OverlayLayout(mainLayout));
        mainLayout.add(createChooserExpanded(controlModel));
        mainLayout.add(createChooserCollapsed(controlModel));

        myJFrame.setView(mainLayout);
    }

    /**
     * creates a panel as placeholder for the control view of the currently selected program
     * (should never be visible)
     *
     * @return returns an empty panel
     */
    private JPanel createProgramPanel() {
        programPanel = new JPanel(new GridBagLayout());
        programPanel.setBackground(new Color(255, 0, 0));
        programPanel.setVisible(true);
        return programPanel;
    }

    /**
     * creates the panel for the view when the program chooser is collapsed
     *
     * @param controlModel main model of the control frame
     * @return returns the panel
     */
    private JPanel createChooserCollapsed(ControlModel controlModel) {
        chooserCollapsed = new JPanel(new GridBagLayout());
        chooserCollapsed.setBackground(StandardAssetFields.NORMAL_COLOR);
        chooserCollapsed.add(controlModel.getProgramChooserModel().getProgramChooserView(),createConstraint(0,0, 2,0,1,LINE_START,VERTICAL));
        chooserCollapsed.add(controlModel.getBuzzerControl().getView(),createConstraint(1,1, 1,1,0.1f,LAST_LINE_END,BOTH));
        chooserCollapsed.add(createProgramPanel(),createConstraint(1,0, 1,1,1,LAST_LINE_END,BOTH));
        return chooserCollapsed;
    }

    /**
     * creates the panel for the view when the program chooser is expanded
     *
     * @param controlModel main model of the control frame
     * @return returns the panel
     */
    private JPanel createChooserExpanded(ControlModel controlModel) {
        JPanel chooserExpanded = new JPanel(new GridBagLayout());
        chooserExpanded.setOpaque(false);
        chooserExpanded.setBackground(new Color(0.3f,0.3f,0.3f,0.3f));
        chooserExpanded.add(controlModel.getProgramChooserModel().getProgramChooserView().getExpandedView(),createConstraint(0,0, 2,1,1,LINE_START,BOTH));
        return chooserExpanded;
    }

    /**
     * helper method to create a GridBagConstraint
     *
     * @param gridX x position in the grid (starts with 0)
     * @param gridY y position in the grid (starts with 0)
     * @param gridHeight height of the item in the grid (in rows)
     * @param weightX weight of the item in x direction
     * @param weightY weight of the item in y direction
     * @param anchor anchor of the item
     * @param fill filling behaviour of the item in the grid
     * @return a GridBagConstraints
     */
    private GridBagConstraints createConstraint(int gridX, int gridY, int gridHeight, float weightX, float weightY, int anchor, int fill) {
        return new GridBagConstraints(gridX,gridY, 1,gridHeight,weightX,weightY,anchor,fill, new Insets(0, 0, 0, 0), 0, 0);
    }


    /**
     * replaces the current program Panel with a new one
     *
     * @param programPanel new program panel
     */
    void setProgramPane(JPanel programPanel) {
        chooserCollapsed.remove(this.programPanel);
        this.programPanel = programPanel;
        chooserCollapsed.add(programPanel,createConstraint(1,0,1,1,8,LAST_LINE_END,BOTH));
    }

    /**
     * changes the view of the main panel of the frame
     *
     * @param panel new content of the window
     */
    void setView(JPanel panel) {
        myJFrame.setView(panel);
    }

    /**
     * @return returns the background panel of the view when the program chooser is collapsed
     */
    JPanel getChooserCollapsed() {
        return chooserCollapsed;
    }

    /**
     * @return returns the main layout of the control view
     */
    JPanel getMainLayout() {
        return mainLayout;
    }

    /**
     * @return returns the Frame this view is placed inside
     */
    public MyJFrame getMyJFrame() {
        return myJFrame;
    }
}
