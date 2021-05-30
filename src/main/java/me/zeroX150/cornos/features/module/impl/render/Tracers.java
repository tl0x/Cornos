/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: Tracers
# Created by constantin at 20:57, Mär 04 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.zeroX150.cornos.features.module.impl.render;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class Tracers extends Module {
    MConfToggleable entities = new MConfToggleable("Entities", true);
    MConfToggleable players = new MConfToggleable("Players", true);
    MConfToggleable tracers = new MConfToggleable("Tracers", true);
    MConfToggleable debug = new MConfToggleable("debug", false);
    MConfNum dist = new MConfNum("Distance", 100, 1000, 10);

    public Tracers() {
        super("Tracers", "Tired of getting creeped up against?", ModuleType.RENDER);
        this.mconf.add(entities);
        this.mconf.add(players);
        this.mconf.add(dist);
        this.mconf.add(tracers);
        this.mconf.add(debug);
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        boolean e = entities.isEnabled();
        boolean p = players.isEnabled();
        boolean debug = this.debug.isEnabled();
        double dist = this.dist.getValue();
        assert Cornos.minecraft.world != null;
        for (Entity currE : Cornos.minecraft.world.getEntities()) {
            assert Cornos.minecraft.player != null;
            if (currE.getUuid() == Cornos.minecraft.player.getUuid())
                continue;
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
                if ((currE instanceof OtherClientPlayerEntity) && !p)
                    continue;
                else if (!e && !(currE instanceof OtherClientPlayerEntity))
                    continue;
                Vec3d off = new Vec3d(currE.getWidth(), currE.getHeight(), currE.getWidth());
                Vec3d currEV = currE.getPos();

                Renderer.renderBlockOutline(currEV.add(off.multiply(-.5)).add(0, off.y / 2, 0), off, gInit,
                        (int) rInit2, 50, 255);
                // RenderHelper.addToQueue(rb);
                if (((MConfToggleable) this.mconf.getByName("Tracers")).isEnabled()
                        && !ModuleRegistry.budgetGraphicsInstance.isEnabled()) {
                    Color c = Color.GREEN;
                    if (TargetHUD.current != null && TargetHUD.current.getUuid().equals(currE.getUuid()))
                        c = new Color(0, 255, 136);
                    else if (currE instanceof HostileEntity)
                        c = Color.YELLOW;
                    else if (currE instanceof PlayerEntity)
                        c = Color.RED;
                    else if (currE instanceof ItemEntity)
                        c = Color.CYAN;
                    Renderer.renderLine(currEV.add(0, currE.getHeight() / 2, 0), Renderer.getCrosshairVector(), c, 1);
                }
            }
        }
        super.onRender(ms, td);
    }
}
