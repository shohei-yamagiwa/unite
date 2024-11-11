/**
 * The MIT License (MIT)
 * <p>
 * Copyright @ 2024, Shohei Yamagiwa
 * </p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * </p>
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dev.shoheiyamagiwa.unite.graphics.impl.shader;

import dev.shoheiyamagiwa.unite.math.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

/**
 * The {@code ShaderProgram} class represents the shader program in OpenGL.
 *
 * @author Shohei Yamagiwa
 * @since 1.0
 */
public class ShaderProgram {
    /**
     * The handle of the program.
     */
    private final int id;

    /**
     * Creates new shader program.
     */
    public ShaderProgram() {
        id = glCreateProgram();
    }

    /**
     * Attach given shader to this program
     *
     * @param shader The shader you want to attach to the program
     */
    public void attachShader(Shader shader) {
        glAttachShader(id, shader.getHandle());
    }

    /**
     * Binds the fragment out color variable.
     *
     * @param number Color number you want to bind
     * @param name   Variable name
     */
    public void bindFragmentDataLocation(int number, CharSequence name) {
        GL30.glBindFragDataLocation(id, number, name);
    }

    /**
     * Links shaders
     */
    public void link() {
        glLinkProgram(id);
        int linked = glGetProgrami(id, GL_LINK_STATUS);
        if (linked != GL_TRUE) {
            throw new RuntimeException(GL20.glGetProgramInfoLog(id));
        }
    }

    /**
     * Uses the shader program
     */
    public void use() {
        glUseProgram(id);
    }

    /**
     * Deletes the shader program
     */
    public void delete() {
        glDeleteProgram(id);
    }

    /**
     * Gets the location of an attribute variable with specified name.
     *
     * @param name Attribute name
     * @return Location of the attribute
     */
    public int getAttributeLocation(CharSequence name) {
        return glGetAttribLocation(id, name);
    }

    /**
     * Enables a vertex attribute.
     *
     * @param location Location of the vertex attribute
     */
    public void enableVertexAttribute(int location) {
        glEnableVertexAttribArray(location);
    }

    /**
     * Disables a vertex attribute.
     *
     * @param location Location of the vertex attribute
     */
    public void disableVertexAttribute(int location) {
        glDisableVertexAttribArray(location);
    }

    /**
     * Sets the vertex attribute pointer.
     *
     * @param location Location of the vertex attribute
     * @param size     Number of values per vertex
     * @param stride   Offset between consecutive generic vertex attributes in
     *                 bytes
     * @param offset   Offset of the first component of the first generic vertex
     *                 attribute in bytes
     */
    public void pointVertexAttribute(int location, int size, int stride, int offset) {
        glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
    }

    /**
     * Gets the location of an uniform variable with specified name.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value    Value to set
     */
    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value    Value to set
     */
    public void setUniform(int location, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4 * 4);
            value.toBuffer(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    /**
     * Get the handle of the shader program.
     *
     * @return The handle of the shader program.
     */
    protected int getId() {
        return id;
    }
}
