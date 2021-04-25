package programs.mouseClicker.control;

import assets.standardAssets.MyLabel;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;

public class MouseTrackerWindow extends JPanel {

    private JFrame window;

    private MyLabel xCord;

    private MyLabel yCord;

    public MouseTrackerWindow() {
        super(new BorderLayout());
        window = new JFrame();
        window.setUndecorated(true);
        window.setBackground(StandardAssetFields.NORMAL_COLOR);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screenSize.width/10,screenSize.height/20);

        this.setBackground(StandardAssetFields.NORMAL_COLOR);

        xCord = new MyLabel("x: 0");
        yCord = new MyLabel("y: 0");

        this.add(xCord,BorderLayout.LINE_START);
        this.add(yCord, BorderLayout.LINE_END);

        window.setContentPane(this);
    }

    public void setWindowVisibility(boolean visibility) {
        window.setVisible(visibility);
        listenForMousePosition();
    }

    private void listenForMousePosition() {
        new Thread(() -> {
            while (window.isVisible()) {
                PointerInfo inf = MouseInfo.getPointerInfo();
                Point p = inf.getLocation();
                SwingUtilities.invokeLater(() -> {
                    xCord.setText("x: "+p.x);
                    yCord.setText("y: "+p.y);
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
