/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class Test extends Module {
    public Test() {
        super("TestModule", "Poggê", MType.HIDDEN);
    }

    @Override
    public void onHudRender(MatrixStack ms, float td) {
        Vec3d inst = new Vec3d(50, 100, 0);

        RenderHelper.renderRoundedQuad(inst, inst.add(5, 5, 5), 5, Color.RED);
        super.onHudRender(ms, td);
    }
}
