package me.constantindev.ccl.etc.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.javafx.geom.Vec2d;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.render.RenderType;
import me.constantindev.ccl.etc.render.RenderableBlock;
import me.constantindev.ccl.etc.render.RenderableLine;
import me.constantindev.ccl.etc.render.RenderableText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderHelper {
    public static List<RenderableBlock> BPQueue = new ArrayList<>();
    public static List<RenderableLine> B1B2LQueue = new ArrayList<>();
    public static List<RenderableText> B1S1TQueue = new ArrayList<>();

    public static void addToQueue(RenderableBlock block) {
        if (!BPQueue.contains(block)) BPQueue.add(block);
    }

    public static void addToQueue(RenderableLine line) {
        if (!B1B2LQueue.contains(line)) B1B2LQueue.add(line);
    }

    public static void addToQueue(RenderableText text) {
        if (!B1S1TQueue.contains(text)) B1S1TQueue.add(text);
    }

    public static void renderBlockOutline(Vec3d bpos, Vec3d dimensions, int r, int g, int b, int a, MatrixStack matrices, Camera camera) {
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
        builder.vertex(m, (float) from.x, (float) from.y, (float) from.z).color(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha()).next();
        builder.vertex(m, (float) to.x, (float) to.y, (float) to.z).color(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha()).next();
        RenderSystem.disableDepthTest();
        matrices.pop();
        entityVertexConsumers.draw(RenderType.OVERLAY_LINES);
    }

    public static void drawText(Vec3d pos, Color col, MatrixStack matrices, Camera camera, String text, Vec2d size) {
        Vec3d cameraPos = camera.getPos();
        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        matrices.translate(pos.x, pos.y, pos.z);
        matrices.scale(1, -1, 1);
        matrices.scale(0.1f, 0.1f, 0.1f);
        matrices.scale((float) size.x, (float) size.y, 1);
        //Cornos.minecraft.textRenderer.draw(matrices,text,0,0,col.getRGB());
        DrawableHelper.drawCenteredString(matrices, MinecraftClient.getInstance().textRenderer, text, 0, 0, col.getRGB());
        matrices.scale(-1, 1, -1);
        DrawableHelper.drawCenteredString(matrices, MinecraftClient.getInstance().textRenderer, text, 0, 0, col.getRGB());
        RenderSystem.disableDepthTest();
        matrices.pop();
    }

    private static void renderBlockBounding(MatrixStack matrices, Vec3d dim, VertexConsumer builder, Vec3d bp, float r, float g, float b, float a) {
        if (bp == null) {
            return;
        }
        final float x = (float) bp.getX(), y = (float) bp.getY(), z = (float) bp.getZ();

        WorldRenderer.drawBox(matrices, builder, x, y, z, x + dim.x, y + dim.y, z + dim.z, r, g, b, a);
    }

    public static void drawImage(MatrixStack matrices, Identifier identifier, int x, int y, int imageWidth, int imageHeight) {

        RenderSystem.enableAlphaTest();
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 771);

        Cornos.minecraft.getTextureManager().bindTexture(identifier);
        Screen.drawTexture(matrices, x, y, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);

        RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();

    }
}
