/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Confuse
# Created by constantin at 08:37, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.COMBAT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.Raycast;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class Confuse extends Module {
    MultiOption mode = new MultiOption("mode", "randomtp", new String[]{"randomtp", "switch", "circle"});
    Num delay = new Num("delay", 3, 20, 1);
    Toggleable moveThroughBlocks = new Toggleable("moveThroughBlocks", false);
    int delayWaited = 0;
    Entity target;

    public Confuse() {
        super("Confuse", "Makes your entities shit themselves", MType.COMBAT);
        this.mconf.add(mode);
        this.mconf.add(delay);
        this.mconf.add(moveThroughBlocks);
    }

    @Override
    public void onExecute() {
        delayWaited++;
        if (delayWaited < delay.getValue()) return;
        delayWaited = 0;
        assert Cornos.minecraft.player != null;
        Vec3d sel1 = Cornos.minecraft.player.getPos().add(-4, -4, -4);
        Vec3d sel2 = sel1.add(8, 8, 8);
        Box selector = new Box(sel1, sel2);
        assert Cornos.minecraft.world != null;
        for (Entity e : Cornos.minecraft.world.getEntities()) {
            if (e.getUuid() == Cornos.minecraft.player.getUuid()) continue;
            if (!e.isAlive()
                    || !e.isAttackable()) continue;
            if (e.getBoundingBox().intersects(selector)) {
                target = e;
                break;
            }
        }
        if (target == null) return;
        if (!target.isAlive()) {
            target = null;
            return;
        }
        Vec3d entityPos = target.getPos();
        Vec3d playerPos = Cornos.minecraft.player.getPos();
        Random r = new Random();
        switch (mode.value) {
            case "randomtp":
                double x = r.nextDouble() * 6 - 3;
                double y = 0;
                double z = r.nextDouble() * 6 - 3;
                Vec3d addend = new Vec3d(x, y, z);
                Vec3d goal = entityPos.add(addend);
                if (!Cornos.minecraft.world.getBlockState(new BlockPos(goal.x, goal.y, goal.z)).getBlock().is(Blocks.AIR)) {
                    goal = new Vec3d(x, playerPos.y, z);
                }
                if (Cornos.minecraft.world.getBlockState(new BlockPos(goal.x, goal.y, goal.z)).getBlock().is(Blocks.AIR)) {
                    Raycast rc = new Raycast(Cornos.minecraft.player.getPos(), goal);
                    if (rc.passesThroughBlock(1, true) && !moveThroughBlocks.isEnabled()) {
                        delayWaited = (int) (delay.getValue() - 1);
                        break;
                    }
                    Cornos.minecraft.player.updatePosition(goal.x, goal.y, goal.z);
                } else {
                    delayWaited = (int) (delay.getValue() - 1);
                }
                break;
            case "switch":
                Vec3d diff = entityPos.subtract(playerPos);
                Vec3d diff1 = new Vec3d(MathHelper.clamp(diff.x, -3, 3), MathHelper.clamp(diff.y, -3, 3), MathHelper.clamp(diff.z, -3, 3));
                Vec3d goal2 = entityPos.add(diff1);
                Raycast rc1 = new Raycast(playerPos, goal2);
                if (rc1.passesThroughBlock(1, true) && !moveThroughBlocks.isEnabled()) {
                    delayWaited = (int) (delay.getValue() - 1);
                    break;
                }
                Cornos.minecraft.player.updatePosition(goal2.x, goal2.y, goal2.z);
                break;
            case "circle":
                break;
        }
        super.onExecute();
    }
}
