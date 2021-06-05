package me.zeroX150.cornos.features.module.impl.world;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.etc.event.EventHelper;
import me.zeroX150.cornos.etc.event.EventType;
import me.zeroX150.cornos.etc.event.arg.PacketEvent;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

import java.util.ArrayList;
import java.util.List;

public class MassUse extends Module {
    List<Packet<?>> dontRepeat = new ArrayList<>();
    MConfNum amount = new MConfNum("amount", 10, 100, 1, "The amount of times to use the item at once");

    public MassUse() {
        super("MassUse", "Uses an item a lot of times at once", ModuleType.WORLD);
        mconf.add(amount);
        Module p = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            if (!p.isEnabled()) return;
            PacketEvent pe = (PacketEvent) event;
            if (dontRepeat.contains(pe.packet)) return;
            if (pe.packet instanceof PlayerInteractBlockC2SPacket || pe.packet instanceof PlayerInteractItemC2SPacket) {
                dontRepeat.add(pe.packet);
                for (int i = 0; i < amount.getValue(); i++) {
                    Cornos.minecraft.getNetworkHandler().sendPacket(pe.packet);
                }
                dontRepeat.remove(pe.packet);
            }
        });
    }
}
