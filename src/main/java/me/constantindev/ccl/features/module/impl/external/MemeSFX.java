/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: MemeSFX
# Created by constantin at 01:36, Mär 31 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.ModuleType;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public class MemeSFX extends Module {
    public MemeSFX() {
        super("MemeSFX", "Taco bell", ModuleType.FUN);
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            if (!ModuleRegistry.search(MemeSFX.class).isEnabled()) return;
            PacketEvent pe = (PacketEvent) event;
            if (pe.packet instanceof PlayerInteractEntityC2SPacket) {
                assert Cornos.minecraft.player != null;
                Cornos.minecraft.player.playSound(Cornos.HITMARKER_SOUND, 1f, 1f);
            }
        });
    }
}
