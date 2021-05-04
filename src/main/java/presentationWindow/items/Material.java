package presentationWindow.items;

import org.joml.Vector4f;

/**
 * Material for a GameItem in the window
 */
@SuppressWarnings("unused")
public class Material {

    /**
     * default mesh color
     */
    private static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    /**
     * color in ambient light
     */
    private Vector4f ambientColor;

    /**
     * color in diffuse light
     */
    private Vector4f diffuseColor;

    /**
     * color in specular light
     */
    private Vector4f specularColor;

    /**
     * reflectance of the surface
     */
    private float reflectance;

    /**
     * texture of the material
     */
    private Texture texture;

    /**
     * setting default values
     */
    @SuppressWarnings({"unused", "WeakerAccess"})
    public Material() {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, null, null, 0);
    }

    /**
     * creating a single colored material
     *
     * @param colour      mesh color
     * @param reflectance mesh reflectance
     */
    public Material(Vector4f colour, float reflectance) {
        this(colour, colour, colour, null, reflectance);
    }

    /**
     * creating a textured material
     *
     * @param texture used texture
     */
    public Material(Texture texture) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
    }

    /**
     * creating a textured material with reflectiveness
     *
     * @param texture     texture used
     * @param reflectance reflectance of the surface
     */
    public Material(Texture texture, float reflectance) {
        this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
    }

    /**
     * custom values for all fields (no normal map)
     *
     * @param ambientColor  ambient
     * @param diffuseColor  diffuse
     * @param specularColor specular
     * @param texture       texture
     * @param reflectance   reflectance
     */
    @SuppressWarnings("WeakerAccess")
    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, Texture texture, float reflectance) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    /**
     * custom values for all fields
     *
     * @param ambientColor  ambient
     * @param diffuseColor  diffuse
     * @param specularColor specular
     * @param texture       texture
     * @param normalMap     normal map
     * @param reflectance   reflectance
     */
    @SuppressWarnings("WeakerAccess")
    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, Texture texture, Texture normalMap, float reflectance) {
        this(ambientColor, diffuseColor, specularColor, texture, reflectance);
    }

    /**
     * @return ambient color vector
     */
    public Vector4f getAmbientColor() {
        return ambientColor;
    }

    /**
     * @param ambientColor new color
     */
    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    /**
     * @return diffuse color vector
     */
    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }

    /**
     * @param diffuseColor new color
     */
    @SuppressWarnings("unused")
    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    /**
     * @return specular color vector
     */
    public Vector4f getSpecularColor() {
        return specularColor;
    }

    /**
     * @param specularColor new color
     */
    @SuppressWarnings("unused")
    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    /**
     * @return reflectance
     */
    public float getReflectance() {
        return reflectance;
    }

    /**
     * @param reflectance new reflectance
     */
    @SuppressWarnings("unused")
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    /**
     * @return check if texture is used
     */
    public boolean isTexture() {
        return this.texture != null;
    }

    /**
     * @return texture of the material
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * @param texture new texture
     */
    @SuppressWarnings("unused")
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}
