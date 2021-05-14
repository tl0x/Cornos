package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.WORLD.Scaffold;
import me.constantindev.ccl.module.ext.Safewalk;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "clipAtLedge", at = @At("HEAD"), cancellable = true)
    public void cAL(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity context = (PlayerEntity) ((Object) this);
        if (Cornos.minecraft.player == null) return;
        if (context.getUuid() == Cornos.minecraft.player.getUuid()) {
            if ((Scaffold.preventFalling.isEnabled() && ModuleRegistry.search(Scaffold.class).isEnabled()) || ModuleRegistry.search(Safewalk.class).isEnabled()) {
                cir.setReturnValue(true);
            }
        }
    }
}
