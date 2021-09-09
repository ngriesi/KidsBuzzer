package controlWindow;

import assets.standardAssets.StandardAssetFields;
import assets.standardAssets.UnoptimizedPanel;
import assets.customWindow.MyJFrame;
import controlWindow.settings.SettingsController;
import org.joml.Vector4i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.GridBagConstraints.*;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

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
    public static MyJFrame myJFrame;

    /**
     * main controller of the application
     */
    private MainController mainController;

    /**
     * Constructor creates the Window and sets it up for its first look
     *
     * @param mainController reference to the model of this assets.View
     */
    ControlWindow(final MainController mainController) {

        this.mainController = mainController;


        Vector4i storedWindowBounds = getSavedWindowBounds();

        myJFrame = new MyJFrame(storedWindowBounds.x, storedWindowBounds.y, storedWindowBounds.z, storedWindowBounds.w);
        myJFrame.getFrame().addWindowListener(createWindowListener());


        setupWindow();

        setupInputMap();
        setupActionMap();
    }

    /**
     * creates a window listener for the frame to catch the close event
     *
     * @return returns the listener
     */
    private WindowListener createWindowListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainController.applicationClosing();
            }
        };
    }

    /**
     * gets the bounds of the window from the save file
     *
     * @return returns a Vector4i with the window bounds
     */
    private Vector4i getSavedWindowBounds() {
        Vector4i result = new Vector4i();
        result.x = mainController.getControlModel().getSettingsController().getSettingsSaveFile().getInteger(SettingsController.WINDOW_X);
        result.y = mainController.getControlModel().getSettingsController().getSettingsSaveFile().getInteger(SettingsController.WINDOW_Y);
        result.z = mainController.getControlModel().getSettingsController().getSettingsSaveFile().getInteger(SettingsController.WINDOW_WIDTH);
        result.w = mainController.getControlModel().getSettingsController().getSettingsSaveFile().getInteger(SettingsController.WINDOW_HEIGHT);
        return result;
    }

    /**
     * creates the main layout of the window
     */
    void setupWindow() {
        mainLayout = new UnoptimizedPanel(new GridBagLayout());
        mainLayout.setLayout(new OverlayLayout(mainLayout));
        mainLayout.add(createChooserExpanded());
        mainLayout.add(createChooserCollapsed());

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
     * @return returns the panel
     */
    private JPanel createChooserCollapsed() {
        chooserCollapsed = new JPanel(new GridBagLayout());
        chooserCollapsed.setBackground(StandardAssetFields.NORMAL_COLOR);
        chooserCollapsed.add(mainController.getControlModel().getProgramChooserModel().getProgramChooserView(), createConstraint(0, 0, 2, 0, 1, LINE_START, VERTICAL));
        chooserCollapsed.add(mainController.getControlModel().getBuzzerControl().getView(), createConstraint(1, 1, 1, 1, 0.1f, LAST_LINE_END, BOTH));
        chooserCollapsed.add(createProgramPanel(), createConstraint(1, 0, 1, 1, 1, LAST_LINE_END, BOTH));
        return chooserCollapsed;
    }

    /**
     * creates the panel for the view when the program chooser is expanded
     *
     * @return returns the panel
     */
    private JPanel createChooserExpanded() {
        JPanel chooserExpanded = new JPanel(new GridBagLayout());
        chooserExpanded.setOpaque(false);
        chooserExpanded.setBackground(new Color(0.3f, 0.3f, 0.3f, 0.3f));
        chooserExpanded.add(mainController.getControlModel().getProgramChooserModel().getProgramChooserView().getExpandedView(), createConstraint(0, 0, 2, 1, 1, LINE_START, BOTH));
        return chooserExpanded;
    }

    /**
     * helper method to create a GridBagConstraint
     *
     * @param gridX      x position in the grid (starts with 0)
     * @param gridY      y position in the grid (starts with 0)
     * @param gridHeight height of the item in the grid (in rows)
     * @param weightX    weight of the item in x direction
     * @param weightY    weight of the item in y direction
     * @param anchor     anchor of the item
     * @param fill       filling behaviour of the item in the grid
     * @return a GridBagConstraints
     */
    private GridBagConstraints createConstraint(int gridX, int gridY, int gridHeight, float weightX, float weightY, int anchor, int fill) {
        return new GridBagConstraints(gridX, gridY, 1, gridHeight, weightX, weightY, anchor, fill, new Insets(0, 0, 0, 0), 0, 0);
    }


    /**
     * replaces the current program Panel with a new one
     *
     * @param programPanel new program panel
     */
    void setProgramPane(JPanel programPanel) {
        this.programPanel.setVisible(false);
        this.programPanel = programPanel;
        this.programPanel.setVisible(true);
        chooserCollapsed.add(programPanel, createConstraint(1, 0, 1, 1, 8, LAST_LINE_END, BOTH));
    }

    /**
     * changes the view of the main panel of the frame
     *
     * @param panel new content of the window
     */
    public void setView(JPanel panel) {
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

    /**
     * sets up the action map for the program panel (program selection)
     */
    private void setupActionMap() {
        chooserCollapsed.getActionMap().put("1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToProgram(0);
            }
        });
        chooserCollapsed.getActionMap().put("2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToProgram(1);
            }
        });
        chooserCollapsed.getActionMap().put("3", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToProgram(2);
            }
        });
        chooserCollapsed.getActionMap().put("4", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToProgram(3);
            }
        });
        chooserCollapsed.getActionMap().put("5", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToProgram(4);
            }
        });
        chooserCollapsed.getActionMap().put("6", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToProgram(5);
            }
        });
    }

    /**
     * switches to the program with the given number and updates the window by
     * calling <code>JFrame.setVisible(true)</code>
     *
     * @param number program number
     */
    private void switchToProgram(int number) {
        mainController.getControlModel().getProgramChooserModel().programButtonAction(number);
        myJFrame.getFrame().setVisible(true);
    }

    /**
     * sets up the input map of this panel
     */
    private void setupInputMap() {
        chooserCollapsed.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control 1"), "1");
        chooserCollapsed.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control 2"), "2");
        chooserCollapsed.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control 3"), "3");
        chooserCollapsed.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control 4"), "4");
        chooserCollapsed.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control 5"), "5");
        chooserCollapsed.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control 6"), "6");
    }
}
