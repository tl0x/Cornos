/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: ThunderAura
# Created by constantin at 15:52, Mär 06 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.COMBAT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.render.RenderableLine;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;
import java.util.Random;

public class ThunderAura extends Module {
    Entity goal = null;
    int tick = 0;
    RenderableLine rlCurrent;

    public ThunderAura() {
        super("ThunderAura", "Wanna completely destroy someone at pvp? ! This does not work well in confined areas !", MType.COMBAT);
        this.mconf.add(new Num("delay", 3, 20, 1));
    }

    @Override
    public void onExecute() {
        tick++;
        if (rlCurrent != null) RenderHelper.addToQueue(rlCurrent);
        assert Cornos.minecraft.player != null;
        Vec3d init = Cornos.minecraft.player.getPos().add(-4, -4, -4);
        Vec3d goal = init.add(8, 8, 8);
        Box selector = new Box(init, goal);
        int delay = (int) ((Num) this.mconf.getByName("delay")).getValue();
        assert Cornos.minecraft.world != null;
        for (Entity e : Cornos.minecraft.world.getEntities()) {
            if (e.getBoundingBox().intersects(selector) && e.getUuid() != Cornos.minecraft.player.getUuid() && e.isAttackable()) {
                this.goal = e;
                break;
            }
        }
        if (tick > delay) {
            tick = 0;
            if (this.goal == null) return;
            if (!this.goal.isAlive()) {
                this.goal = null;
                return;
            }
            Random r = new Random();
            double offX = (r.nextDouble() * 6) - 3;
            double offZ = (r.nextDouble() * 6) - 3;
            Vec3d attv = this.goal.getPos().add(offX, 0, offZ);
            Vec3d playerP = Cornos.minecraft.player.getPos();
            double coordZ = playerP.y;
            for (int i = (int) Math.min(playerP.y + 7, 255); i > playerP.y; i--) {
                if (!Cornos.minecraft.world.getBlockState(new BlockPos(attv.x, i, attv.z)).getBlock().is(Blocks.AIR)) {
                    coordZ = i + 1;
                    break;
                }
            }
            //if (Cornos.minecraft.player.getAir() == 0) Cornos.minecraft.player.jump();
            rlCurrent = new RenderableLine(playerP, new Vec3d(attv.x, coordZ, attv.z), 255, 50, 50, 255);
            Cornos.minecraft.player.updatePosition(attv.x, coordZ, attv.z);

            //ClientHelper.sendChat(attv.x+", "+coordZ+", "+attv.z);
            PlayerInteractEntityC2SPacket p = new PlayerInteractEntityC2SPacket(this.goal, true);
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(p);
        }
        super.onExecute();
    }

    @Override
    public void onDisable() {
        this.goal = null;
        rlCurrent = null;
        super.onDisable();
    }
}
