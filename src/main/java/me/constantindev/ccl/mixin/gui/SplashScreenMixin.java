package me.constantindev.ccl.mixin.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BackgroundHelper;
import net.minecraft.client.gui.screen.SplashScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mixin(SplashScreen.class)
public class SplashScreenMixin {
    @Inject(method="init",at=@At("TAIL"))
    private static void i(MinecraftClient client, CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {
        Field BRAND_RGB = SplashScreen.class.getDeclaredField("BRAND_RGB");
        Field BRAND_ARGB = SplashScreen.class.getDeclaredField("BRAND_ARGB");
        BRAND_RGB.setAccessible(true);
        BRAND_ARGB.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(BRAND_RGB, BRAND_RGB.getModifiers() & ~Modifier.FINAL);
        modifiersField.setInt(BRAND_ARGB, BRAND_ARGB.getModifiers() & ~Modifier.FINAL);
        BRAND_RGB.set(null, BackgroundHelper.ColorMixer.getArgb(255,0,0,0) & 16777215);
        BRAND_ARGB.set(null, BackgroundHelper.ColorMixer.getArgb(255,0,0,0));
    }
}
