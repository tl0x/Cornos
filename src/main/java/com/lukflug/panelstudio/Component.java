package com.lukflug.panelstudio;

/**
 * Interface representing a drawable object in the GUI.
 *
 * @author lukflug
 */
public interface Component {
	/**
	 * Get the caption of the component. String may also be empty.
	 *
	 * @return the caption of the component
	 */
	String getTitle();

	/**
	 * Redraw component on screen. The current height of the component should be set
	 * by this method via {@link Context#setHeight(int)}.
	 *
	 * @param context
	 *            the {@link Context} for the component
	 */
	void render(Context context);

	/**
	 * Should be called by the parent when a mouse button state changes. The current
	 * height of the component should be set by this method via
	 * {@link Context#setHeight(int)}.
	 *
	 * @param context
	 *            the {@link Context} for the component
	 * @param button
	 *            the button that changed its state
	 * @see Interface#LBUTTON
	 * @see Interface#RBUTTON
	 */
	void handleButton(Context context, int button);

	/**
	 * Should be called by the parent when a key is typed. The current height of the
	 * component should be set by this method via {@link Context#setHeight(int)}.
	 *
	 * @param context
	 *            the {@link Context} for the component
	 * @param scancode
	 *            the scancode for the key that was typed
	 */
	void handleKey(Context context, int scancode);

	/**
	 * Should be called by the parent when the mouse wheel is scrolled. The current
	 * height of the component should be set by this method via
	 * {@link Context#setHeight(int)}.
	 *
	 * @param context
	 *            the {@link Context} for the component
	 * @param diff
	 *            the amount by which the wheel was moved
	 */
	void handleScroll(Context context, int diff);

	/**
	 * Get the current height via {@link Context#setHeight(int)}.
	 *
	 * @param context
	 *            the {@link Context} for the component
	 */
	void getHeight(Context context);

	/**
	 * Should be called by the parent when the panel is shown. The current height of
	 * the component should be set by this method via
	 * {@link Context#setHeight(int)}.
	 *
	 * @param context
	 *            the {@link Context} for the component
	 */
	void enter(Context context);

	/**
	 * Should be called by the parent when the panel is hidden. The current height
	 * of the component should be set by this method via
	 * {@link Context#setHeight(int)}.
	 *
	 * @param context
	 *            the {@link Context} for the component
	 */
	void exit(Context context);

	/**
	 * Called when a parent loses focus.
	 */
	void releaseFocus();
}
