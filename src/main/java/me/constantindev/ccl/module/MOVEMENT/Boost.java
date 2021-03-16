/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Boost
# Created by constantin at 16:29, Mär 15 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.util.math.Vec3d;

public class Boost extends Module {
    Num strength = new Num("strength", 4.0, 30, 1);

    public Boost() {
        super("Boost", "Boosts you upwards", MType.MOVEMENT);
        this.mconf.add(strength);
    }

    @Override
    public void onExecute() {
        this.setEnabled(false);
        double mtp = strength.getValue();
        assert Cornos.minecraft.player != null;
        Vec3d mtpV = Cornos.minecraft.player.getRotationVector().multiply(mtp);
        Cornos.minecraft.player.addVelocity(mtpV.x, mtpV.y, mtpV.z);
        super.onExecute();
    }
}
