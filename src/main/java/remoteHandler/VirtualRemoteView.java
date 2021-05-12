package remoteHandler;

import assets.standardAssets.MyPanel;
import assets.standardAssets.StandardAssetFields;

import java.awt.*;

/**
 * class represents a panel that draws the virtual
 * representation of a remote to the screen
 */
class VirtualRemoteView extends MyPanel {

    /**
     * creates Panel with Layout
     */
    VirtualRemoteView() {
        super(new FlowLayout());
    }

    /**
     * Paint method of the panel
     *
     * @param g graphics context of this panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(StandardAssetFields.FOREGROUND_COLOR);

        g.drawLine(0, height/10, width/5, height/10);
        g.drawLine(width/5, height/10, (int) (width/5f * 2), (int) (height/10f * 2));

        g.drawLine(width, height/10, (int) (width/5f * 4), height/10);
        g.drawLine((int) (width/5f * 4), height/10, (int) (width/5f * 3), (int) (height/10f * 2));

        g.drawLine(0, (int) (height/5f * 3), width/5, (int) (height/5f * 3));
        g.drawLine(width/5, (int) (height/10f * 6), (int) (width/5f * 2), (int) (height/10f * 5));

        g.drawLine(width, (int) (height/5f * 3), (int) (width/5f * 4), (int) (height/5f * 3));
        g.drawLine((int) (width/5f * 4), (int) (height/10f * 6), (int) (width/5f * 3), (int) (height/10f * 5));
    }
}
