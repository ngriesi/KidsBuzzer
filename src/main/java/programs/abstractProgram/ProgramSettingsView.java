package programs.abstractProgram;

import assets.settings.general.SettingsChangeListener;
import assets.standardAssets.MyButton;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MyToggleButton;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.awt.BorderLayout.*;
import static java.awt.GridBagConstraints.BOTH;

/**
 * Parent class for the settings view of the programs. Creates a bar at the bottom with
 * a back button and a bar at the top for multiple settings pages
 */
public abstract class ProgramSettingsView extends ProgramView {

    /**
     * buttons to select the pages
     */
    private MyToggleButton[] layerSelectors;

    /**
     * index of the currently selected page
     */
    private int selectedIndex;

    /**
     * layout of the panel in the center of the layout containing the different pages of the settings
     */
    private CardLayout center;

    /**
     * panel in the center of the layout containing the different pages of the settings
     */
    private JPanel centerPanel;

    /**
     * panel which sits on the bottom of the page and contains the back button
     */
    private JPanel bottomBar;

    /**
     * creates a new view
     *
     * @param actionListener         sets the actionListener of the view
     * @param settingsChangeListener settings change listener for this settings view
     * @param panelNames             names of the panels, displayed in the buttons to select them
     */
    public ProgramSettingsView(ActionListener actionListener, SettingsChangeListener settingsChangeListener, String[] panelNames) {
        super(actionListener);

        this.setLayout(new BorderLayout());


        JPanel[] panels = createPanels(actionListener, settingsChangeListener);


        center = new CardLayout();


        centerPanel = new JPanel(center);
        centerPanel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        int i = 0;
        for (JPanel panel : panels) {
            panel.setBorder(createShadowBorder());
            centerPanel.add(panel, "" + i);
            i++;
        }

        bottomBar = createBottomBar(actionListener);

        this.add(createTopBar(panelNames), PAGE_START);
        this.add(centerPanel, CENTER);
        this.add(bottomBar, PAGE_END);


        center.show(centerPanel, "0");

        selectedIndex = 0;
        layerSelectors[0].setSelected(true);

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    protected abstract JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener);

    /**
     * Method creates the top bar with the buttons to select the pages
     *
     * @param panelNames names of the pages
     * @return The panel of the top bar
     */
    private MyPanel createTopBar(String[] panelNames) {
        MyPanel topBar = new MyPanel(new GridBagLayout());
        topBar.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        topBar.setPreferredSize(new Dimension(10, Toolkit.getDefaultToolkit().getScreenSize().height / 20));
        createTopButtons(panelNames, topBar);
        JPanel thickLine = new JPanel();
        thickLine.setBackground(StandardAssetFields.ROLLOVER_COLOR);
        topBar.addComponent(topBar, thickLine, 1, 5, 0, 1, 1, 0.2f);

        return topBar;
    }

    /**
     * method creates the buttons of the top bar
     *
     * @param panelNames names of the buttons
     * @param topBar     background panel
     */
    private void createTopButtons(String[] panelNames, MyPanel topBar) {
        layerSelectors = new MyToggleButton[panelNames.length];
        for (int i = 0; i < 5; i++) {
            if (i < panelNames.length) {
                layerSelectors[i] = new MyToggleButton(panelNames[i]);
                int finalI = i;
                layerSelectors[i].addActionListener((event) -> selectNewLayer(finalI));
                topBar.addComponent(topBar, layerSelectors[i], 1, 1, i, 0, 1, 1);
            } else {
                topBar.addComponent(topBar, new MyPanel(new GridBagLayout()), 1, 1, i, 0, 1, 1);
            }
        }
    }

    /**
     * action of the page selector buttons
     *
     * @param newIndex index of the pressed button
     */
    private void selectNewLayer(int newIndex) {

        layerSelectors[selectedIndex].setSelected(false);
        layerSelectors[selectedIndex].setEnabled(true);
        layerSelectors[newIndex].setEnabled(false);
        selectedIndex = newIndex;
        center.show(centerPanel, "" + newIndex);
    }

    /**
     * method creates the bottom bar of the layout
     *
     * @param actionListener action listener for the view
     * @return returns the bottom panel of the view
     */
    private JPanel createBottomBar(ActionListener actionListener) {

        JPanel bottomBar = new JPanel(new GridBagLayout());
        bottomBar.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        bottomBar.setPreferredSize(new Dimension(10, Toolkit.getDefaultToolkit().getScreenSize().height / 20));

        //noinspection SpellCheckingInspection
        MyButton back = new MyButton("Zurück");
        back.setActionCommand("back");
        back.addActionListener(actionListener);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0.1f;
        bottomBar.add(back, c);

        c.weightx = 1;
        c.gridx = 1;
        bottomBar.add(new MyPanel(new GridBagLayout()), c);

        return bottomBar;
    }

    /**
     * Method adds a new layer to the settings view
     *
     * @param name  name of the panel displayed in the layer selection button
     * @param panel panel containing the new settings page
     */
    public void createNewSettingsPage(String name, MyPanel panel) {

        this.removeAll();

        this.setLayout(new BorderLayout());

        panel.setBorder(createShadowBorder());
        centerPanel.add(panel, "" + layerSelectors.length);

        String[] layerNames = new String[layerSelectors.length + 1];
        for (int i = 0; i < layerNames.length; i++) {
            if (i < layerSelectors.length) {
                layerNames[i] = layerSelectors[i].getText();
            } else {
                layerNames[i] = name;
            }
        }

        this.add(createTopBar(layerNames), PAGE_START);
        this.add(centerPanel, CENTER);
        this.add(bottomBar, PAGE_END);

        center.show(centerPanel, "0");

        selectedIndex = 0;
        layerSelectors[0].setSelected(true);

        this.setBorder(BorderFactory.createEmptyBorder());
    }
}
