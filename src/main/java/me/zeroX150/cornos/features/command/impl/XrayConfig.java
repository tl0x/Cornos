package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.gui.screen.XrayConfigScreen;

public class XrayConfig extends Command {
	public XrayConfig() {
		super("XrayConfig", "Configuration for xray", new String[]{"xrayconfig", "xray", "xconf"});
	}

	@Override
	public void onExecute(String[] args) {
		new Thread(() -> {
			STL.sleep(10);
			Cornos.minecraft.openScreen(new XrayConfigScreen());
		}).start();
		super.onExecute(args);
	}
}
