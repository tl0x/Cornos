/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: CombatUpdateHook
# Created by constantin at 01:24, Mär 31 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CombatEventS2CPacket.class)
public class CombatEventS2CPacketMixin {
    @Inject(method = "<init>(Lnet/minecraft/entity/damage/DamageTracker;Lnet/minecraft/network/packet/s2c/play/CombatEventS2CPacket$Type;Lnet/minecraft/text/Text;)V", at = @At("TAIL"))
    public void init(DamageTracker damageTracker, CombatEventS2CPacket.Type type, Text deathMessage, CallbackInfo ci) {
        if (type == CombatEventS2CPacket.Type.ENTITY_DIED && ModuleRegistry.getByName("memesfx").isEnabled()) {
            assert Cornos.minecraft.player != null;
            Cornos.minecraft.player.playSound(Cornos.BONG_SOUND, 1f, 1f);
        }
    }
}
