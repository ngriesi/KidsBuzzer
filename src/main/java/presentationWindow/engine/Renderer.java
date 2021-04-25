package presentationWindow.engine;


import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import presentationWindow.renderItems.PresentationViewRenderItem;
import utils.resources.Resources;

import static org.lwjgl.opengl.GL11.*;

/**
 * renderer class setting up the shader and calling the rendering methods
 */
public class Renderer {

    /** transformation object used for the matrix calculations */
    private final Transformation transformation;

    /** shader program for the scene */
    private ShaderProgram shaderProgram;

    /**
     * constructor creates transformation object
     */
    public Renderer(){
        transformation = new Transformation();
    }

    /**
     * initialization: sets up the shader programs
     *
     * @param window unused
     * @throws Exception if shaders cant be created or the uniforms cant be found
     */
    @SuppressWarnings("unused")
    public void init(Window window) throws Exception{

        setupShader();
    }

    /**
     * sets up scene shader:
     * creates the shaders, creates the uniforms and initializes the light handler
     *
     * @throws Exception if shaders cant be created or uniforms cant be found
     */
    @SuppressWarnings("unused")
    private void setupShader() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Resources.loadResource("/shader/vertex.shader"));
        shaderProgram.createFragmentShader(Resources.loadResource("/shader/fragment.shader"));
        shaderProgram.link();


        //noinspection SpellCheckingInspection
        shaderProgram.createUniforms("projModelMatrix");
        shaderProgram.createUniforms("transparencyMode");
        shaderProgram.createUniforms("opacity");
        shaderProgram.createUniforms("keepCornerProportion");
        shaderProgram.createUniforms("cornerScale");
        shaderProgram.createUniforms("cornerSize");
        shaderProgram.createUniforms("useTexture");
        shaderProgram.createUniforms("texture2d");
        shaderProgram.createVector4fArrayUniform("colors",4);
        shaderProgram.createUniforms("useColorShade");
        shaderProgram.createUniforms("edgeStartColor");
        shaderProgram.createUniforms("edgeEndColor");
        shaderProgram.createUniforms("edgeSize");
        shaderProgram.createUniforms("edgeBlendMode");

    }


    /**
     * Main rendering method updates the viewport if the window got resized,
     * updates the main matrices with the camera and calls the three rendering methods
     *
     * @param window in witch the rendering happens
     */
    public void render(Window window, Scene scene){
        clear();




        GL11.glViewport(0,0,window.getWidth(),window.getHeight());
        if(window.isResized()){
            window.setResized(false);
        }

        renderScreen(window,scene);

        glClear( GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);


    }

    /**
     * calls method to render hud
     * rendering is done in the hud components classes
     *
     * @param window window to render in
     */
    @SuppressWarnings("unused")
    private void renderScreen(Window window, Scene scene) {

        Matrix4f orthographic = transformation.getOrtho2DProjectionMatrix(-PresentationViewRenderItem.MAX_DEPTH);

        shaderProgram.bind();

        glColorMask(true,true,true,true);

        scene.render(shaderProgram, orthographic, transformation);

        shaderProgram.unbind();
    }

    /**
     * cleans up the shaders
     */
    public void cleanup(){
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }

    /**
     * clears the buffers:
     * colorBuffer
     * depthBuffer
     * stencilBuffer
     */
    private void clear(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }
}
