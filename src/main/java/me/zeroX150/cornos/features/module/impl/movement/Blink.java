package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfMultiOption;
import me.zeroX150.cornos.etc.event.EventHelper;
import me.zeroX150.cornos.etc.event.EventType;
import me.zeroX150.cornos.etc.event.arg.PacketEvent;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blink extends Module {
    List<Packet<?>> pl = new ArrayList<>();
    boolean blockPackets = false;
    MConfMultiOption mode = new MConfMultiOption("mode", "delay", new String[]{"delay", "drop"}, "Mode to change packets by");
    long enableTime = 0;

    public Blink() {
        super("Blink", "Tired of a good internet connection?", ModuleType.MOVEMENT);
        mconf.add(mode);
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            PacketEvent pe = (PacketEvent) event;
            if (blockPackets && !(pe.packet instanceof KeepAliveC2SPacket)) {
                pe.cancel();
                if (mode.value.equals("delay"))
                    pl.add(pe.packet);
            }
        });
    }

    @Override
    public void onEnable() {
        blockPackets = true;
        enableTime = System.currentTimeMillis();
        super.onEnable();
    }

    @Override
    public String getContext() {
        return STL.roundToNTh((System.currentTimeMillis() - enableTime) / 1000f, 2) + "s" + (pl.size() == 0 ? "" : " " + pl.size() + "p");
    }

    @Override
    public void onDisable() {
        blockPackets = false;
        for (Packet<?> packet : pl) {
            Objects.requireNonNull(Cornos.minecraft.getNetworkHandler()).sendPacket(packet);
        }
        pl.clear();
        super.onDisable();
    }
}
