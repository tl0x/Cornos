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
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import me.zeroX150.cornos.gui.clickgui.ClickGUI;

public class Test extends Module {
    public Test() {
        super("TestModule", "j", ModuleType.HIDDEN);
    }

    @Override
    public void onEnable() {
        setEnabled(false);
        Cornos.minecraft.openScreen(new ClickGUI());
    }
}
