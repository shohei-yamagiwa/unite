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
package dev.shoheiyamagiwa.unite.graphics.impl;

import dev.shoheiyamagiwa.unite.graphics.AbstractGraphics;
import dev.shoheiyamagiwa.unite.graphics.AbstractImage;
import dev.shoheiyamagiwa.unite.graphics.font.impl.AsciiFont;
import dev.shoheiyamagiwa.unite.graphics.font.Glyph;
import dev.shoheiyamagiwa.unite.graphics.impl.shader.*;
import dev.shoheiyamagiwa.unite.math.Matrix4f;
import dev.shoheiyamagiwa.unite.utils.Color;
import dev.shoheiyamagiwa.unite.utils.FileUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * The {@code Graphics} class is the implementation of {@link AbstractGraphics} interface.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public class Graphics extends AbstractGraphics {
    /**
     * Variable names in vertex shader file.
     */
    private static final String VERT_ATTR_POSITION = "attrPosition";
    private static final String VERT_ATTR_COLOR = "attrColor";
    private static final String VERT_ATTR_TEX_COORD = "attrTexCoord";
    private static final String VERT_UNI_MODEL = "uniModel";
    private static final String VERT_UNI_VIEW = "uniView";
    private static final String VERT_UNI_PROJECTION = "uniProjection";

    /**
     * Variable names in fragment shader file.
     */
    private static final String FRAG_UNI_TEX_IMAGE = "uniTexImage";
    private static final String FRAG_OUT_COLOR = "fragColor";

    private final VertexArrayObject vao;
    private final VertexBufferObject vbo;
    private final ShaderProgram program;

    private final FloatBuffer vertices;
    private int verticesCount;

    private boolean rendering;

    /**
     * Initialize graphics context.
     */
    public Graphics() {
        // Initialize states
        verticesCount = 0;
        rendering = false;

        vao = new VertexArrayObject();
        vao.bind();
        vbo = new VertexBufferObject();
        vbo.bind();

        // Allocate vertices
        vertices = MemoryUtil.memAllocFloat(4096);

        // Upload null data to allocate storage for the VBO
        long size = (long) vertices.capacity() * Float.BYTES;
        vbo.uploadData(size);

        // Load shaders from GLSL file
        // FIXME: Change the way to load a shader file.
        Shader vertexShader = new VertexShader(FileUtil.readFile("/Unite/src/main/resources/default.vert"));
        Shader fragmentShader = new FragmentShader(FileUtil.readFile("/Unite/src/main/resources/default.frag"));

        // Create shader program
        program = new ShaderProgram();
        program.attachShader(vertexShader);
        program.attachShader(fragmentShader);
        program.bindFragmentDataLocation(0, FRAG_OUT_COLOR);
        program.link();
        program.use();

        // Dispose linked shaders
        vertexShader.dispose();
        fragmentShader.dispose();

        /* Specify vertex attributes */
        /* pos[0]  pos[1]  color[0]  color[1]  color[2]  color[3]  texCoord[0]  texCoord[1] */
        int attributePos = program.getAttributeLocation(VERT_ATTR_POSITION);
        program.enableVertexAttribute(attributePos);
        program.pointVertexAttribute(attributePos, 2, 8 * Float.BYTES, 0);

        int attributeColor = program.getAttributeLocation(VERT_ATTR_COLOR);
        program.enableVertexAttribute(attributeColor);
        program.pointVertexAttribute(attributeColor, 4, 8 * Float.BYTES, 2 * Float.BYTES);

        int attributeTextureCoord = program.getAttributeLocation(VERT_ATTR_TEX_COORD);
        program.enableVertexAttribute(attributeTextureCoord);
        program.pointVertexAttribute(attributeTextureCoord, 2, 8 * Float.BYTES, 6 * Float.BYTES);

        /* Get width and height of framebuffer */
        long window = GLFW.glfwGetCurrentContext();
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer widthBuffer = stack.mallocInt(1);
            IntBuffer heightBuffer = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
            width = widthBuffer.get();
            height = heightBuffer.get();
        }

        /* Specify texture uniform */
        int uniformTexture = program.getUniformLocation(FRAG_UNI_TEX_IMAGE);
        program.setUniform(uniformTexture, 0);

        /* Set model matrix to identity matrix */
        int uniformModel = program.getUniformLocation(VERT_UNI_MODEL);
        Matrix4f model = new Matrix4f();
        program.setUniform(uniformModel, model);

        /* Set view matrix to identity matrix */
        int uniformView = program.getUniformLocation(VERT_UNI_VIEW);
        Matrix4f view = new Matrix4f();
        program.setUniform(uniformView, view);

        /* Set projection matrix to an orthographic projection */
        int uniformProjection = program.getUniformLocation(VERT_UNI_PROJECTION);
        Matrix4f projection = Matrix4f.orthographic(0.0F, width, 0.0F, height, -1.0F, 1.0F);
        program.setUniform(uniformProjection, projection);

        /* Enable blending */
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set fallback font in case that the user forget to set the font.
        if (font == null) {
            font = new AsciiFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        }
    }

    @Override
    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        MemoryUtil.memFree(vertices);

        vao.delete();
        vbo.delete();
        program.delete();
    }

    @Override
    public void drawRect(int x, int y, int width, int height, Color color) {
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            long hex = color.toHex();
            /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
            buffer.put((byte) ((hex >> 16) & 0xFF));
            /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
            buffer.put((byte) ((hex >> 8) & 0xFF));
            /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
            buffer.put((byte) (hex & 0xFF));
            /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
            buffer.put((byte) ((hex >> 24) & 0xFF));
        }
        buffer.flip();
        AbstractImage image = new Image(buffer, width, height);
        MemoryUtil.memFree(buffer);

        begin();
        drawImage(image, x, y);
        end();
    }

    @Override
    public void drawText(String text, int x, int y, Color color) {
        AsciiFont asciiFont = (AsciiFont) font;
        Image image = (Image) asciiFont.getFontImage();

        int textHeight = asciiFont.getHeight(text);
        float drawX = x;
        float drawY = y;
        if (textHeight > image.getHeight()) {
            drawY += textHeight - image.getHeight();
        }

        begin();
        image.bind();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            /* Update x and y to draw at the next line */
            if (c == '\n') {
                drawY -= image.getHeight();
                drawX = x;
                continue;
            }

            /* Skip the carriage return */
            if (c == '\r') {
                continue;
            }

            Glyph glyph = asciiFont.getGlyph(c);
            drawImage(image, drawX, drawY, glyph.getX(), glyph.getY(), glyph.getWidth(), glyph.getHeight(), color);
            drawX += glyph.getWidth();
        }
        end();
    }

    @Override
    public int getTextWidth(String text) {
        return font.getWidth(text);
    }

    @Override
    public int getTextHeight(String text) {
        return font.getHeight(text);
    }

    @Override
    public void drawImage(AbstractImage image, int x, int y) {
        /* Vertex positions */
        float x2 = x + image.getWidth();
        float y2 = y + image.getHeight();

        /* Image coordinates */
        float s1 = 0.0F;
        float t1 = 0.0F;
        float s2 = 1.0F;
        float t2 = 1.0F;

        begin();
        image.bind();
        drawImage(x, y, x2, y2, s1, t1, s2, t2, Color.WHITE);
        end();
    }

    /**
     * Begin rendering.
     */
    private void begin() {
        if (rendering) {
            return;
        }
        rendering = true;
        verticesCount = 0;
    }

    /**
     * End rendering.
     */
    private void end() {
        if (!rendering) {
            return;
        }
        rendering = false;
        flush();
    }

    private void flush() {
        if (verticesCount <= 0) {
            return;
        }
        vertices.flip();

        vao.bind();
        program.use();

        // Upload a new vertex data
        vbo.bind();
        vbo.uploadSubData(vertices);

        // Render batch
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, verticesCount);

        // Clear vertex data for next batch
        vertices.clear();
        verticesCount = 0;
    }

    /**
     * Draws an image region with the currently bound texture on specified coordinates.
     *
     * @param image     Used for getting width and height of the image
     * @param x         X position of the texture
     * @param y         Y position of the texture
     * @param regX      X position of the texture region
     * @param regY      Y position of the texture region
     * @param regWidth  Width of the texture region
     * @param regHeight Height of the texture region
     * @param c         The color to use
     */
    private void drawImage(AbstractImage image, float x, float y, float regX, float regY, float regWidth, float regHeight, Color c) {
        /* Vertex positions */
        float x2 = x + regWidth;
        float y2 = y + regHeight;

        /* Texture coordinates */
        float s1 = regX / image.getWidth();
        float t1 = regY / image.getHeight();
        float s2 = (regX + regWidth) / image.getWidth();
        float t2 = (regY + regHeight) / image.getHeight();

        drawImage(x, y, x2, y2, s1, t1, s2, t2, c);
    }

    /**
     * Draws a texture region with the currently bound texture on specified
     * coordinates.
     *
     * @param x1 Bottom left x position
     * @param y1 Bottom left y position
     * @param x2 Top right x position
     * @param y2 Top right y position
     * @param s1 Bottom left s coordinate
     * @param t1 Bottom left t coordinate
     * @param s2 Top right s coordinate
     * @param t2 Top right t coordinate
     * @param c  The color to use
     */
    private void drawImage(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Color c) {
        if (vertices.remaining() < 8 * 6) {
            flush(); // We need more space in the buffer, so flush it
        }
        float r = c.getRed();
        float g = c.getGreen();
        float b = c.getBlue();
        float a = c.getAlpha();

        vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);
        verticesCount += 6;
    }
}
