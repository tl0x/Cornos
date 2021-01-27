package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Objects;

public class NoFallModule extends Module {
    public NoFallModule() {
        super("nofall", "prevents you from dying from falling");
    }

    @Override
    public void onExecute() {
        try {
            Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket(true));
        } catch (Exception ignored) {
        }
        super.onExecute();
    }
}
