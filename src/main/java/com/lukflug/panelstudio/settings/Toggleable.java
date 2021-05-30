package com.lukflug.panelstudio.settings;

/**
 * Interface representing a boolean value that can be toggled.
 *
 * @author lukflug
 */
public interface Toggleable {
    /**
     * Toggle the boolean value.
     */
    void toggle();

    /**
     * Get the boolean value.
     *
     * @return the value
     */
    boolean isOn();
}
