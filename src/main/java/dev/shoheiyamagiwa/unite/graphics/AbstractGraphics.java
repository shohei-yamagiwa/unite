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
package dev.shoheiyamagiwa.unite.graphics;

import dev.shoheiyamagiwa.unite.graphics.font.AbstractFont;
import dev.shoheiyamagiwa.unite.utils.Color;

/**
 * {@code Graphics} holds all renderer contexts that allow an application to draw onto components.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public abstract class AbstractGraphics {
    /**
     * The current font to rendering the text.
     */
    protected AbstractFont font;

    /**
     * Clear the buffer.
     */
    public abstract void clear();

    /**
     * Dispose the graphics and its used data
     */
    public abstract void dispose();

    /**
     * Draws a rect with given dimensions and color.
     *
     * @param x      The left position of the rect.
     * @param y      The top position of the rect.
     * @param width  The width of the rect.
     * @param height The height of the rect.
     * @param color  The color of the rect.
     */
    public abstract void drawRect(int x, int y, int width, int height, Color color);

    /**
     * Draws a text with given dimensions and color.
     *
     * @param text  The text to be rendered.
     * @param x     The left position of the text.
     * @param y     The top position of the text.
     * @param color The color of the text.
     */
    public abstract void drawText(String text, int x, int y, Color color);

    /**
     * Gets the width of the rendered text.
     *
     * @param text The text to be rendered.
     * @return The width of the rendered text.
     */
    public abstract int getTextWidth(String text);

    /**
     * Gets the height of the rendered text.
     *
     * @param text The text to be rendered.
     * @return The height of the rendered text.
     */
    public abstract int getTextHeight(String text);

    /**
     * Draws an image at given positions on the screen.
     *
     * @param image The image to be rendered.
     * @param x     The x position of the texture.
     * @param y     The y position of the texture.
     */
    public abstract void drawImage(AbstractImage image, int x, int y);

    /**
     * Set the font to use for text rendering.
     *
     * @param font The font to be rendered on the screen.
     */
    public void setFont(AbstractFont font) {
        this.font = font;
    }
}
