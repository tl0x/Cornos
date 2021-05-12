/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoJumpingCooldown
# Created by constantin at 13:14, Mär 27 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.ModuleType;
import me.constantindev.ccl.mixin.LivingEntityAccessor;

public class NoJumpingCooldown extends Module {
    public NoJumpingCooldown() {
        super("NoJumpingCooldown", "fast jumping", ModuleType.MOVEMENT);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        ((LivingEntityAccessor) Cornos.minecraft.player).setJumpingCooldown(0);
        super.onExecute();
    }
}
