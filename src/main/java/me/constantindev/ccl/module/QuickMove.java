/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: QuickMove
# Created by constantin at 17:19, Mär 03 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.ColoredBlockEntry;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.render.RenderableLine;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuickMove extends Module {
    List<ColoredBlockEntry> bpl = new ArrayList<>();
    int counter = 0;

    public QuickMove() {
        super("QuickMove", "Lets you draw a tail and then quickly travels alongside of it (Basically blink but poggers)", MType.MOVEMENT);
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            PacketEvent pe = (PacketEvent) event;
            if (pe.packet instanceof PlayerMoveC2SPacket && parent.isOn.isOn()) event.cancel();
        });
        this.mconf.add(new Num("delay", 20, 100, 0));
    }

    @Override
    public void onEnable() {

        super.onEnable();
    }

    @Override
    public void onExecute() {
        ColoredBlockEntry latest = null;
        for (ColoredBlockEntry bp : bpl) {
            if (latest == null) latest = bp;
            Color c = bp.c;
            RenderHelper.addToQueue(new RenderableLine(latest.bp, bp.bp, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
            latest = bp;
        }
        counter++;
        if (counter > 2) {
            assert Cornos.minecraft.player != null;
            bpl.add(new ColoredBlockEntry(Cornos.minecraft.player.getPos(), new Color(ClientConfig.latestRGBVal)));
            counter = 0;
        }
        super.onExecute();
    }

    @Override
    public void onDisable() {
        new Thread(() -> {
            Cornos.minecraft.player.setNoGravity(true);
            for (ColoredBlockEntry bp : bpl) {
                try {
                    Thread.sleep((long) ((Num) this.mconf.getByName("delay")).getValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Cornos.minecraft.getNetworkHandler() == null) break;
                assert Cornos.minecraft.player != null;
                Cornos.minecraft.player.updatePosition(bp.bp.getX(), bp.bp.getY(), bp.bp.getZ());

            }
            bpl.clear();
            Cornos.minecraft.player.setNoGravity(false);
        }).start();
        super.onDisable();
    }
}
