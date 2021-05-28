/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: CrashPlayer
# Created by saturn5VFive
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;

public class CrashPlayer extends Command {
    public CrashPlayer() {
        super("PCrash", "crash someones game [NEEDS OP]", new String[]{"pCrash", "pcrash", "crashplayer", "CrashPlayer"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length != 1) {
            STL.notifyUser("Specify a player"); //system
            return;
        }
        Cornos.minecraft.player.sendChatMessage("/execute as " + args[0] + " at @s run particle flame ~ ~ ~ 1 1 1 0 999999999 force @s"); // add particles to their client
        super.onExecute(args);
    }
}
