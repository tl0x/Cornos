package com.lukflug.panelstudio.theme;

import java.awt.*;

/**
 * Interface to provide a means for GUI methods to access color setting set by
 * the user.
 *
 * @author lukflug
 */
public interface ColorScheme {
    /**
     * Returns the color that should be used to indicate that a component is active.
     *
     * @return the active color
     */
    Color getActiveColor();

    /**
     * Returns the color that should be used to indicate that a component is
     * inactive.
     *
     * @return the inactive color
     */
    Color getInactiveColor();

    /**
     * Returns the background color.
     *
     * @return the background color
     */
    Color getBackgroundColor();

    /**
     * Returns the outline color.
     *
     * @return the outline color
     */
    Color getOutlineColor();

    /**
     * Returns the text color.
     *
     * @return the font color
     */
    Color getFontColor();

    /**
     * Returns the alpha for partially transparent components of the GUI.
     *
     * @return the opacity
     */
    int getOpacity();
}
