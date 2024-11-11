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
package dev.shoheiyamagiwa.unite.gui.theme.impl;

import dev.shoheiyamagiwa.unite.gui.theme.FontStyle;
import dev.shoheiyamagiwa.unite.gui.theme.FontWeight;
import dev.shoheiyamagiwa.unite.gui.theme.Theme;
import dev.shoheiyamagiwa.unite.gui.theme.Typography;
import dev.shoheiyamagiwa.unite.utils.Color;

/**
 * A basic implementation of theme system for Fluent UI.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public class FluentUITheme implements Theme {
    /**
     * The default font family for this theme.
     */
    private static final String FONT_FAMILY = "Segoe UI";

    @Override
    public Typography getTypography() {
        return new Typography() {
            @Override
            public FontStyle getBodyStyle() {
                return new FontStyle() {
                    @Override
                    public String getFamilyName() {
                        return FONT_FAMILY;
                    }

                    @Override
                    public FontWeight getWeight() {
                        return FontWeight.REGULAR;
                    }

                    @Override
                    public int getSize() {
                        return 14;
                    }

                    @Override
                    public int getLineHeight() {
                        return 20;
                    }
                };
            }

            @Override
            public FontStyle getSubtitleStyle() {
                return new FontStyle() {
                    @Override
                    public String getFamilyName() {
                        return FONT_FAMILY;
                    }

                    @Override
                    public FontWeight getWeight() {
                        return FontWeight.SEMI_BOLD;
                    }

                    @Override
                    public int getSize() {
                        return 20;
                    }

                    @Override
                    public int getLineHeight() {
                        return 28;
                    }
                };
            }

            @Override
            public FontStyle getTitleStyle() {
                return new FontStyle() {
                    @Override
                    public String getFamilyName() {
                        return FONT_FAMILY;
                    }

                    @Override
                    public FontWeight getWeight() {
                        return FontWeight.SEMI_BOLD;
                    }

                    @Override
                    public int getSize() {
                        return 28;
                    }

                    @Override
                    public int getLineHeight() {
                        return 36;
                    }
                };
            }
        };
    }

    @Override
    public Color getSurface() {
        return new Color(0.2F, 0.2F, 0.2F, 0.5F);
    }
}
