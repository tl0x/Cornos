/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Test
# Created by constantin at 21:26, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.misc;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import me.zeroX150.cornos.mixin.IMinecraftClientAccessor;
import me.zeroX150.cornos.mixin.IRenderTickCounterAccessor;

public class Test extends Module {
    float oldTickTime;

    public Test() {
        super("TestModule", "j", ModuleType.HIDDEN);
    }

    @Override
    public void onEnable() {
        IRenderTickCounterAccessor accessor = (IRenderTickCounterAccessor) ((IMinecraftClientAccessor) Cornos.minecraft).getRenderTickCounter();
        STL.notifyUser("Current tick time is: " + accessor.getTickTime());
        oldTickTime = accessor.getTickTime();
        accessor.setTickTime(1f);
    }

    @Override
    public void onDisable() {
        IRenderTickCounterAccessor accessor = (IRenderTickCounterAccessor) ((IMinecraftClientAccessor) Cornos.minecraft).getRenderTickCounter();
        accessor.setTickTime(oldTickTime);
    }
}
