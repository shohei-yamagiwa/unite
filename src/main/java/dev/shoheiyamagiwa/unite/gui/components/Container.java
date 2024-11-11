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

import java.util.ArrayList;
import java.util.List;

/**
 * A container is a set of graphical components.
 *
 * @author Shohei Yamagiwa
 * @since 0.1
 */
public abstract class Container extends Component {
    /**
     * All child components that belong to the container.
     */
    private final List<Component> children = new ArrayList<>();

    /**
     * Adds new component to the container.
     *
     * @param component The component to be added.
     */
    public void add(Component component) {
        children.add(component);
        component.setParent(this);
    }

    /**
     * Gets all child components.
     *
     * @return All child components belong to the container.
     */
    public List<Component> getChildren() {
        return children;
    }
}
