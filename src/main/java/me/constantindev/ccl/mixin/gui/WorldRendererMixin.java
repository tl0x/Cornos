package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.RENDER.Freecam;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        //RenderHelper.B1S1TQueue.forEach(renderableText -> RenderHelper.drawText(renderableText.pos, renderableText.color, matrices, camera, renderableText.text, renderableText.size));
        //MinecraftClient.getInstance().getBlockRenderManager()
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void render1(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        if (ModuleRegistry.getByName("freecam").isOn.isOn()) {
            Vec3d o = Freecam.currentOffset;
            matrices.translate(o.x, o.y, o.z);
        }
    }
}
