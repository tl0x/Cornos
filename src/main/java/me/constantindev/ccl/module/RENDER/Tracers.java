/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Tracers
# Created by constantin at 20:57, Mär 04 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.RENDER;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.render.RenderableBlock;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class Tracers extends Module {
    public Tracers() {
        super("PlayerInfo", "Shows nearby entities and/or players", MType.RENDER);
        this.mconf.add(new Toggleable("Entities", true));
        this.mconf.add(new Toggleable("Players", true));
        this.mconf.add(new Num("Distance", 100, 1000, 10));
        this.mconf.add(new Toggleable("debug", false));
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        boolean e = ((Toggleable) this.mconf.getByName("Entities")).isEnabled();
        boolean p = ((Toggleable) this.mconf.getByName("Players")).isEnabled();
        boolean debug = ((Toggleable) this.mconf.getByName("debug")).isEnabled();
        double dist = ((Num) this.mconf.getByName("Distance")).getValue();
        assert Cornos.minecraft.world != null;
        for (Entity currE : Cornos.minecraft.world.getEntities()) {
            assert Cornos.minecraft.player != null;
            if (currE.getUuid() == Cornos.minecraft.player.getUuid()) continue;
            if (currE.distanceTo(Cornos.minecraft.player) < dist) {
                double distance = currE.distanceTo(Cornos.minecraft.player);
                int rInit = 255;
                int gInit;
                double rInit2 = rInit * (distance / dist);
                gInit = (int) Math.round(rInit - rInit2);
                if (debug) {
                    currE.setCustomNameVisible(true);
                    currE.setCustomName(Text.of(rInit2 + " " + gInit));
                }
                if ((currE instanceof OtherClientPlayerEntity) && !p) continue;
                else if (!e && !(currE instanceof OtherClientPlayerEntity)) continue;
                Vec3d off = new Vec3d(currE.getWidth(), currE.getHeight(), currE.getWidth());
                RenderableBlock rb = new RenderableBlock(currE.getPos().add(off.multiply(-.5)).add(0, off.y / 2, 0), gInit, (int) rInit2, 50, 255, off);
                //RenderHelper.addToQueue(rb);
                this.rbq.add(rb);
            }
        }
        super.onRender(ms, td);
    }
}
