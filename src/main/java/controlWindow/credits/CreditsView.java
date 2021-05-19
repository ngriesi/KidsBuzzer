package controlWindow.credits;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;
import savedataHandler.languages.Text;

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
        MyButton back = new MyButton(Text.BACK);
        back.addActionListener(creditsController);
        back.setActionCommand("back");
        this.add(back, BorderLayout.PAGE_END);
    }

    /**
     * creates the <code>MyLabel</code> of the <code>CreditsView</code>
     */
    private void createMainText() {
        //noinspection SpellCheckingInspection
        MyLabel mainLabel = new MyLabel(Text.CREDITS_TEXT);
        this.add(mainLabel, BorderLayout.CENTER);
    }
}
