package me.constantindev.ccl.command;

import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.client.MinecraftClient;

public class VClip extends Command {
    public VClip() {
        super("VClip", "Allows you to teleport up or down through blocks. Works best on non solid blocks.", new String[]{"vclip"});
    }

    @Override
    public void onExecute(String[] args) {

        double amount;

        if (args.length == 0) {
            ClientHelper.sendChat("Please provide a number.");
            return;
        }

        try {
            amount = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            ClientHelper.sendChat("Invalid number.");
            return;
        }

        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.setBoundingBox(MinecraftClient.getInstance().player.getBoundingBox().offset(0D, amount, 0D));
        MinecraftClient.getInstance().player.setPos(MinecraftClient.getInstance().player.getX(), MinecraftClient.getInstance().player.getY() + amount, MinecraftClient.getInstance().player.getZ());

        ClientHelper.sendChat("Teleported you " + amount + " blocks.");
    }
}
