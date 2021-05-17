/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoJumpingCooldown
# Created by constantin at 13:14, Mär 27 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.module.impl.movement;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;
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
