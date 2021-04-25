package presentationWindow.items;

import org.joml.Vector3f;

/**
 * Item class used for items that get rendered in the open gl window
 */
public class GameItem {

    /** mesh - drawable object of the GameItem */
    private Mesh mesh;

    /** position as Vector */
    private final Vector3f position;

    /** scale as float */
    private float scale;

    /** scale as Vector */
    private final Vector3f scale3;

    /** rotation as Vector */
    private final Vector3f rotation;

    /*
      For all Vector Attributes the changes can also be applied by using the get Method

      The new Values a used in the Transformation Methods to scale, rotate and position the mesh in the scene
     */

    /**
     * Constructor with mesh
     *
     * @param mesh mesh
     */

    @SuppressWarnings("unused")
    public GameItem(Mesh mesh){
        this();
        this.mesh = mesh;

    }

    /**
     * default constructor setting rotation, position and scales to default values
     */

    protected GameItem() {
        position = new Vector3f(0,0,0);
        scale = 1;
        rotation = new Vector3f(0,0,0);
        scale3 = new Vector3f(1,1,1);
    }

    /**
     * @return mesh of the game Item
     */

    public Mesh getMesh() {
        return mesh;
    }

    /**
     * @return position Vector
     */

    public Vector3f getPosition() {
        return position;
    }

    /**
     * @return scale float
     */

    @SuppressWarnings("unused")
    public float getScale() {
        return scale;
    }

    /**
     * @return rotation Vector
     */

    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * @return scale Vector
     */

    public Vector3f getScale3() {
        return scale3;
    }

    /**
     * changes the scale of the mesh
     *
     * @param scale new scale
     */

    @SuppressWarnings("unused")
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * changes the Position of the mesh
     *
     * @param x Position
     * @param y Position
     * @param z Position
     */

    public void setPosition(float x,float y,float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    /**
     * changes the rotation of the mesh
     *
     * @param x rotation
     * @param y rotation
     * @param z rotation
     */

    @SuppressWarnings("unused")
    public void setRotation(float x, float y, float z){
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    /**
     * changes the scale on certain axes
     * can be used to resize hud components
     *
     * @param x scale
     * @param y scale
     * @param z scale
     */

    public void setScale3(float x,float y,float z) {
        this.scale3.x = x;
        this.scale3.y = y;
        this.scale3.z = z;
    }

    /**
     * sets new mesh
     *
     * @param mesh new mesh
     */

    protected void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
