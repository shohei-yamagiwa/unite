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
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public abstract class AbstractLabel extends Container implements Drawable {
    /**
     * The text to be rendered.
     */
    private String text;

    /**
     * Creates a label with given text.
     *
     * @param text The text to be rendered as label.
     */
    public AbstractLabel(String text) {
        this(text, 0, 0);
    }

    /**
     * Creates a label with given text and positions.
     *
     * @param text The text to be rendered as label.
     * @param x    The x position of the label.
     * @param y    The y position of the label.
     */
    public AbstractLabel(String text, int x, int y) {
        this.text = text;
        setLocation(x, y);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
