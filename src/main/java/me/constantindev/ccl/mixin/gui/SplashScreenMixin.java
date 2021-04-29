package me.constantindev.ccl.mixin.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.gui.screen.SplashScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashScreen.class)
public class SplashScreenMixin {
    @Mutable
    @Shadow
    @Final
    private static int BRAND_RGB;

    @Mutable
    @Shadow
    @Final
    private static int BRAND_ARGB;

    @Inject(method = "init", at = @At("TAIL"))
    private static void i(MinecraftClient client, CallbackInfo ci) {
        BRAND_RGB = BackgroundHelper.ColorMixer.getArgb(255, 0, 0, 0) & 16777215;
        BRAND_ARGB = BackgroundHelper.ColorMixer.getArgb(255, 0, 0, 0);
    }
}
