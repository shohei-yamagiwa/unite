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
package dev.shoheiyamagiwa.unite.graphics.font.impl;

import dev.shoheiyamagiwa.unite.graphics.AbstractImage;
import dev.shoheiyamagiwa.unite.graphics.font.AbstractFont;
import dev.shoheiyamagiwa.unite.graphics.font.Glyph;
import dev.shoheiyamagiwa.unite.graphics.impl.Image;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code AsciiFont} class is an implementation of {@link AbstractFont} for Ascii code
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public class AsciiFont extends AbstractFont {
    /**
     * Printable character list in ASCII.
     */
    public static final char[] PRINTABLE_CODES = new char[]{32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255};

    /**
     * The image that stores all standard glyphs.
     */
    private final AbstractImage fontImage;

    /**
     * All standard glyphs.
     */
    private final Map<Character, Glyph> glyphs = new HashMap<>();

    /**
     * Creates new AsciiFont from given font info.
     *
     * @param font The font of the text to be rendered on the screen.
     */
    public AsciiFont(Font font) {
        fontImage = createFontImage(font);
    }

    @Override
    public void dispose() {
        if (fontImage != null) {
            fontImage.dispose();
        }
    }

    @Override
    public int getWidth(CharSequence text) {
        int currentLineWidth = 0;
        int maxWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            /* Update the maximum width and reset current line width */
            if (c == '\n') {
                maxWidth = Math.max(maxWidth, currentLineWidth);
                currentLineWidth = 0;
                continue;
            }
            // Skip the carriage return.
            if (c == '\r') {
                continue;
            }
            // Update line width
            Glyph glyph = glyphs.get(c);
            currentLineWidth += glyph.getWidth();
        }
        maxWidth = Math.max(maxWidth, currentLineWidth);
        return maxWidth;
    }

    @Override
    public int getHeight(CharSequence text) {
        int currentLineHeight = 0;
        int totalHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            /* Update the total height and reset current line height */
            if (c == '\n') {
                totalHeight += currentLineHeight;
                currentLineHeight = 0;
                continue;
            }
            /* Skip the carriage return */
            if (c == '\r') {
                continue;
            }
            // Update the line height.
            Glyph glyph = glyphs.get(c);
            currentLineHeight = Math.max(currentLineHeight, glyph.getHeight());
        }
        totalHeight += currentLineHeight;
        return totalHeight;
    }

    /**
     * Creates concatenated image of font glyph to be rendered with given font.
     *
     * @param font The font to be used in rendering text.
     * @return One concatenated image of font glyph to be rendered with given font.
     */
    private AbstractImage createFontImage(Font font) {
        int imageWidth = 0;
        int imageHeight = 0;

        /* Loop through all printable ascii codes */
        for (char c : PRINTABLE_CODES) {
            BufferedImage ch = createCharImage(font, c);
            if (ch == null) { // If character image is null, specified font doesn't contain the character.
                continue;
            }
            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        /* Create image that aligns standard chars horizontally */
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int x = 0;

        /* Loop through all printable ascii codes */
        for (char c : PRINTABLE_CODES) {
            BufferedImage charImage = createCharImage(font, c);
            if (charImage == null) { // If character image is null, specified font does not contain the character.
                continue;
            }
            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            /* Register the glyph information */
            Glyph glyph = new Glyph(x, image.getHeight() - charHeight, charWidth, charHeight, 0.0F);
            glyphs.put(c, glyph);

            /* Draw the character on the image */
            g.drawImage(charImage, x, 0, null);

            // Update x coordinate to the next start position.
            x += charWidth;
        }
        /* Flip image horizontal to set the origin to bottom left */
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

        /* Get width and height of the image */
        int width = image.getWidth();
        int height = image.getHeight();

        /* Get pixel data of image */
        int[] pixels = new int[height * width];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        /* Put pixel data into a ByteBuffer */
        ByteBuffer buffer = MemoryUtil.memAlloc(height * width * 4);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                /* Pixel as RGBA: 0xAARRGGBB */
                int pixel = pixels[h * width + w];
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (pixel & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        // Prepare the buffer for use.
        buffer.flip();

        // Create font image
        AbstractImage fontImage = new Image(buffer, width, height);

        // Free the memory.
        MemoryUtil.memFree(buffer);
        return fontImage;
    }

    /**
     * Creates the image of given character.
     *
     * @param font The font to create character image with.
     * @param c    The character to be created as an image.
     * @return Buffer image for the given character.
     */
    private BufferedImage createCharImage(Font font, char c) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get width and height of given char */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();
        if (charWidth == 0) {
            return null;
        }

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setPaint(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public AbstractImage getFontImage() {
        return fontImage;
    }

    public Glyph getGlyph(char c) {
        return glyphs.get(c);
    }
}
