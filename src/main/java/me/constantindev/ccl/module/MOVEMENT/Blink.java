package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;

import java.util.ArrayList;
import java.util.List;

public class Blink extends Module {
    List<Packet<?>> pl = new ArrayList<>();
    boolean blockPackets = false;
    public Blink() {
        super("Blink", "makes you lag intentionally", MType.MOVEMENT);
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND,event -> {
            PacketEvent pe = (PacketEvent) event;
            if (blockPackets && !(pe.packet instanceof KeepAliveC2SPacket)) {
                pe.cancel();
                pl.add(pe.packet);
            }
        });
    }

    @Override
    public void onEnable() {
        blockPackets = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        blockPackets = false;
        for (Packet<?> packet : pl) {
            Cornos.minecraft.getNetworkHandler().sendPacket(packet);
        }
        super.onDisable();
    }
}
