package me.constantindev.ccl.mixin.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.Vibe;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    Module vibe;
    @Shadow
    private ClientWorld world;

    @Shadow
    private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j) {
    }

    @Inject(method = "drawBlockOutline", at = @At("HEAD"), cancellable = true)
    public void drawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        if (vibe == null) vibe = ModuleRegistry.search("vibe");
        if (!vibe.isEnabled()) return;
        HitResult hr = Cornos.minecraft.crosshairTarget;
        if (hr instanceof BlockHitResult) {
            BlockPos bp = ((BlockHitResult) hr).getBlockPos();
            if (blockPos == bp) {
                ci.cancel();
                Color c = Vibe.blockOutline.getColor();
                float r = (float) c.getRed() / 255;
                float g = (float) c.getGreen() / 255;
                float b = (float) c.getBlue() / 255;
                drawShapeOutline(matrixStack, vertexConsumer, blockState.getOutlineShape(this.world, blockPos, ShapeContext.of(entity)), (double) blockPos.getX() - d, (double) blockPos.getY() - e, (double) blockPos.getZ() - f, r, g, b, 1F);
            }
        }
    }
}
