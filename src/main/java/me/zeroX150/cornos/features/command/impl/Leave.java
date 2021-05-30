/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Leave
# Created by constantin at 22:59, Mär 04 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.command.Command;

public class Leave extends Command {
	public Leave() {
		super("Leave", "Leaves the server", new String[]{"leave", "l", "quit"});
	}

	@Override
	public void onExecute(String[] args) {
		if (Cornos.minecraft.world == null)
			return;
		Cornos.minecraft.world.disconnect();
		super.onExecute(args);
	}
}
