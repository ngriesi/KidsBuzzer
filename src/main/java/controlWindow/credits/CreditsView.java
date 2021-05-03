package controlWindow.credits;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;

import java.awt.*;
import java.awt.event.ActionListener;

class CreditsView extends MyPanel {


    CreditsView(ActionListener creditsController) {
        super(new BorderLayout());

        MyLabel mainLabel = new MyLabel("Bei Problemen und Fragen an " +
                "niels.griesu@gmail.com schreiben");
        this.add(mainLabel, BorderLayout.CENTER);

        MyButton back = new MyButton("Zurück");
        back.addActionListener(creditsController);
        back.setActionCommand("back");
        this.add(back, BorderLayout.PAGE_END);
    }
}
