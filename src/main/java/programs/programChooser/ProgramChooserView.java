package programs.programChooser;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyPanel;
import assets.standardAssets.StandardAssetFields;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.GridBagConstraints.*;

/**
 * View of the program chooser
 */
public class ProgramChooserView extends MyPanel {

    /**
     * left side panel in its expanded state to select programs
     */
    private MyPanel expanded;

    /**
     * creates a new view
     *
     * @param programChooserModel controller of this view
     */
    ProgramChooserView(ProgramChooserModel programChooserModel) {
        super(new GridBagLayout());

        createCollapsedLayout(programChooserModel);

        setupExpandedPanel(programChooserModel);

        JPanel programListBack = createLayoutComponents(programChooserModel);

        createProgramButtons(programChooserModel, programListBack);

    }

    /**
     * creates the layout for when the chooser is collapsed
     *
     * @param programChooserModel action listener
     */
    private void createCollapsedLayout(ProgramChooserModel programChooserModel) {
        MyButton expandButton = new MyButton(">");
        expandButton.addActionListener(e -> programChooserModel.expandButtonAction());
        this.addComponent(this, expandButton, 0, 0, 1, 1, VERTICAL);
        this.setBackground(StandardAssetFields.NORMAL_COLOR);
        this.setVisible(true);
    }

    /**
     * creates the layout components for the expanded layout
     *
     * @param programChooserModel action listener
     * @return panel for the program buttons to be added
     */
    private JPanel createLayoutComponents(ProgramChooserModel programChooserModel) {
        JPanel leftBarBack = createLeftBarBack();

        this.addComponent(expanded, leftBarBack, 1, 2, 0, 0, 0.5f, 1);
        this.addComponent(expanded, createCollapseButton(programChooserModel), 1, 1, 2, 0, 0.02f, 1);
        this.addComponent(expanded, createEmptySpace(), 3, 0, 1, 1);

        JPanel programListBack = createProgramListBack();
        this.addComponent(leftBarBack, programListBack, 1, 2, 0, 0, 0.02f, 1, BOTH, PAGE_START);
        leftBarBack.add(createSettingsButton(programChooserModel), new GridBagConstraints(0, 2, 1, 1, 0.5f, 0.08f, PAGE_END, BOTH, new Insets(0, 10, 10, 5), 0, 0));
        leftBarBack.add(createCreditsButton(programChooserModel), new GridBagConstraints(1, 2, 1, 1, 0.5f, 0.08f, PAGE_END, BOTH, new Insets(0, 5, 10, 10), 0, 0));


        return programListBack;
    }

    /**
     * creates the credits button
     *
     * @param programChooserModel action listener for the button
     * @return credits button
     */
    private MyButton createCreditsButton(ProgramChooserModel programChooserModel) {
        MyButton creditsButton = new MyButton(Text.CREDITS);
        creditsButton.setActionCommand("credits");
        creditsButton.addActionListener(programChooserModel);
        return creditsButton;
    }

    /**
     * creates the program buttons and adds them to a panel
     *
     * @param programChooserModel action listener for the buttons
     * @param programListBack     parent panel for the buttons
     */
    private void createProgramButtons(ProgramChooserModel programChooserModel, JPanel programListBack) {
        int i = 0;
        for (String name : programChooserModel.getProgramHandler().getProgramNamesList()) {
            MyButton program = new MyButton(name);
            programListBack.add(program, new GridBagConstraints(0, i + 1, 2, 1, 1, 0.03f, PAGE_END, BOTH, new Insets(0, 0, 10, 0), 0, 0));
            program.addActionListener((e) -> programChooserModel.programButtonAction(name));
            i++;
        }
    }

    /**
     * creates the settings button
     *
     * @param programChooserModel action listener
     * @return settings button
     */
    private MyButton createSettingsButton(ProgramChooserModel programChooserModel) {
        MyButton settingsButton = new MyButton(Text.SETTINGS);
        settingsButton.setActionCommand("settings");
        settingsButton.addActionListener(programChooserModel);
        return settingsButton;
    }

    /**
     * @return returns the back panel for the list of programs
     */
    private JPanel createProgramListBack() {
        JPanel programListBack = new JPanel(new GridBagLayout());
        programListBack.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        return programListBack;
    }

    /**
     * @return returns an empty panel
     */
    private JPanel createEmptySpace() {
        JPanel emptySpace = new JPanel();
        emptySpace.setOpaque(false);
        return emptySpace;
    }

    /**
     * creates the collapse button
     *
     * @param programChooserModel action listener
     * @return collapse button
     */
    private MyButton createCollapseButton(ProgramChooserModel programChooserModel) {
        MyButton collapseButton = new MyButton("<");
        collapseButton.addActionListener(e -> programChooserModel.collapseButtonAction());
        return collapseButton;
    }

    /**
     * @return returns the back panel of the whole left bar
     */
    private JPanel createLeftBarBack() {
        JPanel leftBarBack = new JPanel(new GridBagLayout());
        leftBarBack.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        leftBarBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        return leftBarBack;
    }

    /**
     * sets up the main panel for the expanded layout
     *
     * @param programChooserModel action listener
     */
    private void setupExpandedPanel(ProgramChooserModel programChooserModel) {
        expanded = new MyPanel(new GridBagLayout());
        expanded.setVisible(false);
        expanded.setOpaque(true);
        expanded.setBackground(new Color(0.3f, 0.3f, 0.3f, 0.5f));
        expanded.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                programChooserModel.collapseButtonAction();
            }
        });
    }

    /**
     * @return returns the main panel of the expanded state
     */
    public MyPanel getExpandedView() {
        return expanded;
    }
}
