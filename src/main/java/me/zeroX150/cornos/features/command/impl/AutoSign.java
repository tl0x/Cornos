package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.command.Command;
import me.zeroX150.cornos.gui.screen.AutoSignEditor;

public class AutoSign extends Command {
	public AutoSign() {
		super("AutoSign", "GUI for autosign", new String[]{"autosign", "as"});
	}

	@Override
	public void onExecute(String[] args) {
		new Thread(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Cornos.minecraft.openScreen(new AutoSignEditor());
		}).start();
		super.onExecute(args);
	}
}
