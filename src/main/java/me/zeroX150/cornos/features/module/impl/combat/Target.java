/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Target
# Created by constantin at 16:56, Mär 15 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.combat;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.etc.helper.SilentRotations;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Target extends Module {
    MConfToggleable players = new MConfToggleable("players", true);
    MConfToggleable entities = new MConfToggleable("entities", false);
    MConfToggleable silentrot = new MConfToggleable("silentrotations", false);

    public Target() {
        super("Target", "Helps you aim at shit", ModuleType.COMBAT);
        this.mconf.add(players);
        this.mconf.add(entities);
        this.mconf.add(silentrot);
    }

    @Override
    public void onFastUpdate() {
        assert Cornos.minecraft.player != null;
        Vec3d init = Cornos.minecraft.player.getPos().add(-4, -4, -4);
        Vec3d fin = init.add(8, 8, 8);
        Box selector = new Box(init, fin);
        assert Cornos.minecraft.world != null;
        for (Entity e : Cornos.minecraft.world.getEntities()) {
            if (!(e instanceof LivingEntity)) continue;
            if ((e instanceof PlayerEntity) && !players.isEnabled()) continue;
            if (!entities.isEnabled() && !(e instanceof PlayerEntity)) continue;
            LivingEntity le = (LivingEntity) e;
            if (Cornos.friendsManager.getFriends().containsKey(e.getName().asString())) {
                return;
            }
            if (e.getBoundingBox().intersects(selector) && e.getUuid() != Cornos.minecraft.player.getUuid() && e.isAttackable() && !le.isDead()) {
                Vec3d finP = e.getPos().add(0, e.getHeight() / 2, 0);
                if (silentrot.isEnabled()) {
                    SilentRotations.doSilentRotation(EntityAnchorArgumentType.EntityAnchor.EYES, finP);
                } else {
                    Cornos.minecraft.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, finP);
                }
                break;
            }
        }
        super.onFastUpdate();
    }
}
