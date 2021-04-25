package presentationWindow.items;

import org.lwjgl.system.MemoryStack;
import startupApp.LoadingHandler;
import startupApp.LoadingMonitor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

@SuppressWarnings("WeakerAccess")
public class Texture {

    public static Texture loadTexture(File file, LoadingHandler loadingHandler) throws FileNotFoundException {
        LoadingMonitor loadingMonitor = new LoadingMonitor(file.getAbsolutePath());
        loadingHandler.addLoadingProcess(loadingMonitor);
        ByteBuffer buf;
        //Load texture File
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            buf = stbi_load(file.getAbsolutePath(), w, h, channels, 4);
            if (buf == null) {
                throw new FileNotFoundException("Cant find file " + file.getAbsolutePath());
            }


            return new Texture(w.get(), h.get(), buf, loadingHandler, loadingMonitor);
        }
    }

    public static Texture loadTexture(File file) {
        ByteBuffer buf;
        //Load texture File
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            buf = stbi_load(file.getAbsolutePath(), w, h, channels, 4);
            if (buf == null) {
                throw new Exception("Image file [" + file.getAbsolutePath() + "] not loaded: " + stbi_failure_reason());
            }


            return new Texture(w.get(), h.get(), buf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Modes of texture filtering
     */
    public enum FilterMode {
        NEAREST, LINEAR
    }

    /**
     * texture id
     */
    private final int id;

    /**
     * texture image width in pixels
     */
    private final int width;

    /**
     * texture image height in pixels
     */
    private final int height;

    /**
     * determines the texture filter used
     */
    private FilterMode filterMode = FilterMode.NEAREST;

    private Texture(int width, int height, ByteBuffer buf, LoadingHandler loadingHandler, LoadingMonitor loadingMonitor) {
        this.width = width;
        this.height = height;

        this.id = createTexture(buf);
        loadingMonitor.finishedProcess(loadingHandler);
    }

    private Texture(int width, int height, ByteBuffer buf) {
        this.width = width;
        this.height = height;

        this.id = createTexture(buf);
    }

    public Texture(BufferedImage image) {
        filterMode = FilterMode.LINEAR;
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                int pixel = pixels[h * image.getWidth() + w];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();


        width = image.getWidth();
        height = image.getHeight();


        id = createTexture(buffer);
    }

    /**
     * creates a texture from a byte buffer and stores the id that points to it in the id field
     *
     * @param imageBuffer byte buffer with image data
     * @throws Exception if stbi_load_from_memory cant load the data into a buffer
     */
    @SuppressWarnings("unused")
    public Texture(ByteBuffer imageBuffer) throws Exception {
        ByteBuffer buf;
        //Load texture File
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            buf = stbi_load_from_memory(imageBuffer, w, h, channels, 4);
            if (buf == null) {
                throw new Exception("Image file not loaded: " + stbi_failure_reason());
            }

            //Get width and height of image
            width = w.get();
            height = h.get();
        }

        this.id = createTexture(buf);
    }

    /**
     * creates texture form image file and stores the texture id in the id field
     *
     * @param filename   name of the image file
     * @param filterMode filtering mode for the texture
     * @throws Exception if stbi_load cant load the image into a buffer
     */
    public Texture(String filename, FilterMode filterMode) throws Exception {
        this.filterMode = filterMode;
        ByteBuffer buf;
        //Load texture File
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            buf = stbi_load(filename, w, h, channels, 4);
            if (buf == null) {
                throw new Exception("Image file [" + filename + "] not loaded: " + stbi_failure_reason());
            }

            //Get width and height of image
            width = w.get();
            height = h.get();
        }

        this.id = createTexture(buf);
    }

    /**
     * constructor creating an empty texture for storing the depth component of the
     * framebuffer
     *
     * @param width       width of the framebuffer
     * @param height      height of the framebuffer
     * @param pixelFormat format of the data  of one pixel
     */
    @SuppressWarnings("unused")
    public Texture(int width, int height, int pixelFormat) {
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, this.id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width, this.height, 0, pixelFormat, GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    }

    /**
     * creates texture form byte buffer and returns the texture id
     * it also frees the buffer again
     *
     * @param buf image data
     * @return texture id
     */
    private int createTexture(ByteBuffer buf) {
        //Create an openGl texture
        int textureId = glGenTextures();
        //Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        //Tell openGl how to unpack the RGBA bytes, Each component is 1 byte size
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        switch (filterMode) {
            case NEAREST:
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                break;
            case LINEAR:
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                break;
        }


        //upload the texture data
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        //Generate MipMap
        glGenerateMipmap(GL_TEXTURE_2D);

        stbi_image_free(buf);


        return textureId;
    }

    /**
     * @return texture width in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return texture height in pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * binds texture
     */
    @SuppressWarnings("unused")
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * @return texture id
     */
    public int getId() {
        return id;
    }

    /**
     * deletes the texture with this id
     */
    public void cleanup() {
        glDeleteTextures(id);
    }


}
