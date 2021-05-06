package programs.scoreBoard.main.view.items;

import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.PresentationViewRenderItem;
import presentationWindow.renderItems.QuadItem;

/**
 * creates a quad item for the presentation view that looks like a metal plate
 */
public class MetalQuad extends QuadItem {

    /**
     * inner quad of the metal
     */
    private QuadItem innerQuad;

    /**
     * creates the <code>MetalQuad</code> view item
     */
    MetalQuad() {
        ColorScheme colorScheme = new ColorScheme(Color.WHITE, Color.GREY, new Color(0, 0, 0, 0), new Color(0, 0, 0, 0));
        this.setColorScheme(colorScheme);
        this.setUseColorShade(true);

        innerQuad = new QuadItem();
        ColorScheme colorSchemeInner = new ColorScheme(Color.DARK_GRAY, Color.WHITE, Color.TRANSPARENT, Color.TRANSPARENT);
        innerQuad.setColorScheme(colorSchemeInner);
        updateInnerQuad();
        innerQuad.setUseColorShade(true);


        super.addItem(innerQuad);
    }

    /**
     * updates the size and position of the inner quad according to the size an position of the outer quad
     */
    private void updateInnerQuad() {
        if (window != null) {
            float edgeSize = 0.015f * window.getWidth() / (float) window.getScreenWidth();
            float ratio = window.getWidth() / (float) window.getScreenWidth();
            innerQuad.setSize(this.getSize().x - edgeSize * ratio, this.getSize().y - edgeSize * ratio * window.getAspectRatio());
            innerQuad.setPosition(this.getXPosition(), this.getYPosition());
            innerQuad.setOpacity(this.getOpacity());
        }
    }

    /**
     * overrides the add method of the outer quad so items added to this component will
     * be added to the inner component
     *
     * @param item to be added
     */
    @Override
    public void addItem(PresentationViewRenderItem item) {
        innerQuad.addItem(item);
    }

    /**
     * sets the size of the outer quad and updates the inner quad
     *
     * @param width  new width (float, 1 equals window width)
     * @param height new height (float, 1 equals window height)
     */
    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        updateInnerQuad();
    }

    /**
     * sets the position of the outer quad and updates the inner quad
     *
     * @param x new x position (float, 0 equals start of the window, 1 equals end of the window,
     *          the component is centered around the position set here)
     * @param y new y position (float, 0 equals start of the window, 1 equals end of the window,
     *          the component is centered around the position set here)
     */
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updateInnerQuad();
    }

    /**
     * sets the opacity of the outer component and updates the inner quad
     *
     * @param opacity new opacity (float, 1 equals completely visible, 0 equals completely hidden)
     */
    @Override
    public void setOpacity(float opacity) {
        super.setOpacity(opacity);
        updateInnerQuad();
    }

    /**
     * sets the reference to the window of the inner and outer quad
     *
     * @param window window
     */
    @Override
    public void setWindow(Window window) {
        super.setWindow(window);
        updateInnerQuad();
    }

    /**
     * @return returns the inner quad of the item
     */
    public QuadItem getCentralPlane() {
        return innerQuad;
    }
}
