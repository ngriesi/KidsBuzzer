package programs.scoreBoard.main.view.items;

import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.PresentationViewRenderItem;
import presentationWindow.renderItems.QuadItem;

public class MetalQuad extends QuadItem {

    private QuadItem innerQuad;

    public MetalQuad() {
        ColorScheme colorScheme = new ColorScheme(Color.WHITE,Color.GREY,new Color(0,0,0,0), new Color(0,0,0,0));
        this.setColorScheme(colorScheme);
        this.setUseColorShade(true);

        innerQuad = new QuadItem();
        ColorScheme colorSchemeInner = new ColorScheme(Color.DARK_GRAY,Color.WHITE,Color.TRANSPARENT, Color.TRANSPARENT);
        innerQuad.setColorScheme(colorSchemeInner);
        updateInnerQuad();
        innerQuad.setUseColorShade(true);


        super.addItem(innerQuad);
    }

    private void updateInnerQuad() {
        if (window != null) {
            float edgeSize = 0.03f;
            float ratio = window.getWidth()/ (float)window.getScreenWidth();
            innerQuad.setSize(this.getSize().x - edgeSize * ratio, this.getSize().y - edgeSize * ratio * window.getAspectRatio());
            innerQuad.setPosition(this.getXPosition(), this.getYPosition());
            innerQuad.setOpacity(this.getOpacity());
        }
    }

    @Override
    public void addItem(PresentationViewRenderItem item) {
        innerQuad.addItem(item);
    }

    @Override
    public void setSize(float x, float y) {
        super.setSize(x, y);
        updateInnerQuad();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updateInnerQuad();
    }

    @Override
    public void setOpacity(float opacity) {
        super.setOpacity(opacity);
        updateInnerQuad();
    }

    @Override
    public void setWindow(Window window) {
        super.setWindow(window);
        updateInnerQuad();
    }
}
