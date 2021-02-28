package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.event.EventHelper;
import me.constantindev.ccl.etc.event.EventType;
import me.constantindev.ccl.etc.event.arg.PacketApplyEvent;
import me.constantindev.ccl.etc.event.arg.PacketEvent;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket.class)
public class AntiOffhandCrash {
    @Shadow
    private SoundEvent sound;

    @Inject(method = "apply", cancellable = true, at = @At("HEAD"))
    public void overrideApply(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        boolean flag = EventHelper.BUS.invokeEventCall(EventType.ONPACKETHANDLE, new PacketApplyEvent((Packet<?>) this,clientPlayPacketListener));
        if (!flag) ci.cancel();
        /*
        if (this.sound == SoundEvents.ITEM_ARMOR_EQUIP_GENERIC && ModuleRegistry.getByName("antioffhandcrash").isOn.isOn())

           ci.cancel();

         */
    }
}
