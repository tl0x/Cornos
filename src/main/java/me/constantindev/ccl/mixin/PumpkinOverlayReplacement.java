package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class PumpkinOverlayReplacement {
    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    public void renderPumpkinOverlayReplacement(CallbackInfo ci) {
        if (ModuleRegistry.getByName("nopumpkin").isEnabled) {
            ci.cancel();
            MatrixStack defaultM = new MatrixStack();
            int w2d = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
            MinecraftClient.getInstance().textRenderer.draw(defaultM, "You are wearing a pumpkin", w2d - ((float) MinecraftClient.getInstance().textRenderer.getWidth("You are wearing a pumpkin") / 2), 50, 0xFFFFFFFF);
        }
    }
}
