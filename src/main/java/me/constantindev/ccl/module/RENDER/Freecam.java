package me.constantindev.ccl.module.RENDER;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.util.math.Vec3d;

public class Freecam extends Module {
    public static Vec3d currentOffset = new Vec3d(0, 4, 0);
    Num speed = new Num("speed", 1, 2, 0);
    Vec3d startloc;

    public Freecam() {
        super("Freecam", "Become a ghost and leave your body", MType.MISC);
        this.mconf.add(speed);
    }

    @Override
    public void onEnable() {
        currentOffset = new Vec3d(0, 0, 0);
        if (Cornos.minecraft.player == null) return;
        startloc = Cornos.minecraft.player.getPos();
        super.onEnable();
    }

    @Override
    public void onExecute() {
        Cornos.minecraft.player.setVelocity(0, 0, 0);
        Cornos.minecraft.player.updatePosition(startloc.x, startloc.y, startloc.z);
        if (Cornos.minecraft.currentScreen == null) {
            Vec3d vec3d = Cornos.minecraft.player.getRotationVector();
            Vec3d considerSpeed = vec3d.multiply(speed.getValue());
            if (Cornos.minecraft.options.keyJump.isPressed()) {
                currentOffset = currentOffset.add(0, -speed.getValue(), 0);
            }
            if (Cornos.minecraft.options.keySneak.isPressed()) {
                currentOffset = currentOffset.add(0, speed.getValue(), 0);
            }
            if (Cornos.minecraft.options.keyForward.isPressed()) {
                currentOffset = currentOffset.add(considerSpeed.multiply(-1));
            }
            if (Cornos.minecraft.options.keyBack.isPressed()) {
                currentOffset = currentOffset.add(considerSpeed);
            }
        }
        super.onExecute();
    }
}
