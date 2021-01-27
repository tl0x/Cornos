package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Objects;

public class NukerModule extends Module {
    int current = 0;

    public NukerModule() {
        super("nuker", "bonk");
        this.mconf.add(new ModuleConfig.ConfigKey("range", "5"));
    }

    @Override
    public void onExecute() {
        int range = 5;
        try {
            range = Integer.parseInt(this.mconf.getByName("range").value);
        } catch (Exception exc) {
            this.mconf.getByName("range").setValue("5");
        }
        current++;
        if (current > 2) current = 0;
        else return;
        assert MinecraftClient.getInstance().player != null;
        BlockPos original = MinecraftClient.getInstance().player.getBlockPos();
        for (int x = -range; x < range + 1; x++) {
            for (int y = -range; y < range + 1; y++) {
                for (int z = -range; z < range + 1; z++) {
                    BlockPos bp2 = original.add(x, y, z);
                    if (bp2.equals(original.down())) continue;
                    BlockState bstate = MinecraftClient.getInstance().player.world.getBlockState(bp2);
                    if (bstate.getBlock().getName().asString().equalsIgnoreCase("air")) continue;
                    Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, bp2, Direction.UP));
                    MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, bp2, Direction.UP));

                }
            }
        }
        Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, original.down(), Direction.UP));
        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, original.down(), Direction.UP));
        super.onExecute();
    }
}
