package me.constantindev.ccl.mixin;

import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.Vibe;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "getSkyProperties", at = @At("HEAD"), cancellable = true)
    public void bruh(CallbackInfoReturnable<SkyProperties> cir) {
        if (ModuleRegistry.search(Vibe.class).isEnabled()) {
            cir.setReturnValue(Vibe.getProps());
        }
    }

    @Inject(method = "getColor", at = @At("HEAD"), cancellable = true)
    public void bruh1(BlockPos pos, ColorResolver colorResolver, CallbackInfoReturnable<Integer> cir) {
        if (ModuleRegistry.search(Vibe.class).isEnabled() && Vibe.rgbBlocks.isEnabled()) {
            cir.setReturnValue(Vibe.calculateBP(pos));
        }
    }
}
