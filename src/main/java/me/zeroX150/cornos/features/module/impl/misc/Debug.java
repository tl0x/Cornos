/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Debug
# Created by constantin at 12:17, Mär 19 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.misc;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

public class Debug extends Module {
    public Debug() {
        super("Debug", "uhh", ModuleType.HIDDEN);
    }

    @Override
    public void onExecute() {
        STL.notifyUser("[D] VEL: "+Cornos.minecraft.player.getVelocity().toString());

        super.onExecute();
    }
}
