package com.lukflug.panelstudio;

import java.awt.*;

/**
 * Interface representing a single panel configuration state.
 *
 * @author lukflug
 */
public interface PanelConfig {
    /**
     * Store the position of the panel.
     *
     * @param position the current position of the panel
     */
    void savePositon(Point position);

    /**
     * Load the position of the point.
     *
     * @return the stored position
     */
    Point loadPosition();

    /**
     * Store the state of the panel.
     *
     * @param state whether the panel is open
     */
    void saveState(boolean state);

    /**
     * Load the state of the panel.
     *
     * @return the stored panel state
     */
    boolean loadState();
}
