/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoJumpingCooldown
# Created by constantin at 13:14, Mär 27 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import me.zeroX150.cornos.mixin.ILivingEntityAccessor;

public class NoJumpingCooldown extends Module {
    public NoJumpingCooldown() {
        super("NoJumpingCooldown", "fast jumping", ModuleType.MOVEMENT);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        ((ILivingEntityAccessor) Cornos.minecraft.player).setJumpingCooldown(0);
        super.onExecute();
    }
}
