package startupApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Loading screen window
 */
public class LoadingView extends JPanel {

    /**
     * loading progress
     */
    private float loadingProgress = 0f;

    /**
     * finished step text
     */
    private String finishedStep = "initializeLoading";

    /**
     * loading controller
     */
    private LoadingModel loadingModel;

    /**
     * window that displays the loading
     */
    private JFrame window;

    /**
     * creates a new loading view
     *
     * @param loadingModel connected controller
     */
    LoadingView(LoadingModel loadingModel) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.loadingModel = loadingModel;
        this.setOpaque(false);
        this.setBackground(new Color(0,0,0,0));
        window = new JFrame();
        setupFrame(screenSize);
    }

    /**
     * sets the attributes for the frame
     *
     * @param screenSize size of the screen
     */
    private void setupFrame(Dimension screenSize) {
        window.setSize(screenSize.width / 3, screenSize.height / 3);
        window.setLocation(screenSize.width / 3, screenSize.height / 3);
        window.setUndecorated(true);
        window.getContentPane().add(this);
        window.setBackground(new Color(0,0,0,0));
        window.setVisible(true);

    }

    /**
     * paints the image, the text and the progress bar to the loading window
     *
     * @param g graphic context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect((int)(this.getWidth() * 0.2), (int) (this.getHeight() * 0.5f), (int)(this.getWidth() * 0.6), (int) (this.getHeight() * 0.1));

        g.setColor(Color.RED);

        g.fillArc((int)(getWidth() * 0.1f), 0, (int)(getWidth() * 0.8f), (int)(getHeight() * 1f), 0, 180);

        g.setColor(new Color(230,230,230));
        g.setFont(new Font("Broadway",Font.BOLD,90));
        g.drawString("BUZZER", (int)(this.getWidth() * 0.18f),(int)(this.getHeight() * 0.48f));

        g.setColor(new Color(20,20,20,255));
        g.fillRoundRect(0,(int)(getHeight() * 0.6f), getWidth(), (int)(getHeight() * 0.3f),(int)(getHeight() * 0.1f), (int) (getHeight() * 0.1f));

        g.fillRect(0, (int) (this.getHeight() * 0.8f), this.getWidth(), (int) (this.getHeight() * 0.1));


        g.fillRect(0, (int) (this.getHeight() * 0.9f), this.getWidth(), (int) (this.getHeight() * 0.05));

        g.setColor(new Color(230,230,230));
        g.setFont(new Font("Arial",Font.PLAIN,12));
        g.drawString(finishedStep, (int) (this.getWidth() * 0.01), (int) (this.getHeight() * 0.93));

        g.setColor(new Color(20,20,20,100));
        g.fillRect(0, (int) (this.getHeight() * 0.95f), this.getWidth(), (int) (this.getHeight() * 0.05));
        g.setColor(new Color(20, 20, 20, 255));
        g.drawRect(0, (int) (this.getHeight() * 0.95f) - 1, this.getWidth() - 1, (int) (this.getHeight() * 0.05));
        g.setColor(new Color(20,20,20,255));
        g.fillRect((int) (this.getWidth() * loadingProgress), (int) (this.getHeight() * 0.95f), (int) (this.getWidth() - this.getWidth() * loadingProgress), (int) (this.getHeight() * 0.05));

        loadingModel.updateProgressBar();

        repaint();
    }

    /**
     * updates the progress
     *
     * @param newProgress        new progress value for the bar
     * @param newFinishedActions finished action string
     */
    void updateProgressBar(float newProgress, String newFinishedActions) {
        this.loadingProgress = newProgress;
        if (!newFinishedActions.equals("")) {
            this.finishedStep = newFinishedActions;
        }
    }

    /**
     * closes the window
     */
    void closeWindow() {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
