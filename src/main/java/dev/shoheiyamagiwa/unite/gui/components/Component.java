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

import dev.shoheiyamagiwa.unite.OnClickListener;

/**
 * A component is a graphical element that is displayed on the screen and interacted with the user.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public abstract class Component {
    /**
     * The parent of the component. It may be {@code null} if the component is in top-level.
     */
    protected Container parent;

    /**
     * The x position of the component in the parent component.
     */
    protected int x;

    /**
     * The y position of the component in the parent component.
     */
    protected int y;

    /**
     * The width of the component.
     */
    protected int width;

    /**
     * The height of the component.
     */
    protected int height;

    /**
     * Indicates whether the component is visible or not.
     * A component that is not visible is not displayed on the screen.
     */
    protected boolean visible;

    /**
     * Indicates whether the component is enabled or not.
     * A component that is not enabled does not interact with the user.
     */
    protected boolean enabled;

    /**
     * The mouse listener
     */
    private OnClickListener onClickListener;

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public void setLocation(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = Math.max(0, width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = Math.max(0, height);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
