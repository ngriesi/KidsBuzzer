package controlWindow.credits;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * view of the credits
 */
class CreditsView extends MyPanel {

    /**
     * creates the credits view
     *
     * @param creditsController <code>ActionListener</code> for all components of this view
     */
    CreditsView(ActionListener creditsController) {
        super(new BorderLayout());

        createMainText();

        createBackButton(creditsController);
    }

    /**
     * creates the back button to return from the credits to the normal view
     *
     * @param creditsController <code>ActionListener</code> of the button (<code>ActionCommand</code>: "back")
     */
    private void createBackButton(ActionListener creditsController) {
        //noinspection SpellCheckingInspection
        MyButton back = new MyButton("Zurück");
        back.addActionListener(creditsController);
        back.setActionCommand("back");
        this.add(back, BorderLayout.PAGE_END);
    }

    /**
     * creates the <code>MyLabel</code> of the <code>CreditsView</code>
     */
    private void createMainText() {
        //noinspection SpellCheckingInspection
        MyLabel mainLabel = new MyLabel("Bei Problemen und Fragen an " +
                "niels.griesu@gmail.com schreiben");
        this.add(mainLabel, BorderLayout.CENTER);
    }
}
