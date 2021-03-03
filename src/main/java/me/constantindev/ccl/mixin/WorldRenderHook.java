package me.constantindev.ccl.mixin;

import me.constantindev.ccl.etc.helper.RenderHelper;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRenderHook {
    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        RenderHelper.BPQueue.forEach(renderableBlock -> RenderHelper.renderBlockOutline(renderableBlock.bp, renderableBlock.dimensions, renderableBlock.r, renderableBlock.g, renderableBlock.b, renderableBlock.a, matrices, camera));
        RenderHelper.B1B2LQueue.forEach(renderableLine -> RenderHelper.renderLine(renderableLine.bp1,renderableLine.bp2,renderableLine.c,matrices,camera));
    }
}
