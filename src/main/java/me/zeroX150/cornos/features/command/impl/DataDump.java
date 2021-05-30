package me.zeroX150.cornos.features.command.impl;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.command.Command;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;

public class DataDump extends Command {
    public DataDump() {
        super("DataDump", "Dumps data from the selected item", new String[]{"datadump", "ddump", "idump", "itemdump"});
    }

    @Override
    public void onExecute(String[] args) {
        assert Cornos.minecraft.player != null;
        ItemStack mainHandStack = Cornos.minecraft.player.inventory.getMainHandStack();
        if (mainHandStack.getItem() == Items.AIR) {
            STL.notifyUser("Please hold an item");
            return;
        }
        CompoundTag tag = mainHandStack.getTag();
        if (tag == null || !mainHandStack.hasTag()) {
            STL.notifyUser("No data on item");
            return;
        }
        String bruh = tag.toString();
        STL.notifyUser(bruh);
    }
}
