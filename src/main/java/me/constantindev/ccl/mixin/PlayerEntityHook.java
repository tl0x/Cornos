package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.Freecam;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityHook {

    @Inject(at = {@At("INVOKE")}, method = "tick")
    private void tick(CallbackInfo ci) {
        if (ModuleRegistry.getByName("Freecam").isOn.isOn()) {
            Freecam freecam = (Freecam) ModuleRegistry.getByName("Freecam");
            freecam.onPlayerEntityTick();
        }
    }
}
