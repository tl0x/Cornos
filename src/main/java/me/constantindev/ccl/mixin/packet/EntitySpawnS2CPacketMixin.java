package me.constantindev.ccl.mixin.packet;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySpawnS2CPacket.class)
public class EntitySpawnS2CPacketMixin {
    @Shadow
    private EntityType<?> entityTypeId;

    @Inject(method = "apply", at = @At("TAIL"), cancellable = true)
    public void a(ClientPlayPacketListener clientPlayPacketListener, CallbackInfo ci) {
        if (this.entityTypeId == EntityType.AREA_EFFECT_CLOUD && ModuleRegistry.getByName("antiblockban").isEnabled()) {
            ci.cancel();
        }
    }
}
