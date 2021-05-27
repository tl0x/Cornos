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
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
import me.constantindev.ccl.gui.clickgui.ClickGUI;

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
