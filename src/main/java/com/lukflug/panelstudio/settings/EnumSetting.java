package com.lukflug.panelstudio.settings;

/**
 * A setting representing an enumeration.
 *
 * @author lukflug
 */
public interface EnumSetting {
	/**
	 * Cycle through the values of the enumeration.
	 */
	void increment();

	/**
	 * Get the current value.
	 *
	 * @return the name of the current enum value
	 */
	String getValueName();
}
