package presentationWindow.items;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

@SuppressWarnings({"WeakerAccess"})
public class Mesh {

    /** vertex array object id */
    private final int vaoId;

    /** vertex buffer object id  list */
    private final List<Integer> vboidList;

    /** material of the mesh */
    private Material material;

    /** number of vertices */
    private final int vertexCount;

    /**
     * Method creates an vao that can be used by the open gl graphics context form the
     * position Array, texture coordinates, normal Vectors and an indices array used to identify vertices.
     *
     * @param positions positions of the vertices
     * @param textCords texture coordinates
     * @param normals normal vectors
     * @param indices indices of the vertices
     */
    public Mesh(float[] positions, float[] textCords, float[] normals, int[] indices) {
        this(positions, textCords, normals, new float[0], new float[0], new int[0], new float[0], indices);
    }


    /**
     * creates a float vbo from a float array
     *
     * @param index index of the vbo
     * @param size size of the vbo elements
     * @param content content of the vbo
     * @param empty if true an empty vbo is created
     */
    private void createFloatVBO(int index, int size, float[] content,boolean empty) {

        FloatBuffer buffer;

        //Position VBO
        int vboId = glGenBuffers();
        vboidList.add(vboId);
        buffer = MemoryUtil.memAllocFloat(content.length);
        if(!empty) buffer.put(content).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index,size,GL_FLOAT,false,0,0);

        MemoryUtil.memFree(buffer);
    }

    @SuppressWarnings("SameParameterValue")
    private void createIntVBO(int index, int size, int[] content, boolean empty) {

        IntBuffer buffer;

        //Position VBO
        int vboId = glGenBuffers();
        vboidList.add(vboId);
        buffer = MemoryUtil.memAllocInt(content.length);
        if(!empty) buffer.put(content).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glEnableVertexAttribArray(index);
        glVertexAttribIPointer(index,size,GL_INT,0,0);

        MemoryUtil.memFree(buffer);
    }


    /**
     * Method creates an vao that can be used by the open gl graphics context form the
     * position Array, texture coordinates, normal Vectors and an indices array used to identify vertices.
     * It also uses tangent and bi tangent vectors to calculate the orientation of a potential normal map
     *
     * @param positions positions of the vertices
     * @param textCords texture coordinates
     * @param normals normal vectors
     * @param tangents tangent vectors
     * @param biTangents biTangent vectors
     * @param indices indices of the vertices
     */

    public Mesh(float[] positions,float[] textCords,float[] normals,float[] tangents,float[] biTangents,int[] jointIDs, float[] weights, int[] indices){



        IntBuffer indicesBuffer = null;
        material = new Material();
        try{
            vertexCount = indices.length;
            vboidList = new ArrayList<>();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            //Position VBO
            createFloatVBO(0,3,positions,false);

            // texture coordinates VBO
            createFloatVBO(1,2,textCords,false);

            // normals VBO
            if(normals.length > 0) {
                createFloatVBO(2, 3, normals,false);
            } else {
                createFloatVBO(2,3,positions,true);
            }

            // Tangent VBO
            if(tangents.length > 0) {
                createFloatVBO(3, 3, tangents,false);
            } else {
                createFloatVBO(3,3,positions,true);
            }

            // BiTangent VBO
            if(biTangents.length > 0) {
                createFloatVBO(4, 3, biTangents,false);
            } else {
                createFloatVBO(4,3,positions,true);
            }

            // weights
            if(weights.length > 0) {
                createFloatVBO(5, 3, weights,false);
            }

            // joint ids
            if (jointIDs.length > 0) {
                createIntVBO(6, 3, jointIDs, false);
            }



            //Index VBO
            int vboId = glGenBuffers();
            vboidList.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer,GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER,0);
            glBindVertexArray(0);

        }finally {

            if(indicesBuffer != null){
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    /**
     * @return Material of the Mesh
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * sets the material of the mesh
     *
     * @param material new Material
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * returns the vaoid to identify the mesh
     *
     * @return vaoid
     */
    @SuppressWarnings("WeakerAccess")
    public int getVaoId() {
        return vaoId;
    }

    /**
     * @return number of vertices used
     */

    @SuppressWarnings("WeakerAccess")
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * initialises the rendering by binding the texture and the Vertex Array
     * to make them ready to be rendered
     */
    private void initRender() {
        Texture texture = material.getTexture();
        if(texture != null){
            //Activate first texture Bank
            glActiveTexture(GL_TEXTURE0);
            //Bind texture
            glBindTexture(GL_TEXTURE_2D,texture.getId());
        }

        Texture normalMap = material.getNormalMap();
        if(normalMap != null) {
            // Activate second texture bank
            glActiveTexture(GL_TEXTURE1);

            // bind texture
            glBindTexture(GL_TEXTURE_2D, normalMap.getId());
        }

        //Draw the mesh
        glBindVertexArray(getVaoId());
    }

    /**
     * ends the rendering by unbinding texture and vertex array
     */
    private void endRender(){
        //restore state
        glBindVertexArray(0);

        glBindTexture(GL_TEXTURE_2D,0);
    }

    /**
     * draws elements on screen
     */
    public void render() {
        initRender();

        glDrawElements(GL_TRIANGLES,getVertexCount(),GL_UNSIGNED_INT,0);

        endRender();
    }


    /**
     * frees resources and cleans up memory
     */
    public void cleanup(){

        glDisableVertexAttribArray(0);

        //Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER,0);
        for(int vboId : vboidList){
            glDeleteBuffers(vboId);
        }

        //Delete texture
        Texture texture = material.getTexture();
        if(texture != null){
            texture.cleanup();
        }


        //Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    /**
     * deletes the buffers to free resources
     */
    @SuppressWarnings("unused")
    public void deleteBuffers() {
        glDisableVertexAttribArray(0);

        //Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER,0);
        for(int vboId : vboidList){
            glDeleteBuffers(vboId);
        }

        //Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
