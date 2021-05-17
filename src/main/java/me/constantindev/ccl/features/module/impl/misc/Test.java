/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.module.impl.misc;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.SilentRotations;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.util.math.Vec3d;

public class Test extends Module {
    double r = 0;
    public Test() {
        super("TestModule", "j", ModuleType.HIDDEN);
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        r+=0.1;
        r %= 360;
        double rr = Math.toRadians(r);
        double rs = Math.sin(rr);
        double rc = Math.cos(rr);
        Vec3d a = new Vec3d(rs,0,rc).multiply(10);
        Vec3d lp = Cornos.minecraft.player.getPos().add(a);
        SilentRotations.doSilentRotation(EntityAnchorArgumentType.EntityAnchor.EYES,lp);
        super.onRender(ms, td);
    }
}
