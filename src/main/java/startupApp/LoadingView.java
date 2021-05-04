package startupApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Loading screen window
 */
public class LoadingView extends JPanel {

    /**
     * loading screen background
     */
    private BufferedImage image;

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
     * @param image background image
     * @param loadingModel connected controller
     */
    LoadingView(BufferedImage image, LoadingModel loadingModel) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.loadingModel = loadingModel;

        this.image = image;

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
        window.setLocation(screenSize.width/3, screenSize.height/3);
        window.setUndecorated(true);
        window.getContentPane().add(this);
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

        g.drawImage(image, 0, 0,this.getWidth(),this.getHeight(), this);
        g.drawString(finishedStep,(int)(this.getWidth() * 0.01),(int)(this.getHeight() * 0.94));
        g.fillRect(0,(int)(this.getHeight() * 0.95), (int) (this.getWidth() * loadingProgress), (int)(this.getHeight() * 0.02));

        loadingModel.updateProgressBar();

        repaint();
    }

    /**
     * updates the progress
     *
     * @param newProgress new progress value for the bar
     * @param newFinishedActions finished action string
     */
    void updateProgressBar(float newProgress, String newFinishedActions) {
        this.loadingProgress = newProgress;
        if(!newFinishedActions.equals("")) {
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
