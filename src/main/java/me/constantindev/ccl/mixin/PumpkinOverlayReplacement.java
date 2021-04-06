package me.constantindev.ccl.mixin;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.NoRender;
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
        if (NoRender.pumpkin.isEnabled() && ModuleRegistry.getByName("norender").isOn.isOn()) {
            ci.cancel();
            MatrixStack defaultM = new MatrixStack();
            int w2d = Cornos.minecraft.getWindow().getScaledWidth() / 2;
            Cornos.minecraft.textRenderer.draw(defaultM, "You are wearing a pumpkin", w2d - ((float) Cornos.minecraft.textRenderer.getWidth("You are wearing a pumpkin") / 2), 50, 0xFFFFFFFF);
        }
    }
}
