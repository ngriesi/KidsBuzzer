package programs.scoreBoard.control;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MyTextField;
import programs.abstractProgram.ProgramView;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * control view of the score board program
 */
class ScoreBoardControlView extends ProgramView {

    /**
     * show and hide buttons to show or hide the output window
     */
    private MyButton show, hide;

    /**
     * <code>TextFields</code> containing the current scores of the teams.
     * They can be used to change the scores
     */
    private MyTextField[] scores;

    /**
     * Labels containing the names of the teams
     */
    private MyLabel[] teamNames;

    /**
     * creates a new view
     *
     * @param actionListener sets the actionListener of the view
     */
    ScoreBoardControlView(ActionListener actionListener) {
        super(actionListener);

        MyPanel topBar = new MyPanel(new GridBagLayout());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "show");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("V"), "hide");

        this.getActionMap().put("show", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(new ActionEvent(show, ActionEvent.ACTION_PERFORMED, "show"));
            }
        });

        this.getActionMap().put("hide", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(new ActionEvent(hide, ActionEvent.ACTION_PERFORMED, "hide"));
            }
        });

        show = createButton(actionListener, topBar, Text.SHOW, "show", 0);

        hide = createButton(actionListener, topBar, Text.HIDE, "hide", 1);

        createButton(actionListener, topBar, Text.SETTINGS, "settings", 2);

        this.addComponent(this, topBar, 1, 2, 0, 0, 1f, 1f);

        createTextFields(actionListener);
    }

    /**
     * creates a button of the top bar
     *
     * @param actionListener <code>ActionListener</code> of al components in this view
     * @param topBar         parent component of the created button
     * @param text           text that is displayed on the button
     * @param actionCommand  action command of the button used to identify the action in the action performed
     *                       method of the <code>ActionListener</code>
     * @param gridX          x position of the button in the <code>GridBagLayout</code>
     * @return returns the build button
     */
    private MyButton createButton(ActionListener actionListener, MyPanel topBar, String text, String actionCommand, int gridX) {
        MyButton button = new MyButton(text);
        button.setActionCommand(actionCommand);
        button.addActionListener(actionListener);
        this.addComponent(topBar, button, gridX, 0, 1, 1, GridBagConstraints.NONE);
        return button;
    }

    /**
     * Method creates the labels containing the team names and the
     * <code>TextFields</code> containing their scores
     *
     * @param actionListener <code>ActionListener</code> for all components in this view
     */
    private void createTextFields(ActionListener actionListener) {
        teamNames = new MyLabel[SaveDataHandler.MAX_BUZZER_COUNT];
        scores = new MyTextField[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            teamNames[i] = new MyLabel("default");
            this.addComponent(this, teamNames[i], 0, i + 1, 1, 1);

            scores[i] = new MyTextField("0");
            scores[i].addActionListener(actionListener);
            scores[i].setActionCommand("score" + i);
            scores[i].useOnlyInts();
            this.addComponent(this, scores[i], 1, i + 1, 1, 1, GridBagConstraints.NONE);
        }
    }

    /**
     * @return returns the show button of the view
     */
    public MyButton getShow() {
        return show;
    }

    /**
     * @return returns the hide button of the view
     */
    public MyButton getHide() {
        return hide;
    }

    /**
     * @return returns the array of the labels displaying the team names
     */
    MyLabel[] getTeamNames() {
        return teamNames;
    }

    /**
     * @return returns the array of the <code>TextFields</code> displaying the team scores
     */
    MyTextField[] getScore() {
        return scores;
    }
}
