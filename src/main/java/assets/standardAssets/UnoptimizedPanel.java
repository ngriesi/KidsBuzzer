package assets.standardAssets;

import javax.swing.*;
import java.awt.*;

/**
 * panel without optimized drawing enabled
 */
public class UnoptimizedPanel extends JPanel {

    /**
     * creates an unoptimized panel with a layout
     *
     * @param layout layout of the panel
     */
    public UnoptimizedPanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * disables optimized drawing
     *
     * @return false
     */
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }
}
