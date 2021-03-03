package me.constantindev.ccl.etc.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.render.RenderType;
import me.constantindev.ccl.etc.render.RenderableBlock;
import me.constantindev.ccl.etc.render.RenderableLine;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderHelper {
    public static List<RenderableBlock> BPQueue = new ArrayList<>();
    public static List<RenderableLine> B1B2LQueue = new ArrayList<>();

    public static void addToQueue(RenderableBlock block) {
        if (BPQueue.contains(block)) return;
        BPQueue.add(block);
    }
    public static void addToQueue(RenderableLine line) {
        if (!B1B2LQueue.contains(line)) B1B2LQueue.add(line);
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
    public static void renderLine(Vec3d from, Vec3d to, Color col, MatrixStack matrices, Camera camera) {
        Vec3d cameraPos = camera.getPos();
        VertexConsumerProvider.Immediate entityVertexConsumers = Cornos.minecraft.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer builder = entityVertexConsumers.getBuffer(RenderType.OVERLAY_LINES);

        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f m = matrices.peek().getModel();
        builder.vertex(m,(float)from.x,(float)from.y,(float)from.z).color(col.getRed(),col.getGreen(),col.getBlue(),col.getAlpha()).next();
        builder.vertex(m,(float)to.x,(float)to.y,(float)to.z).color(col.getRed(),col.getGreen(),col.getBlue(),col.getAlpha()).next();
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
