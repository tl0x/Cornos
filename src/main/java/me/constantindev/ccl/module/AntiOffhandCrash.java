package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketApplyEvent;
import me.constantindev.ccl.etc.ms.MType;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;

public class AntiOffhandCrash extends Module {
    public AntiOffhandCrash() {
        super("AntiOffhandCrash", "Prevents you from getting bonked by some random mf with inertia", MType.EXPLOIT);
        Module parent = this;
        EventHelper.BUS.registerEvent(EventType.ONPACKETHANDLE, event -> {
            if (!parent.isOn.isOn()) return;
            PacketApplyEvent pae = (PacketApplyEvent) event;
            if (pae.packet instanceof PlaySoundS2CPacket) {
                if (((PlaySoundS2CPacket) pae.packet).getSound() == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC)
                    event.cancel();
            }
        });


    }
}
