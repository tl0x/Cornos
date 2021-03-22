/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Debug
# Created by constantin at 12:17, Mär 19 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.item.Items;

public class Debug extends Module {
    public Debug() {
        super("Debug", "uhh", MType.HIDDEN);
    }

    @Override
    public void onExecute() {
        ClientHelper.sendChat("[DEBUG] MinecraftClient.currentScreen = "+Cornos.minecraft.currentScreen);
        super.onExecute();
    }
}
