package me.constantindev.ccl.etc.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.render.RenderType;
import me.constantindev.ccl.etc.render.RenderableBlock;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class RenderHelper {
    public static List<RenderableBlock> queue = new ArrayList<>();

    public static void addToQueue(RenderableBlock block) {
        if (queue.contains(block)) return;
        queue.add(block);
    }

    public static void renderBlockOutline(BlockPos bpos, Vec3d dimensions, int r, int g, int b, int a, MatrixStack matrices, Camera camera) {
        Vec3d cameraPos = camera.getPos();
        VertexConsumerProvider.Immediate entityVertexConsumers = Cornos.minecraft.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer builder = entityVertexConsumers.getBuffer(RenderType.OVERLAY_LINES);

        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        RenderHelper.renderBlockBounding(matrices, dimensions, builder, bpos, (float) r / 255, (float) g / 255, (float) b / 255, (float) a / 255);

        RenderSystem.disableDepthTest();
        matrices.pop();
        entityVertexConsumers.draw(RenderType.OVERLAY_LINES);
    }

    private static void renderBlockBounding(MatrixStack matrices, Vec3d dim, VertexConsumer builder, BlockPos bp, float r, float g, float b, float a) {
        if (bp == null) {
            return;
        }
        final float x = bp.getX(), y = bp.getY(), z = bp.getZ();

        WorldRenderer.drawBox(matrices, builder, x, y, z, x + dim.x, y + dim.y, z + dim.z, r, g, b, a);
    }
}
