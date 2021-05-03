package me.constantindev.ccl.module.WORLD;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.helper.Renderer;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class MidAirPlace extends Module {
    int timeout = 20;

    public MidAirPlace() {
        super("MidAirPlace", "Allows you to place blocks midair", ModuleType.WORLD);
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        HitResult hr = Cornos.minecraft.crosshairTarget;
        if (hr instanceof BlockHitResult) {
            BlockPos bp = ((BlockHitResult) hr).getBlockPos();
            Vec3d vec3d = new Vec3d(bp.getX(), bp.getY(), bp.getZ());
            Renderer.renderBlockOutline(vec3d, new Vec3d(1, 1, 1), 50, 50, 255, 255);
            if (Cornos.minecraft.options.keyUse.isPressed()) {
                timeout--;
                if (timeout != 19 && timeout > 0) return;
                PlayerInteractBlockC2SPacket p = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, (BlockHitResult) hr);
                Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(p);
            } else timeout = 20;
        }
        super.onRender(ms, td);
    }

    @Override
    public void onExecute() {
        super.onExecute();
    }
}
