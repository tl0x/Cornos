package me.constantindev.ccl.mixin;

import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.NoRender;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapRenderer.class)
public class ItemFrameEntityRenderMixin {
    @Inject(method = "draw", cancellable = true, at = @At("HEAD"))
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, MapState mapState, boolean bl, int i, CallbackInfo ci) {
        if (NoRender.maps.isEnabled() && ModuleRegistry.search(NoRender.class).isEnabled()) ci.cancel();
    }
}
