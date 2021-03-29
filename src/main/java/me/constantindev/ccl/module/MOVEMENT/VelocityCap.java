/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: VelocityCap
# Created by constantin at 12:46, Mär 27 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.util.math.Vec3d;

public class VelocityCap extends Module {
    Num x = new Num("x", 5, 10, 0);
    Num y = new Num("y", 5, 10, 0);
    Num z = new Num("z", 5, 10, 0);

    public VelocityCap() {
        super("VelocityCap", "Prevents you from going faster than the velocity you specified", MType.MOVEMENT);
        this.mconf.add(x);
        this.mconf.add(y);
        this.mconf.add(z);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        Vec3d vel = Cornos.minecraft.player.getVelocity();
        double nx = vel.x;
        double ny = vel.y;
        double nz = vel.z;
        if (Math.abs(nx) > x.getValue()) {
            nx = nx < 0 ? -x.getValue() : x.getValue();
        }
        if (Math.abs(ny) > y.getValue()) {
            ny = ny < 0 ? -y.getValue() : y.getValue();
        }
        if (Math.abs(nz) > z.getValue()) {
            nz = nz < 0 ? -z.getValue() : z.getValue();
        }
        Cornos.minecraft.player.setVelocity(nx, ny, nz);
        super.onExecute();
    }
}
