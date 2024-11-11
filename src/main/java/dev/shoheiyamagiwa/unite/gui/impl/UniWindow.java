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
package dev.shoheiyamagiwa.unite.gui.impl;

import dev.shoheiyamagiwa.unite.graphics.AbstractGraphics;
import dev.shoheiyamagiwa.unite.graphics.impl.Graphics;
import dev.shoheiyamagiwa.unite.gui.components.AbstractWindow;
import dev.shoheiyamagiwa.unite.gui.components.Component;
import dev.shoheiyamagiwa.unite.gui.Drawable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * {@code UniWindow} is a main implementation of {@link AbstractWindow}.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public class UniWindow extends AbstractWindow {

    public UniWindow() {
    }

    public UniWindow(String title) {
        super(title);
    }

    public UniWindow(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public void run() {
        AbstractGraphics g = null;
        running = true;

        create();
        try {
            g = new Graphics();
            while (running) {
                draw(g);
            }
        } finally {
            if (g != null) {
                dispose(g);
            }
        }
    }

    /**
     * Creates the actual window by using GLFW.
     */
    private void create() {
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }
        // Reset all window hints
        glfwDefaultWindowHints();

        // Configure window hints
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_VISIBLE, isVisible() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, isResizable() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, isFullscreen() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE);
        // TODO: Implement custom title bar feature.
        // glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);

        // Create new window
        if (isFullscreen()) {
            handle = glfwCreateWindow(getWidth(), getHeight(), getTitle(), glfwGetPrimaryMonitor(), NULL);
        } else {
            handle = glfwCreateWindow(getWidth(), getHeight(), getTitle(), NULL, NULL);
        }
        if (handle == NULL) {
            throw new RuntimeException("Failed to create a new window.");
        }

        // Configure callbacks
        glfwSetWindowCloseCallback(handle, _ -> running = false);

        // Make the context of the window as current one.
        glfwMakeContextCurrent(handle);

        // Show the window if needed.
        if (isVisible()) {
            glfwShowWindow(handle);
        }

        // Initialize GL features.
        GL.createCapabilities();
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void draw(AbstractGraphics g) {
        g.clear();

        // Draw child components
        for (Component c : getChildren()) {
            if (c instanceof Drawable) {
                ((Drawable) c).draw(g);
            }
        }

        // Post processes
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    @Override
    public void dispose(AbstractGraphics g) {
        // Dispose child components
        for (Component c : getChildren()) {
            if (c instanceof Drawable) {
                ((Drawable) c).dispose(g);
            }
        }

        // Dispose graphics context
        g.dispose();

        // Dispose GLFW
        glfwDestroyWindow(handle);
        glfwTerminate();
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);

        // Tell GLFW to update the window position.
        glfwSetWindowPos(handle, x, y);
    }
}
