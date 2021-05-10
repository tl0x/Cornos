/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: TriggerBot
# Created by constantin at 19:44, Mär 31 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.COMBAT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {
    MConfNum delay = new MConfNum("delay", 2, 20, 0);
    int delayWaited = 0;

    public TriggerBot() {
        super("TriggerBot", "Automatically attacks shit you are aiming at", ModuleType.COMBAT);
        this.mconf.add(delay);
    }

    @Override
    public void onExecute() {
        if (Cornos.minecraft.crosshairTarget instanceof EntityHitResult) {
            delayWaited++;
            if (delayWaited > delay.getValue()) {
                delayWaited = 0;
            } else return;
            EntityHitResult ehr = (EntityHitResult) Cornos.minecraft.crosshairTarget;
            if (!ehr.getEntity().isAttackable() || !ehr.getEntity().isAlive()) return;
            assert Cornos.minecraft.interactionManager != null;
            if (ehr.getEntity() instanceof PlayerEntity && Cornos.friendsManager.getFriends().containsKey(ehr.getEntity().getName().asString())) {
                return;
            }
            Cornos.minecraft.interactionManager.attackEntity(Cornos.minecraft.player, ehr.getEntity());

        }
        super.onExecute();
    }
}
