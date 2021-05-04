package presentationWindow.engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

/**
 * shader program containing methods to set uniforms and to create shaders
 */
@SuppressWarnings({"WeakerAccess"})
public class ShaderProgram {

    /**
     * id of the program
     */
    private final int programId;

    /**
     * id of the vertex shader
     */
    private int vertexShaderId;

    /**
     * if of fragment shader
     */
    @SuppressWarnings("unused")
    private int fragmentShaderId;

    /**
     * uniform pointer
     */
    private final Map<String, Integer> uniforms;

    /**
     * creates new shader program
     *
     * @throws Exception if open gl cant create a program
     */
    public ShaderProgram() throws Exception {
        uniforms = new HashMap<>();
        programId = glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
    }

    /**
     * creates the vertex shader
     *
     * @param shaderCode code of the vertex shader
     * @throws Exception if shader cant be created
     */
    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    /**
     * creates the fragment shader
     *
     * @param shaderCode code of the fragment shader
     * @throws Exception if shader cant be created
     */
    public void createFragmentShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    /**
     * creates shader of certain type
     *
     * @param shaderCode content of the shader
     * @param shaderType type of the shader
     * @return shader id
     * @throws Exception if shader cant be created or has a compile error
     */
    private int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader, shader type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    /**
     * links a program (refereed to with id) with a shader
     *
     * @throws Exception if shader cant be linked or validated
     */
    public void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    /**
     * binds th shader program
     */
    public void bind() {
        glUseProgram(programId);
    }

    /**
     * unbinds the shader program
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * unbinds shader program and deletes it
     */
    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    /**
     * creates a uniform variable
     *
     * @param uniformName name of the variable (has to be exactly same as in shader code)
     * @throws Exception if uniform cant be found
     */
    public void createUniforms(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform: " + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    /**
     * sets value of a Matrix4f uniform
     *
     * @param uniformName name
     * @param value       value
     */
    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    /**
     * sets value of a int uniform
     *
     * @param uniformName name
     * @param value       value
     */
    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    /**
     * sets value of a float uniform
     *
     * @param uniformName name
     * @param value       value
     */
    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    /**
     * sets value of a Vector3f uniform
     *
     * @param uniformName name
     * @param value       value
     */
    @SuppressWarnings("unused")
    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    /**
     * sets value of a Vector2f uniform
     *
     * @param uniformName name
     * @param value       value
     */
    public void setUniform(String uniformName, Vector2f value) {
        glUniform2f(uniforms.get(uniformName), value.x, value.y);
    }


    /**
     * sets value of a Vector4f uniform
     *
     * @param uniformName name
     * @param value       value
     */
    public void setUniform(String uniformName, Vector4f value) {
        glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    /**
     * sets the value of an array of uniforms
     *
     * @param uniformName name of the uniform
     * @param colors      colors ( max 4 )
     */
    public void setUniform(String uniformName, Vector4f[] colors) {
        int numColors = colors != null ? colors.length : 0;
        for (int i = 0; i < numColors; i++) {
            setUniform(uniformName + "[" + i + "]", colors[i]);
        }
    }

    /**
     * creates an array of vector4f uniforms
     *
     * @param uniformName name of the array
     * @param size        size of the array
     * @throws Exception if array cant be found
     */
    @SuppressWarnings("WeakerAccess")
    public void createVector4fArrayUniform(String uniformName, int size) throws Exception {
        for (int i = 0; i < size; i++) {
            createUniforms(uniformName + "[" + i + "]");
        }
    }
}
