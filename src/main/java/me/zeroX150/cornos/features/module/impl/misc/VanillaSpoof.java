package me.zeroX150.cornos.features.module.impl.misc;

import io.netty.buffer.Unpooled;
import me.zeroX150.cornos.etc.event.EventHelper;
import me.zeroX150.cornos.etc.event.EventType;
import me.zeroX150.cornos.etc.event.arg.PacketEvent;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import me.zeroX150.cornos.mixin.packet.CustomPayloadC2SPacketAccessor;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

import java.nio.charset.StandardCharsets;

public class VanillaSpoof extends Module {
    public VanillaSpoof() {
        super("VanillaSpoof", "Tells the server you're on vanilla", ModuleType.MISC);
        Module p = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETSEND, event -> {
            if (!p.isEnabled()) return;
            PacketEvent pe = (PacketEvent) event;
            if (pe.packet instanceof CustomPayloadC2SPacket) {
                CustomPayloadC2SPacketAccessor a = (CustomPayloadC2SPacketAccessor) pe.packet;
                if (a.getChannel().equals(CustomPayloadC2SPacket.BRAND)) {
                    a.setData(new PacketByteBuf(Unpooled.buffer()).writeString("vanilla"));
                } else if (a.getData().toString(StandardCharsets.UTF_8).toLowerCase().contains("fabric"))
                    event.cancel();
                System.out.println(a.getData().toString(StandardCharsets.UTF_8));
            }
        });
    }
}
