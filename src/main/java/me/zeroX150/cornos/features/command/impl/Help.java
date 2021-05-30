package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.gui.screen.DocsScreen;

public class Help extends Command {
	public Help() {
		super("Help", "Shows all commands and modules", new String[]{"h", "help", "man", "?", "commands", "modules"});
	}

	@Override
	public void onExecute(String[] args) {
		new Thread(() -> {
			STL.sleep(10);
			Cornos.minecraft.openScreen(new DocsScreen(true));
		}).start();
		super.onExecute(args);
	}
}
