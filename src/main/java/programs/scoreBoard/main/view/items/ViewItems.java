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

public class ViewItems {


    private MetalQuad[] teamMetalBacks;

    private QuadItem[] teamYellowBacks;

    private ImageItem[] icons;

    private TextItem[] labels;

    private ScoreBoardModel scoreBoardModel;

    public ViewItems(ScoreBoardModel scoreBoardModel) {
        this.scoreBoardModel = scoreBoardModel;
    }

    public void setupView(MainItem mainItem) {

        teamMetalBacks = new MetalQuad[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            teamMetalBacks[i] = new MetalQuad();
            teamMetalBacks[i].setPosition(1 / (SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i), 1.5f);
            teamMetalBacks[i].setSize(0.3f,0.3f);
            mainItem.addItem(teamMetalBacks[i]);
        }

        icons = new ImageItem[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            icons[i] = new ImageItem(scoreBoardModel.getIcons()[i]);
            icons[i].setPosition(1 / (SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i), 1.5f);
            icons[i].setSize((icons[i].getAspectRatio() * 0.3f)/ Window.WINDOW_ASPECT_RATIO,0.3f);
            teamMetalBacks[i].addItem(icons[i]);
        }

        Color darkYellow = new Color(0.98f, 0.82f, 0.243f, 0.7f);
        Color lightYellow = new Color(0.98f, 0.859f, 0.463f, 0.7f);

        teamYellowBacks = new QuadItem[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            teamYellowBacks[i] = new QuadItem();
            teamYellowBacks[i].setPosition(-0.1f, 0.95f);
            teamYellowBacks[i].setSize(0.1f,0.1f);
            teamYellowBacks[i].setColorScheme(new ColorScheme(darkYellow, lightYellow, Color.TRANSPARENT,Color.TRANSPARENT));
            teamYellowBacks[i].setUseColorShade(true);
            icons[i].addItem(teamYellowBacks[i]);
        }

        labels = new TextItem[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            labels[i] = new TextItem(scoreBoardModel.getSaveFile().getTeamNames()[i] + ": " + 0, new Font(scoreBoardModel.getSaveFile().getFont(), scoreBoardModel.getSaveFile().isTextBold() ? Font.BOLD : Font.PLAIN, 200));
            labels[i].setPosition(1 / (SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i), 0.95f);
            labels[i].setSize((labels[i].getAspectRatio() * 0.07f)/Window.WINDOW_ASPECT_RATIO,0.07f);
            labels[i].setOpacity(0);
            labels[i].setColorScheme(new ColorScheme(new Color(scoreBoardModel.getSaveFile().getTextColor())));
            teamYellowBacks[i].addItem(labels[i]);
        }

    }

    public MetalQuad[] getTeamMetalBacks() {
        return teamMetalBacks;
    }

    public QuadItem[] getTeamYellowBacks() {
        return teamYellowBacks;
    }

    public ImageItem[] getIcons() {
        return icons;
    }

    public TextItem[] getLabels() {
        return labels;
    }

    public ScoreBoardModel getScoreBoardModel() {
        return scoreBoardModel;
    }

    public void updateFont() {
        for (TextItem label : labels) {

            Font font = new Font(scoreBoardModel.getSaveFile().getFont(), scoreBoardModel.getSaveFile().isTextBold() ? Font.BOLD : Font.PLAIN, 200);
            Color color = new Color(scoreBoardModel.getSaveFile().getTextColor());

            label.setColorScheme(new ColorScheme(color));
            label.changeFont(font);
        }
    }

    public void updateText() {
        for (int i = 0; i < labels.length;i++) {
            labels[i].changeText(scoreBoardModel.getSaveFile().getTeamNames()[i] + ": " + scoreBoardModel.getScores()[i]);
            labels[i].setSize((labels[i].getAspectRatio() * 0.07f)/Window.WINDOW_ASPECT_RATIO,0.07f);
        }
    }

    public void updateIcon(int buzzerNumber) {
        icons[buzzerNumber].setTexture(scoreBoardModel.getIcons()[buzzerNumber]);
    }
}
