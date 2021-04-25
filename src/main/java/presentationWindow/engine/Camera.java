package presentationWindow.engine;

import org.joml.Vector3f;

@SuppressWarnings("WeakerAccess")
public class Camera {

    /** position of the camera */
    private final Vector3f position;

    /** rotation of the camera */
    private final Vector3f rotation;

    /**
     * sets position and rotation to 0,0,0
     */
    public Camera(){
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
    }

    /**
     * Creates camera with certain attributes
     *
     * @param position camera position
     * @param rotation camera rotation
     */

    @SuppressWarnings("unused")
    public Camera(Vector3f position, Vector3f rotation){
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * returns the position vector of the camera
     * changing the vectors attributes (x,y,z) will also change the camera Position
     *
     * @return positionVector of the camera
     */

    public Vector3f getPosition() {
        return position;
    }

    /**
     * sets camera Position
     *
     * @param x xPosition
     * @param y yPosition
     * @param z zPosition
     */

    @SuppressWarnings("unused")
    public void setPosition(float x, float y, float z){
        position.x = x;
        position.y = y;
        position.z = z;
    }

    /**
     * moves camera a certain offset
     *
     * @param offsetX move along x axis
     * @param offsetY move along y axis
     * @param offsetZ move along z axis
     */

    @SuppressWarnings("unused")
    public void movePosition(float offsetX, float offsetY, float offsetZ){
        if(offsetZ != 0){
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f *offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if(offsetX != 0){
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f *offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    /**
     * returns the rotation vector of the camera
     * changing the vectors attributes (x,y,z) will also change the camera Rotation
     *
     * @return rotation Vector of the camera
     */

    public Vector3f getRotation(){
        return rotation;
    }

    /**
     * sets camera Rotation
     *
     * @param x xRotation
     * @param y yRotation
     * @param z zRotation
     */

    @SuppressWarnings("unused")
    public void setRotation(float x, float y, float z){
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    /**
     * rotates camera around all three axes
     * values in degree
     *
     * @param offsetX x rotation
     * @param offsetY y rotation
     * @param offsetZ z rotation
     */

    @SuppressWarnings("unused")
    public void moveRotation(float offsetX, float offsetY, float offsetZ){
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
}
