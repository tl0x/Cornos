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
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;

public class Test extends Module {
    public static SkyProperties prop = new SkyProperties(128, true, SkyProperties.SkyType.NORMAL, false, true) {
        @Override
        public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
            return color.normalize();
        }

        @Override
        public boolean useThickFog(int camX, int camY) {
            return true;
        }
    };

    public Test() {
        super("TestModule", "Poggê", MType.HIDDEN);
    }

    @Override
    public void onEnable() {


        super.onEnable();
    }
}
