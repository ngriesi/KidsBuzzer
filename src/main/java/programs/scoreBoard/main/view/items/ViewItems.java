package programs.scoreBoard.main.view.items;

import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.ImageItem;
import presentationWindow.renderItems.MainItem;
import presentationWindow.renderItems.QuadItem;
import presentationWindow.renderItems.TextItem;
import programs.scoreBoard.data.ScoreBoardModel;
import savedataHandler.SaveDataHandler;

import java.awt.*;

/**
 * Class containing all the items of the sore board output view
 */
public class ViewItems {

    /**
     * Background <code>MetalQuads</code>
     */
    private MetalQuad[] teamMetalBacks;

    /**
     * Text Backgrounds
     */
    private QuadItem[] teamYellowBacks;

    /**
     * icons of the teams
     */
    private ImageItem[] icons;

    /**
     * Labels with the scores and team names
     */
    private TextItem[] labels;

    /**
     * Model of the score board program
     */
    private ScoreBoardModel scoreBoardModel;

    /**
     * size of the text
     */
    private float textSize = 0.11f;

    /**
     * constructor sets the reference to the program model
     *
     * @param scoreBoardModel program model
     */
    public ViewItems(ScoreBoardModel scoreBoardModel) {
        this.scoreBoardModel = scoreBoardModel;
    }

    /**
     * sets up the output view of the program by creating all the items inside it
     *
     * @param mainItem main item of the output window
     */
    public void setupView(MainItem mainItem) {

        teamMetalBacks = new MetalQuad[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            teamMetalBacks[i] = new MetalQuad();
            teamMetalBacks[i].setPosition(1 / (SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i), 1.5f);
            teamMetalBacks[i].setSize(0.3f, 0.3f);
            mainItem.addItem(teamMetalBacks[i]);
        }

        icons = new ImageItem[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            icons[i] = new ImageItem(scoreBoardModel.getIcons()[i]);
            icons[i].setPosition(1 / (SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i), 1.5f);
            icons[i].setSize((icons[i].getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
            teamMetalBacks[i].addItem(icons[i]);
        }

        Color darkYellow = new Color(0.98f, 0.82f, 0.243f, 0.7f);
        Color lightYellow = new Color(0.98f, 0.859f, 0.463f, 0.7f);

        teamYellowBacks = new QuadItem[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            teamYellowBacks[i] = new QuadItem();
            teamYellowBacks[i].setPosition(-0.1f, 0.95f);
            teamYellowBacks[i].setSize(0.1f, 0.1f);
            teamYellowBacks[i].setColorScheme(new ColorScheme(darkYellow, lightYellow, Color.TRANSPARENT, Color.TRANSPARENT));
            teamYellowBacks[i].setUseColorShade(true);
            icons[i].addItem(teamYellowBacks[i]);
        }

        labels = new TextItem[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            labels[i] = new TextItem(scoreBoardModel.getSaveFile().getTeamNames()[i] + ": " + 0, new Font(scoreBoardModel.getSaveFile().getFont(), scoreBoardModel.getSaveFile().isTextBold() ? Font.BOLD : Font.PLAIN, 200));
            labels[i].setPosition(1 / (SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i), 0.95f);
            labels[i].setSize((labels[i].getAspectRatio() * textSize) / Window.WINDOW_ASPECT_RATIO, textSize);
            labels[i].setOpacity(0);
            labels[i].setColorScheme(new ColorScheme(new Color(scoreBoardModel.getSaveFile().getTextColor())));
            teamYellowBacks[i].addItem(labels[i]);
        }

    }

    /**
     * @return returns the <code>MetalQuad</code> items of the view
     */
    public MetalQuad[] getTeamMetalBacks() {
        return teamMetalBacks;
    }

    /**
     * @return returns the text background items of the view
     */
    public QuadItem[] getTeamYellowBacks() {
        return teamYellowBacks;
    }

    /**
     * @return returns the icon image items of the view
     */
    public ImageItem[] getIcons() {
        return icons;
    }

    /**
     * @return returns the text items of the view
     */
    public TextItem[] getLabels() {
        return labels;
    }

    /**
     * @return returns the score board model
     */
    public ScoreBoardModel getScoreBoardModel() {
        return scoreBoardModel;
    }

    /**
     * updates the font in the view
     */
    public void updateFont() {
        for (TextItem label : labels) {

            Font font = new Font(scoreBoardModel.getSaveFile().getFont(), scoreBoardModel.getSaveFile().isTextBold() ? Font.BOLD : Font.PLAIN, 200);
            Color color = new Color(scoreBoardModel.getSaveFile().getTextColor());

            label.setColorScheme(new ColorScheme(color));
            label.changeFont(font);
        }
    }

    /**
     * updates the text in the view
     */
    public void updateText() {
        for (int i = 0; i < labels.length; i++) {
            labels[i].changeText(scoreBoardModel.getSaveFile().getTeamNames()[i] + ": " + scoreBoardModel.getScores()[i]);
            labels[i].setSize((labels[i].getAspectRatio() * textSize) / Window.WINDOW_ASPECT_RATIO, textSize);
        }
    }

    /**
     * updates the icon of one of the buzzers
     *
     * @param buzzerNumber the buzzer of which the icon gets updated
     */
    public void updateIcon(int buzzerNumber) {
        icons[buzzerNumber].setTexture(scoreBoardModel.getIcons()[buzzerNumber]);
    }
}
