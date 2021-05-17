/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Invsee
# Created by constantin at 23:43, Mär 13 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.command.Command;
import net.minecraft.screen.slot.SlotActionType;

public class Drop extends Command {
    public Drop() {
        super("Drop", "Drops all items in your inv", new String[]{"drop", "d", "throw"});
    }

    @Override
    public void onExecute(String[] args) {
        for (int i = 0; i < 46; i++) {
            assert Cornos.minecraft.interactionManager != null;
            Cornos.minecraft.interactionManager.clickSlot(0, i, 1, SlotActionType.THROW, Cornos.minecraft.player);
        }

        super.onExecute(args);
    }
}
