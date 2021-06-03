package buzzerHandler;

import assets.standardAssets.MyButton;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * class handles the view in witch the virtual representation of the buzzers can be watched
 */
public class BuzzerControlView extends JPanel {

    /**
     * Model of the view
     */
    private BuzzerModel buzzerModel;

    /**
     * Text Label of the buzzers
     */
    private JLabel[] virtualBuzzers;

    /**
     * button at the right bottom corner
     */
    private MyButton button;

    /**
     * creates the Layout of the view
     *
     * @param buzzerModel reference to the model of the view
     */
    BuzzerControlView(BuzzerModel buzzerModel) {
        this.buzzerModel = buzzerModel;

        this.setBackground(new Color(0.3f, 0.3f, 0.3f));
        this.setLayout(new GridLayout(1, buzzerModel.getCount() + 1));

        button = new MyButton("Reset");
        button.addActionListener(this::resetButtonAction);

        virtualBuzzers = new JLabel[buzzerModel.getCount()];

        for (int i = 0; i < virtualBuzzers.length; i++) {
            createVirtualBuzzer(i);
            this.add(virtualBuzzers[i]);
        }

        this.add(button);
    }

    /**
     * creates a virtual buzzer
     *
     * @param i number of the buzzer
     */
    private void createVirtualBuzzer(int i) {
        virtualBuzzers[i] = new JLabel(Text.BUZZER + " " + (i + 1));
        virtualBuzzers[i].setFont(new Font("Yu Gothic Ui", Font.BOLD, 40));
        virtualBuzzers[i].setForeground(Color.WHITE);
        virtualBuzzers[i].setHorizontalAlignment(SwingConstants.CENTER);
        virtualBuzzers[i].setAlignmentX(JLabel.CENTER_ALIGNMENT);
        virtualBuzzers[i].setBackground(SaveDataHandler.BUZZER_COLORS_UNPRESSED[i]);
        virtualBuzzers[i].setOpaque(true);
        virtualBuzzers[i].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buzzerModel.getMainController().getControlModel().getBuzzerPressHandler().handleBuzzerInput(2 + i * 2);
            }
        });
    }

    /**
     * action of the reset Button
     *
     * @param event button event (unused)
     */
    private void resetButtonAction(ActionEvent event) {
        buzzerModel.resetBuzzers();
    }

    /**
     * resets a single buzzer
     *
     * @param number number of the buzzer
     */
    void resetBuzzer(int number) {
        virtualBuzzers[number - 1].setBackground(SaveDataHandler.BUZZER_COLORS_UNPRESSED[number - 1]);
    }

    /**
     * sets the color of one buzzer to the pressed color
     *
     * @param number number of the buzzer that is pressed
     */
    void pressBuzzer(int number) {
        virtualBuzzers[number - 1].setBackground(SaveDataHandler.BUZZER_COLORS_PRESSED[number - 1]);
    }

    /**
     * resets the colors of the buzzers
     */
    void resetBuzzers() {
        for (int i = 0; i < virtualBuzzers.length; i++) {
            virtualBuzzers[i].setBackground(SaveDataHandler.BUZZER_COLORS_UNPRESSED[i]);
        }
    }

    public MyButton getButton() {
        return button;
    }
}
