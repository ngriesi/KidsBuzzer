package programs.scoreBoard.control;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MyTextField;
import programs.abstractProgram.ProgramView;
import savedataHandler.SaveDataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ScoreBoardControlView extends ProgramView {

    private MyButton show,hide;

    private MyTextField[] scores;

    private MyLabel[] teamNames;

    /**
     * creates a new view
     *
     * @param actionListener sets the actionListener of the view
     */
    ScoreBoardControlView(ActionListener actionListener) {
        super(actionListener);

        MyPanel topBar = new MyPanel(new GridBagLayout());

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),"show");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("V"),"hide");

        this.getActionMap().put("show", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(new ActionEvent(show,
                        ActionEvent.ACTION_PERFORMED,
                        "show"));
            }
        });

        this.getActionMap().put("hide", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(new ActionEvent(hide,
                        ActionEvent.ACTION_PERFORMED,
                        "hide"));
            }
        });

        show = new MyButton("Anzeigen");
        show.setActionCommand("show");
        show.addActionListener(actionListener);
        this.addComponent(topBar,show,0,0,1,1, GridBagConstraints.NONE);

        hide = new MyButton("Verstecken");
        hide.setActionCommand("hide");
        hide.addActionListener(actionListener);
        this.addComponent(topBar,hide,1,0,1,1, GridBagConstraints.NONE);

        MyButton settings = new MyButton("Einstellungen");
        settings.setActionCommand("settings");
        settings.addActionListener(actionListener);
        this.addComponent(topBar,settings,2,0,1,1, GridBagConstraints.NONE);

        this.addComponent(this,topBar,1,2,0,0,1f,1f);

        createTextFields(actionListener);
    }

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

    public MyButton getShow() {
        return show;
    }

    public MyButton getHide() {
        return hide;
    }

    public MyLabel[] getTeamNames() {
        return teamNames;
    }

    public MyTextField[] getScore() {
        return scores;
    }
}
