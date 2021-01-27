package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.config.ConfigHelper;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class ShutdownHook {
    @Inject(method = "stop", at = @At("HEAD"))
    public void sdHook(CallbackInfo ci) {
        ConfigHelper.saveConfig();
    }
}
