package me.constantindev.ccl.module;

import me.constantindev.ccl.CornClient;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.apache.logging.log4j.Level;

import java.util.Objects;

public class MassBreak extends Module {
    public MassBreak() {
        super("MassBreak", "Breaks a lot if you break a block");
        this.mconf.add(new ModuleConfig.ConfigKey("radius", "3"));
    }

    @Override
    public void onExecute() {
        int rad = 3;
        try {
            rad = Integer.parseInt(this.mconf.getByName("radius").value);
        } catch (Exception ignored) {
            this.mconf.getByName("radius").setValue("3");
        }
        BlockPos latest;
        try {
            assert MinecraftClient.getInstance().crosshairTarget != null;
            latest = ((BlockHitResult) MinecraftClient.getInstance().crosshairTarget).getBlockPos();
        } catch (Exception ignored) {
            return;
        }
        if (MinecraftClient.getInstance().options.keyAttack.isPressed()) {
            CornClient.log(Level.INFO, "bruh");
            for (int x = 0; x < rad; x++) {
                for (int y = 0; y < rad; y++) {
                    for (int z = 0; z < rad; z++) {
                        BlockPos c = latest.add(x - (rad / 2), y - (rad / 2), z - (rad / 2));
                        if (c.equals(latest)) continue;
                        Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, c, Direction.UP));
                        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, c, Direction.UP));
                    }
                }
            }
            Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, latest, Direction.UP));
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, latest, Direction.UP));
        }
        super.onExecute();
    }
}
