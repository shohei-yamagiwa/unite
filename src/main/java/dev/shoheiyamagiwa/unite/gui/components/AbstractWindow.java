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
package dev.shoheiyamagiwa.unite.gui.components;

import dev.shoheiyamagiwa.unite.gui.Drawable;
import dev.shoheiyamagiwa.unite.utils.MathUtil;

/**
 * {@code AbstractWindow} is an abstracted class of the window.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public abstract class AbstractWindow extends Container implements Drawable {
    /**
     * A handle of the window.
     */
    protected long handle;

    /**
     * The title displayed on the upper window frame.
     */
    private String title;

    /**
     * Indicates whether the window is in fullscreen or not.
     */
    private boolean fullscreen = false;

    /**
     * Indicates whether the window is resizable or not.
     */
    private boolean resizable = true;

    /**
     * Indicates whether the window is running or not.
     */
    protected boolean running = false;

    /**
     * Supported dimensions.
     */
    public static final int SUPPORTED_MIN_WIDTH = 640;
    public static final int SUPPORTED_MIN_HEIGHT = 360;
    public static final int SUPPORTED_MAX_WIDTH = 3840;
    public static final int SUPPORTED_MAX_HEIGHT = 2160;

    /**
     * Creates a default sized window
     */
    public AbstractWindow() {
        this("");
    }

    /**
     * Creates a default sized window with given title.
     *
     * @param title The title to be displayed on the upper window frame.
     */
    public AbstractWindow(String title) {
        this(title, SUPPORTED_MIN_WIDTH, SUPPORTED_MIN_HEIGHT);
    }

    /**
     * Creates a window with specified title and size.
     *
     * @param title  The title to be displayed on the upper frame.
     * @param width  The width of the window.
     * @param height The height of the window.
     */
    public AbstractWindow(String title, int width, int height) {
        this.title = title;
        setSize(width, height);
    }

    /**
     * Run the window.
     */
    public abstract void run();

    @Override
    public void setWidth(int width) {
        super.setWidth(MathUtil.value(width, SUPPORTED_MIN_WIDTH, SUPPORTED_MAX_WIDTH));
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(MathUtil.value(height, SUPPORTED_MIN_HEIGHT, SUPPORTED_MAX_HEIGHT));
    }

    public long getHandle() {
        return handle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public boolean isRunning() {
        return running;
    }
}
