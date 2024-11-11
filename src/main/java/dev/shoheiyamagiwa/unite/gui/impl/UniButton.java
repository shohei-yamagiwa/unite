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

import dev.shoheiyamagiwa.unite.graphics.font.AbstractFont;
import dev.shoheiyamagiwa.unite.graphics.AbstractGraphics;
import dev.shoheiyamagiwa.unite.graphics.font.impl.AsciiFont;
import dev.shoheiyamagiwa.unite.gui.components.AbstractButton;
import dev.shoheiyamagiwa.unite.utils.Color;

import java.awt.*;

/**
 * {@code UniButton} is an implementation of {@link AbstractButton}.
 * Users must instantiate this class to create basic buttons.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public class UniButton extends AbstractButton {
    // FIXME Temporary placed here.
    private AbstractFont font;

    public UniButton(String text) {
        super(text);
    }

    public UniButton(String text, int width, int height) {
        super(text, width, height);
    }

    public UniButton(String text, int x, int y, int width, int height) {
        super(text, x, y, width, height);
    }

    @Override
    public void draw(AbstractGraphics g) {
        if (font == null) {
            font = new AsciiFont(new Font("JetBrains Mono", Font.PLAIN, 16));
        }
        g.setFont(font);
        g.drawRect(x, y, width, height, Color.WHITE);
        g.drawText(text, x + (width / 2) - g.getTextWidth(text) / 2, y + (height / 2) - g.getTextHeight(text) / 2, Color.BLACK);
    }

    @Override
    public void dispose(AbstractGraphics g) {
        if (font != null) {
            font.dispose();
        }
    }
}
