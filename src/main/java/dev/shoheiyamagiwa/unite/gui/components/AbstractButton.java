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

/**
 * {@code AbstractButton} is an abstracted class for buttons.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public abstract class AbstractButton extends Container implements Drawable {
    /**
     * The text to be rendered in the center of button.
     */
    protected String text;

    /**
     * Indicates whether the button is hovered by users or not.
     */
    protected boolean hovered;

    /**
     * Create a button
     */
    public AbstractButton() {
    }

    /**
     * Create a button with given text.
     *
     * @param text The text to be rendered in the center of the button.
     */
    public AbstractButton(String text) {
        this(text, 0, 0, 0, 0);
    }

    /**
     * Create a button with given text, width and height.
     *
     * @param text   The text to be rendered in the center of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public AbstractButton(String text, int width, int height) {
        this(text, 0, 0, width, height);
    }

    /**
     * Create a button with given text, positions, width and height.
     *
     * @param text   The text to be rendered in the center of the button.
     * @param x      The x position from left of parent component.
     * @param y      The y position from bottom of parent component.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public AbstractButton(String text, int x, int y, int width, int height) {
        this.text = text;
        setLocation(x, y);
        setSize(width, height);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHovered() {
        return hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
}
