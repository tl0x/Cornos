package me.constantindev.ccl.mixin;

import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.Range;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    public void gRD(CallbackInfoReturnable<Float> cir) {
        if (ModuleRegistry.search(Range.class).isEnabled()) {
            double range = Range.range.getValue();
            cir.setReturnValue((float) range);
        }
    }
}
