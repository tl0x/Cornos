package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.NoRender;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    public void aPR(Particle particle, CallbackInfo ci) {
        if (ModuleRegistry.search(NoRender.class).isEnabled() && NoRender.particles.isEnabled()) ci.cancel();
    }
}
