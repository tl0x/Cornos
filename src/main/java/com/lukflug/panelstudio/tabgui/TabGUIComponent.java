package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Component;

/**
 * Interface representing a part of a TabGUI.
 *
 * @author lukflug
 */
public interface TabGUIComponent extends Component {
	/**
	 * Boolean indicating to the parent whether to render this component
	 * highlighted.
	 *
	 * @return whether this component is active
	 */
	boolean isActive();

	/**
	 * Called by parent to indicate this component has been selected by the user.
	 *
	 * @return whether this component is requesting focus
	 */
	boolean select();
}
