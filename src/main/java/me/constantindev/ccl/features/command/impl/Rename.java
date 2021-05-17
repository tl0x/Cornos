/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Rename
# Created by constantin at 18:45, Mär 26 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;

public class Rename extends Command {
    public Rename() {
        super("Rename", "Renames the current item", new String[]{"rename", "re", "rn", "name", "setname"});
    }

    @Override
    public void onExecute(String[] args) {
        assert Cornos.minecraft.player != null;
        ItemStack is = Cornos.minecraft.player.inventory.getStack(Cornos.minecraft.player.inventory.selectedSlot);
        if (is.isEmpty()) {
            STL.notifyUser("man u gotta hold sum");
            return;
        }
        if (args.length == 0) {
            STL.notifyUser("Homie ima need the new name");
            return;
        }
        CompoundTag compoundTag = is.getOrCreateSubTag("display");
        compoundTag.putString("Name", Text.Serializer.toJson(Text.of("§r" + String.join(" ", args).replaceAll("&", "§"))));

        super.onExecute(args);
    }
}
