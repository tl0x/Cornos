package me.zeroX150.cornos.mixin.gui;

import me.zeroX150.cornos.features.module.ModuleRegistry;
import me.zeroX150.cornos.features.module.impl.external.NoRender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    @Inject(method = "renderFireOverlay", cancellable = true, at = @At("HEAD"))
    private static void renderFireOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo ci) {
        if (NoRender.fire.isEnabled() && ModuleRegistry.search(NoRender.class).isEnabled()) {
            ci.cancel();
        }
    }
}
