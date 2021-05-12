package presentationWindow.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import presentationWindow.items.GameItem;

/**
 * class containing the methods for the matrix calculations for the 2d and 3d rendering
 */
@SuppressWarnings("SpellCheckingInspection")
public class Transformation {

    /**
     * Matrix for orthographic projection ( hud )
     */
    private final Matrix4f orthoMatrix;

    /**
     * Matrix for an orthographic model
     */
    private final Matrix4f orthographic2DMatrix;

    /**
     * create a new object for every matrix
     */
    public Transformation() {
        orthoMatrix = new Matrix4f();
        orthographic2DMatrix = new Matrix4f();
    }

    /**
     * creates a matrix for the orthographic projection of hud elements by using the windows bounds
     * making the left top the origin by setting left and top to 0 and the right and bottom can be applied to any number
     * setting right to one means an object with the x coordinate 1 has its origin at the right edge of the window
     *
     * @param zFar sets the far field value for the matrix. This corresponds to the range of depth values
     *             that are used
     * @return orthographic Matrix
     */
    final Matrix4f getOrtho2DProjectionMatrix(float zFar) {
        orthoMatrix.identity();
        orthoMatrix.setOrtho((float) 0, (float) 1, (float) 1, (float) 0, (float) 0, zFar);
        return orthoMatrix;
    }

    /**
     * Creates the matrix that has to be used for the specific game item by using the transformation applied to this game item
     * this Method is for the game items drawn orthographically
     *
     * @param gameItem    game item that gets rendered with this transformation
     * @param orthoMatrix of the current window
     * @return orthoProjectionModelMatrix
     */
    public Matrix4f buildOrtoProjModelMatrix(GameItem gameItem, Matrix4f orthoMatrix) {
        Vector3f rotation = gameItem.getRotation();
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.identity().translate(gameItem.getPosition()).
                rotateX((float) Math.toRadians(-rotation.x)).
                rotateY((float) Math.toRadians(-rotation.y)).
                rotateZ((float) Math.toRadians(-rotation.z)).
                scale(gameItem.getScale3().x, gameItem.getScale3().y, gameItem.getScale3().z);
        orthographic2DMatrix.set(orthoMatrix);
        orthographic2DMatrix.mul(modelMatrix);
        return orthographic2DMatrix;
    }

}
