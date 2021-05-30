package me.zeroX150.cornos.etc.config;

import java.awt.*;

public enum Colors {
	INACTIVE(new Color(18, 33, 33)), ACTIVE(new Color(33, 47, 47)), BACKGROUND(new Color(0, 26, 29)), TEXT(
			new Color(0xFFFFFF)), WIDGET(new Color(20, 20, 20, 200)), NOTIFICATION(
					new Color(30, 30, 30)), GUIBACKGROUND(new Color(0, 0, 0, 60));

	private final Color current;

	Colors(Color c) {
		current = c;
	}

	public Color get() {
		return current;
	}
}
