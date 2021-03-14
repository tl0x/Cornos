package me.constantindev.ccl.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.TabManager;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;

public class TabGUI extends DrawableHelper {

    public static void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC, Matrix4f matrix4f) {
        drawGuiRect(x + size, y + size, x1 - size, y1 - size, insideC, matrix4f);
        drawGuiRect(x + size, y + size, x1, y, borderC, matrix4f);
        drawGuiRect(x, y, x + size, y1, borderC, matrix4f);
        drawGuiRect(x1, y1, x1 - size, y + size, borderC, matrix4f);
        drawGuiRect(x, y1 - size, x1, y1, borderC, matrix4f);
    }

    public static void drawGuiRect(float left, float top, float right, float bottom, int color, Matrix4f matrix4f) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFunc(770, 771);
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        buffer.vertex(matrix4f, left, bottom, 0.0F).color(f, f1, f2, f3).next();
        buffer.vertex(matrix4f, right, bottom, 0.0F).color(f, f1, f2, f3).next();
        buffer.vertex(matrix4f, right, top, 0.0F).color(f, f1, f2, f3).next();
        buffer.vertex(matrix4f, left, top, 0.0F).color(f, f1, f2, f3).next();
        buffer.end();
        RenderSystem.enableAlphaTest();
        BufferRenderer.draw(buffer);
        RenderSystem.disableAlphaTest();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    public void render(MatrixStack matrices, float delta) {
        drawTabs(matrices, delta);
    }

    private void drawTabs(MatrixStack matrices, float delta) {
        if (ModuleRegistry.getTabManager() != null) {
            TabManager tabManager = ModuleRegistry.getTabManager();
            int i = 0;
            int k = 0;
            for (MType mType : tabManager.getTabType()) {
                drawGuiRect(5, 5 + i, 79, 15 + i, 0x90000000, matrices.peek().getModel());
                if (k == tabManager.getCurrentTab()) {
                    drawGuiRect(5, 5 + i, 79, 15 + i, ClientConfig.latestRGBVal, matrices.peek().getModel());
                }
                Cornos.minecraft.textRenderer.drawWithShadow(matrices, mType.getN(), 7, 6 + i, 0xffffff, true);
                if (tabManager.getTabs().get(mType).isExpanded()) {
                    int j = 0;
                    int l = 0;
                    for (Module m : tabManager.getMods().get(mType)) {
                        int off = m.isOn.isOn() ? 2 : 0;
                        drawGuiRect(172 + off, 5 + j, 81 + off, 15 + j, new Color(35, 35, 35).getRGB(), matrices.peek().getModel());
                        if (m.isOn.isOn()) {
                            drawGuiRect(174, 5 + j, 83, 15 + j, new Color(47, 47, 47, 134).getRGB(), matrices.peek().getModel());
                        }
                        if (l == tabManager.getCurrentMods().get(mType)) {
                            drawGuiRect(172 + off, 5 + j, 81 + off, 15 + j, ClientConfig.latestRGBVal, matrices.peek().getModel());
                        }
                        Cornos.minecraft.textRenderer.drawWithShadow(matrices, m.name, 84 + off, 6 + j, 0xffffff, true);
                        j += 10;
                        l++;
                    }
                }
                i += 10;
                k++;
            }
        }
    }
}
