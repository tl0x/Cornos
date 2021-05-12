/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: QuickMove
# Created by constantin at 17:19, Mär 03 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.ColoredBlockEntry;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.ms.ModuleType;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.etc.render.RenderableLine;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuickMove extends Module {
    List<ColoredBlockEntry> bpl = new ArrayList<>();
    int counter = 0;

    public QuickMove() {
        super("QuickMove", "Blink but only movement", ModuleType.MOVEMENT);
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            PacketEvent pe = (PacketEvent) event;
            if (pe.packet instanceof PlayerMoveC2SPacket && parent.isEnabled()) event.cancel();
        });
        this.mconf.add(new MConfNum("delay", 20, 100, 0));
    }

    @Override
    public void onEnable() {

        super.onEnable();
    }

    @Override
    public void onRender(MatrixStack ms, float td) {
        ColoredBlockEntry latest = null;
        for (ColoredBlockEntry bp : bpl) {
            if (latest == null) latest = bp;
            Color c = bp.c;
            rlq.add(new RenderableLine(latest.bp, bp.bp, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha(), 2));
            latest = bp;
        }
        counter++;
        if (counter > (ModuleRegistry.budgetGraphicsInstance.isEnabled() ? 80 : 10)) {
            assert Cornos.minecraft.player != null;
            bpl.add(new ColoredBlockEntry(Cornos.minecraft.player.getPos(), new Color(CConf.latestRGBVal)));
            counter = 0;
        }
        super.onRender(ms, td);
    }


    @Override
    public void onDisable() {
        new Thread(() -> {
            if (Cornos.minecraft.player == null) return;
            Cornos.minecraft.player.setNoGravity(true);
            for (ColoredBlockEntry bp : bpl) {
                try {
                    Thread.sleep((long) ((MConfNum) this.mconf.getByName("delay")).getValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Cornos.minecraft.getNetworkHandler() == null) break;
                if (Cornos.minecraft.player == null) continue;
                Cornos.minecraft.player.updatePosition(bp.bp.getX(), bp.bp.getY(), bp.bp.getZ());

            }
            bpl.clear();
            Cornos.minecraft.player.setNoGravity(false);
        }).start();
        super.onDisable();
    }
}
