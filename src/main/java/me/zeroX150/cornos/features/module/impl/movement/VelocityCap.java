/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: VelocityCap
# Created by constantin at 12:46, Mär 27 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.util.math.Vec3d;

public class VelocityCap extends Module {
    MConfNum x = new MConfNum("x", 5, 10, 0, "max x vel");
    MConfNum y = new MConfNum("y", 5, 10, 0, "max y vel");
    MConfNum z = new MConfNum("z", 5, 10, 0, "max z vel");

    public VelocityCap() {
        super("VelocityCap", "makes you go slower", ModuleType.MOVEMENT);
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
