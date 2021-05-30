/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoBreakDelay
# Created by constantin at 20:27, Mär 19 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

public class FastUse extends Module {
    public MConfToggleable fastuse = new MConfToggleable("fastuse", true);
    public MConfToggleable nbd = new MConfToggleable("nobreakdelay", true);

    public FastUse() {
        super("Fast", "Makes you go brrr", ModuleType.WORLD);
        this.mconf.add(fastuse);
        this.mconf.add(nbd);
    }

    @Override
    public void onExecute() {
        if (nbd.isEnabled()) {
            if (Cornos.minecraft.options.keyAttack.isPressed()) {
                if (Cornos.minecraft.crosshairTarget == null)
                    return;
                if (Cornos.minecraft.getNetworkHandler() == null)
                    return;
                if (Cornos.minecraft.crosshairTarget.getType().equals(HitResult.Type.BLOCK)) {
                    BlockHitResult bhr = (BlockHitResult) Cornos.minecraft.crosshairTarget;
                    PlayerActionC2SPacket p = new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, bhr.getBlockPos(), Direction.DOWN);
                    PlayerActionC2SPacket p1 = new PlayerActionC2SPacket(
                            PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, bhr.getBlockPos(), Direction.DOWN);
                    Cornos.minecraft.getNetworkHandler().sendPacket(p);
                    Cornos.minecraft.getNetworkHandler().sendPacket(p1);

                }
            }
        }
        super.onExecute();
    }
}
