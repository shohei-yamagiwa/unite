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
package dev.shoheiyamagiwa.unite.utils;

/**
 * This class holds some utility methods related to math.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public class MathUtil {
    /**
     * Returns the value that is in ranges of {@code min} and {@code max}
     *
     * @param value The original value
     * @param min   The minimum value
     * @param max   The maximum value
     * @return The value that is in ranges of {@code min} and {@code max}
     */
    public static int value(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * Returns the value that is in ranges of {@code min} and {@code max}
     *
     * @param value The original value
     * @param min   The minimum value
     * @param max   The maximum value
     * @return The value that is in ranges of {@code min} and {@code max}
     */
    public static float value(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     * Returns the value that is in ranges of {@code min} and {@code max}
     *
     * @param value The original value
     * @param min   The minimum value
     * @param max   The maximum value
     * @return The value that is in ranges of {@code min} and {@code max}
     */
    public static double value(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
