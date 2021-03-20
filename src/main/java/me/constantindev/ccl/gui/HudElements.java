package me.constantindev.ccl.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.RenderHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.Hud;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HudElements extends DrawableHelper {

    private final DateFormat dateFormat = new SimpleDateFormat("h:mm aa");

    public void render(MatrixStack matrices, float delta) {

        Hud hud = (Hud) ModuleRegistry.getByName("hud");

        TextRenderer textRenderer = Cornos.minecraft.textRenderer;

        if (((Toggleable) hud.mconf.getByName("fps")).isEnabled()) {
            drawTextWithShadow(matrices, textRenderer, new LiteralText("FPS:"), 2, Cornos.minecraft.getWindow().getScaledHeight() - (Cornos.minecraft.currentScreen instanceof ChatScreen ? 34 : 20), ClientConfig.latestRGBVal);
            drawTextWithShadow(matrices, textRenderer, new LiteralText(" " + Cornos.minecraft.fpsDebugString.split(" ")[0]), 2 + textRenderer.getWidth(new LiteralText("FPS:")), Cornos.minecraft.getWindow().getScaledHeight() - (Cornos.minecraft.currentScreen instanceof ChatScreen ? 34 : 20), Color.LIGHT_GRAY.getRGB());
        }
        if (((Toggleable) hud.mconf.getByName("coords")).isEnabled()) {
            drawTextWithShadow(matrices, textRenderer, new LiteralText("XYZ:"), 2, Cornos.minecraft.getWindow().getScaledHeight() - (Cornos.minecraft.currentScreen instanceof ChatScreen ? 24 : 10), ClientConfig.latestRGBVal);
            assert Cornos.minecraft.player != null;
            drawTextWithShadow(matrices, textRenderer, new LiteralText(" " + Cornos.minecraft.player.getBlockPos().getX() + " " + Cornos.minecraft.player.getBlockPos().getY() + " " + Cornos.minecraft.player.getBlockPos().getZ()), 2 + textRenderer.getWidth(new LiteralText("XYZ:")), Cornos.minecraft.getWindow().getScaledHeight() - (Cornos.minecraft.currentScreen instanceof ChatScreen ? 24 : 10), Color.LIGHT_GRAY.getRGB());
        }
        if (((Toggleable) hud.mconf.getByName("effects")).isEnabled()) {
            assert Cornos.minecraft.player != null;
            int i = 0;
            for (StatusEffectInstance statusEffectInstance : Cornos.minecraft.player.getStatusEffects()) {

                StatusEffectType statusEffectType = statusEffectInstance.getEffectType().getType();
                
                Formatting formatting;
                
                switch (statusEffectType) {
                    case HARMFUL:
                        formatting = Formatting.RED;
                        break;
                    case NEUTRAL:
                        formatting = Formatting.AQUA;
                        break;
                    case BENEFICIAL:
                        formatting = Formatting.GREEN;
                        break;
                    default:
                        formatting = Formatting.GRAY;
                        break;
                }
                
                drawTextWithShadow(matrices, textRenderer, new LiteralText(StatusEffectUtil.durationToString(statusEffectInstance, 1)), Cornos.minecraft.getWindow().getScaledWidth() - (5 + textRenderer.getWidth(StatusEffectUtil.durationToString(statusEffectInstance, 1))), Cornos.minecraft.getWindow().getScaledHeight() - (Cornos.minecraft.currentScreen instanceof ChatScreen ? 24 + i : 10 + i), Color.LIGHT_GRAY.getRGB());
                drawTextWithShadow(matrices, textRenderer, new LiteralText(statusEffectInstance.getEffectType().getName().getString() + " " + (statusEffectInstance.getAmplifier() + 1) + " "), Cornos.minecraft.getWindow().getScaledWidth() - (5 + textRenderer.getWidth(statusEffectInstance.getEffectType().getName().getString() + " " + (statusEffectInstance.getAmplifier() + 1) + " " + StatusEffectUtil.durationToString(statusEffectInstance, 1))), Cornos.minecraft.getWindow().getScaledHeight() - (Cornos.minecraft.currentScreen instanceof ChatScreen ? 24 + i : 10 + i), formatting.getColorValue().intValue());
                i = i + 10;
            }
        }
        if (((Toggleable) hud.mconf.getByName("time")).isEnabled()) {
            assert Cornos.minecraft.player != null;
            drawTextWithShadow(matrices, textRenderer, new LiteralText(dateFormat.format(new Date())), 2 + textRenderer.getWidth(" " + Cornos.minecraft.player.getBlockPos().getX() + " " + Cornos.minecraft.player.getBlockPos().getY() + " " + Cornos.minecraft.player.getBlockPos().getZ() + dateFormat.format(new Date())), Cornos.minecraft.getWindow().getScaledHeight() - (Cornos.minecraft.currentScreen instanceof ChatScreen ? 24 : 10), Color.LIGHT_GRAY.getRGB());
        }
    }
}
