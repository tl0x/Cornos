/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Rename
# Created by constantin at 18:45, Mär 26 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;
import net.minecraft.text.LiteralText;
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
            ClientHelper.sendChat("man u gotta hold sum");
            return;
        }
        if (args.length == 0) {
            ClientHelper.sendChat("Homie ima need the new name");
            return;
        }
        CompoundTag compoundTag = is.getOrCreateSubTag("display");
        compoundTag.putString("Name", Text.Serializer.toJson(Text.of("§r"+String.join(" ",args).replaceAll("&", "§"))));

        super.onExecute(args);
    }
}
