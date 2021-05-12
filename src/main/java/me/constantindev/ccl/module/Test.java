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
import me.constantindev.ccl.etc.ms.ModuleType;

public class Test extends Module {
    public Test() {
        super("TestModule", "j", ModuleType.HIDDEN);
    }

    @Override
    public void onExecute() {
        super.onExecute();
    }
}
