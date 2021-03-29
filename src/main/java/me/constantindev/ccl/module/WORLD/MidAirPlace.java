package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.render.RenderableBlock;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class MidAirPlace extends Module {
    RenderableBlock rlB = null;
    int timeout = 20;

    public MidAirPlace() {
        super("MidAirPlace", "Allows you to place blocks midair", MType.WORLD);
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        if (rlB != null) this.rbq.add(rlB);
        super.onRender(ms, td);
    }

    @Override
    public void onExecute() {
        HitResult hr = Cornos.minecraft.crosshairTarget;
        if (!(hr instanceof BlockHitResult)) return;
        BlockPos bp = ((BlockHitResult) hr).getBlockPos();
        Vec3d vec3d = new Vec3d(bp.getX(), bp.getY(), bp.getZ());
        rlB = new RenderableBlock(vec3d, 255, 50, 50, 255);
        if (Cornos.minecraft.options.keyUse.isPressed()) {
            timeout--;
            if (timeout != 19 && timeout > 0) return;
            PlayerInteractBlockC2SPacket p = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, (BlockHitResult) hr);
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(p);
        } else timeout = 20;
        super.onExecute();
    }
}
