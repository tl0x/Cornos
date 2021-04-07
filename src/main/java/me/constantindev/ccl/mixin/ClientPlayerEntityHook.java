package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityHook {

    @Inject(at = @At("INVOKE"), method = "pushOutOfBlocks", cancellable = true)
    private void pushOutOfBlocks(double x, double d, CallbackInfo ci) {
        if (ModuleRegistry.getByName("Freecam").isOn.isOn()) {
            ci.cancel();
        }
    }

    @Inject(at = {@At("HEAD")}, method = "isSubmergedInWater", cancellable = true)
    private void isSubmergedInWater(CallbackInfoReturnable<Boolean> cir) {
        if (ModuleRegistry.getByName("Freecam").isOn.isOn()) {
            cir.setReturnValue(false);
        }
    }


}
