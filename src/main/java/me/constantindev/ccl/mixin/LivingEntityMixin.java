package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.MOVEMENT.Jesus;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    public void cWOF(Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if (((Object) this) instanceof ClientPlayerEntity) {
            ClientPlayerEntity e = (ClientPlayerEntity) ((Object) this);
            if (Cornos.minecraft.player.getUuid().equals(e.getUuid())) {
                System.out.println("Called");
                if (ModuleRegistry.getByName("jesus").isOn.isOn() && Jesus.mode.value.equalsIgnoreCase("solid"))
                    cir.setReturnValue(true);
            }
        }
    }
}
