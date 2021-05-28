/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Gamemode
# Created by saturn5VFive
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import net.minecraft.world.GameMode;

public class Gamemode extends Command {
    public Gamemode() {
        super("Gamemode", "Updates your gamemode client side", new String[]{"gamemode", "gm", "gmode"});
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length != 1) {
            STL.notifyUser("Specify a gamemode");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "survival":
                STL.notifyUser("Updated gamemode to survival");
                Cornos.minecraft.player.setGameMode(GameMode.SURVIVAL);
                Cornos.minecraft.interactionManager.setGameMode(GameMode.SURVIVAL);
                break;

            case "creative":
                STL.notifyUser("Updated gamemode to creative");
                Cornos.minecraft.player.setGameMode(GameMode.CREATIVE);
                Cornos.minecraft.interactionManager.setGameMode(GameMode.CREATIVE);
                break;

            //sdakjsldkjdksjgdfg
            case "s":
                STL.notifyUser("Updated gamemode to survival");
                Cornos.minecraft.player.setGameMode(GameMode.SURVIVAL);
                Cornos.minecraft.interactionManager.setGameMode(GameMode.SURVIVAL);
                break;

            case "c":
                STL.notifyUser("Updated gamemode to creative");
                Cornos.minecraft.player.setGameMode(GameMode.CREATIVE);
                Cornos.minecraft.interactionManager.setGameMode(GameMode.CREATIVE);
                break;
        }
        super.onExecute(args);
    }
}
