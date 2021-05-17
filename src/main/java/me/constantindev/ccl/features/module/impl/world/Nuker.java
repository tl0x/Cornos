package me.constantindev.ccl.features.module.impl.world;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Objects;

public class Nuker extends Module {
    int current = 0;

    public Nuker() {
        super("Nuker", "massbreak on drugs", ModuleType.WORLD);
        this.mconf.add(new MConfNum("range", 5, 10, 0));
    }

    @Override
    public void onExecute() {
        int range = (int) ((MConfNum) this.mconf.getByName("range")).getValue();
        current++;
        if (current > 2) current = 0;
        else return;
        assert Cornos.minecraft.player != null;
        BlockPos original = Cornos.minecraft.player.getBlockPos();
        for (int x = -range; x < range + 1; x++) {
            for (int y = -range; y < range + 1; y++) {
                for (int z = -range; z < range + 1; z++) {
                    BlockPos bp2 = original.add(x, y, z);
                    if (bp2.equals(original.down())) continue;
                    BlockState bstate = Cornos.minecraft.player.world.getBlockState(bp2);
                    if (bstate.getBlock().getName().asString().equalsIgnoreCase("air")) continue;
                    Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, bp2, Direction.UP));
                    Cornos.minecraft.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, bp2, Direction.UP));

                }
            }
        }
        Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, original.down(), Direction.UP));
        Cornos.minecraft.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, original.down(), Direction.UP));
        super.onExecute();
    }
}
