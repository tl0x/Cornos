package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class AutoSneak extends Module {
    public AutoSneak() {
        super("AutoSneak", "Automatically sneaks on an edge", ModuleType.MOVEMENT);
    }

    @Override
    public void onFastUpdate() {
        Box bounding = Cornos.minecraft.player.getBoundingBox();
        bounding = bounding.offset(0, -1, 0);
        bounding = bounding.expand(0.3);
        //bounding = bounding.contract(0.3);
        boolean sneak = false;
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                double xScale = x / 3d + .5;
                double zScale = z / 3d + .5;
                BlockPos current = Cornos.minecraft.player.getBlockPos().add(x, -1, z);
                BlockState bs = Cornos.minecraft.world.getBlockState(current);
                if (bs.isAir() && bounding.contains(new Vec3d(current.getX() + xScale, current.getY() + 1, current.getZ() + zScale))) {
                    sneak = true;
                    break;
                }
            }
        }
        //STL.notifyUser(sneak+"");
        boolean previousState = InputUtil.isKeyPressed(Cornos.minecraft.getWindow().getHandle(), Cornos.minecraft.options.keySneak.getDefaultKey().getCode());
        if (Cornos.minecraft.player.isOnGround()) Cornos.minecraft.options.keySneak.setPressed(sneak || previousState);
    }
}
