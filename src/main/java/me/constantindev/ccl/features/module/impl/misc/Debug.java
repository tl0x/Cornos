/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Debug
# Created by constantin at 12:17, Mär 19 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.module.impl.misc;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

public class Debug extends Module {
    public Debug() {
        super("Debug", "uhh", ModuleType.HIDDEN);
    }

    @Override
    public void onExecute() {
        STL.notifyUser("[D] MinecraftClient.currentScreen = " + Cornos.minecraft.currentScreen);
        Camera c = BlockEntityRenderDispatcher.INSTANCE.camera;
        STL.notifyUser("[D] Camera: " + c);
        STL.notifyUser("[D] CameraRot: " + c.getRotation());

        super.onExecute();
    }
}
