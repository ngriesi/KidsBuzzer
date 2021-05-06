package startupApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

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
     * Image of the loading view
     */
    private Image image;

    /**
     * creates a new loading view
     *
     * @param loadingModel connected controller
     */
    LoadingView(LoadingModel loadingModel) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        URL imageUrl = getClass().getResource("/team.jpg");//assuming your package name is images
        image = Toolkit.getDefaultToolkit().getImage(imageUrl);


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
        window.setSize(screenSize.width / 3, (int) (screenSize.height / 1.5f));
        window.setLocation(screenSize.width / 3, screenSize.height / 6);
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
        g.fillRect((int)(this.getWidth() * 0.2), (int) (this.getHeight() * 0.25f), (int)(this.getWidth() * 0.6), (int) (this.getHeight() * 0.05));

        g.setColor(Color.RED);

        g.fillArc((int)(getWidth() * 0.1f), 0, (int)(getWidth() * 0.8f), (int)(getHeight() * 0.5f), 0, 180);

        g.setColor(new Color(230,230,230));
        g.setFont(new Font("Broadway",Font.BOLD,90));
        g.drawString("BUZZER", (int)(this.getWidth() * 0.18f),(int)(this.getHeight() * 0.24f));

        g.setColor(new Color(20,20,20,255));
        g.fillRoundRect(0,(int)(getHeight() * 0.3f), getWidth(), (int)(getHeight() * 0.15f),(int)(getHeight() * 0.05f), (int) (getHeight() * 0.05f));

        g.fillRect(0, (int) (this.getHeight() * 0.4f), this.getWidth(), (int) (this.getHeight() * 0.05));


        g.fillRect(0, (int) (this.getHeight() * 0.45f), this.getWidth(), (int) (this.getHeight() * 0.025));

        g.setColor(new Color(230,230,230));
        g.setFont(new Font("Arial",Font.PLAIN,12));
        g.drawString(finishedStep, (int) (this.getWidth() * 0.01), (int) (this.getHeight() * 0.465));

        g.setColor(new Color(20,20,20,100));
        g.fillRect(0, (int) (this.getHeight() * 0.475f), this.getWidth(), (int) (this.getHeight() * 0.025));
        g.setColor(new Color(20, 20, 20, 255));
        g.drawRect(0, (int) (this.getHeight() * 0.475f), this.getWidth() - 1, (int) (this.getHeight() * 0.025));
        g.setColor(new Color(20,20,20,255));
        g.fillRect((int) (this.getWidth() * loadingProgress), (int) (this.getHeight() * 0.475f), (int) (this.getWidth() - this.getWidth() * loadingProgress), (int) (this.getHeight() * 0.025));

        g.drawImage(image,0,(int) (this.getHeight() * 0.5f),this.getWidth(),(int) (this.getHeight() * 0.5f),null);

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
