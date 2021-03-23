/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Raycast
# Created by constantin at 09:02, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.render.RenderableLine;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class Raycast {
    Vec3d s;
    Vec3d e;
    Vec3d diff;
    double dist;
    List<RenderableLine> lastVisualization = new ArrayList<>();

    public Raycast(Vec3d start, Vec3d end) {
        s = start;
        e = end;
        diff = e.subtract(s);
        dist = s.distanceTo(e);
    }

    public boolean passesThroughBlock(int laziness, boolean visualize) {
        lastVisualization.clear();
        Vec3d current = s;
        Vec3d last = null;
        double lastDist = Double.MAX_VALUE;
        boolean passes = false;
        double perX = diff.x / 100 * laziness;
        double perY = diff.y / 100 * laziness;
        double perZ = diff.z / 100 * laziness;
        while (lastDist > current.distanceTo(e)) {
            lastDist = current.distanceTo(e);
            current = current.add(perX, perY, perZ);
            BlockPos bp = new BlockPos(current.x, current.y, current.z);
            assert Cornos.minecraft.world != null;
            if (!Cornos.minecraft.world.getBlockState(bp).getBlock().is(Blocks.AIR)) {
                passes = true;
                if (!visualize) break;
                if (last != null) {
                    lastVisualization.add(new RenderableLine(last, current, 255, 50, 50, 255, 2));
                }
            } else {
                if (last != null) {
                    lastVisualization.add(new RenderableLine(last, current, 50, 255, 50, 255, 2));
                }
            }
            for (RenderableLine line : lastVisualization) {
                RenderHelper.addToQueue(line);
            }
            last = current;
        }
        return passes;
    }
}