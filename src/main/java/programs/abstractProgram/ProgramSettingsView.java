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

public abstract class ProgramSettingsView extends ProgramView {

    private MyToggleButton[] layerSelectors;

    private int selectedIndex;

    private CardLayout center;

    private JPanel centerPanel;

    /**
     * creates a new view
     *
     * @param actionListener sets the actionListener of the view
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
            centerPanel.add(panel,"" + i);
            i++;
        }

        this.add(createTopBar(panelNames), PAGE_START);
        this.add(centerPanel, CENTER);
        this.add(createBottomBar(actionListener),PAGE_END);


        center.show(centerPanel,"0");

        selectedIndex = 0;
        layerSelectors[0].setSelected(true);

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    protected abstract JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener);

    private MyPanel createTopBar(String[] panelNames) {
        MyPanel topBar = new MyPanel(new GridBagLayout());
        topBar.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        topBar.setPreferredSize(new Dimension(10,Toolkit.getDefaultToolkit().getScreenSize().height/20));
        createTopButtons(panelNames, topBar);
        JPanel thickLine = new JPanel();
        thickLine.setBackground(StandardAssetFields.ROLLOVER_COLOR);
        topBar.addComponent(topBar,thickLine,1,5,0,1,1,0.2f);

        return topBar;
    }

    private void createTopButtons(String[] panelNames, MyPanel topBar) {
        layerSelectors = new MyToggleButton[panelNames.length];
        for (int i = 0; i < 5; i++) {
            if (i < panelNames.length) {
                layerSelectors[i] = new MyToggleButton(panelNames[i]);
                int finalI = i;
                layerSelectors[i].addActionListener((event) -> selectNewLayer(finalI));
                topBar.addComponent(topBar, layerSelectors[i],1,1, i, 0, 1, 1);
            } else {
                topBar.addComponent(topBar, new MyPanel(new GridBagLayout()),1,1,i,0,1,1);
            }
        }
    }

    private void selectNewLayer(int newIndex) {
        layerSelectors[selectedIndex].setSelected(false);
        layerSelectors[selectedIndex].setEnabled(true);
        layerSelectors[newIndex].setEnabled(false);
        selectedIndex = newIndex;
        center.show(centerPanel,"" + newIndex);
    }

    private JPanel createBottomBar(ActionListener actionListener) {

        JPanel bottomBar = new JPanel(new GridBagLayout());
        bottomBar.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        bottomBar.setPreferredSize(new Dimension(10,Toolkit.getDefaultToolkit().getScreenSize().height/20));

        MyButton back = new MyButton("Zurück");
        back.setActionCommand("back");
        back.addActionListener(actionListener);

        GridBagConstraints c = new GridBagConstraints();
        c.fill  = BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0.1f;
        bottomBar.add(back,c);

        c.weightx = 1;
        c.gridx = 1;
        bottomBar.add(new MyPanel(new GridBagLayout()), c);

        return bottomBar;
    }
}
