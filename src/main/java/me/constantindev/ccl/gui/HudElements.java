package me.constantindev.ccl.gui;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.Hud;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HudElements extends DrawableHelper {
    public static String tps = "Calculating...";
    public static long lastRecv = 0;
    public static List<Double> minAvg = new ArrayList<>();
    private final DateFormat dateFormat = new SimpleDateFormat("h:mm aa");
    double tick = 0;

    public void render(MatrixStack matrices, float delta) {
        tick += .2;
        if (tick > 360) tick = 0;
        double sin = Math.sin(Math.toRadians(tick));
        if (ModuleRegistry.getByName("debug").isOn.isOn())
            ClientHelper.sendChat(sin + ", " + tick + ", " + Math.toRadians(tick) + ", " + Math.toDegrees(tick));
        boolean chatOpen = Cornos.minecraft.currentScreen instanceof ChatScreen;
        Hud hud = (Hud) ModuleRegistry.getByName("hud");

        TextRenderer textRenderer = Cornos.minecraft.textRenderer;
        int offset = 0;
        if (((Toggleable) hud.mconf.getByName("miniplayer")).isEnabled() && !chatOpen) {
            double yaw = sin * 100;
            InventoryScreen.drawEntity((int) (Cornos.minecraft.getWindow().getScaledWidth() / 1.5), Cornos.minecraft.getWindow().getScaledHeight() - 1, 25, (float) (yaw), Cornos.minecraft.player.pitch, Cornos.minecraft.player);
        }
        if (((Toggleable) hud.mconf.getByName("coords")).isEnabled() && !chatOpen) {
            offset += 10;
            drawTextWithShadow(matrices, textRenderer, new LiteralText("XYZ:"), 2, Cornos.minecraft.getWindow().getScaledHeight() - (offset), ClientConfig.latestRGBVal);
            assert Cornos.minecraft.player != null;
            drawTextWithShadow(matrices, textRenderer,
                    new LiteralText(" " + Cornos.minecraft.player.getBlockPos().getX()
                            + " " + Cornos.minecraft.player.getBlockPos().getY() + " " + Cornos.minecraft.player.getBlockPos().getZ()),
                    2 + textRenderer.getWidth(new LiteralText("XYZ:")),
                    Cornos.minecraft.getWindow().getScaledHeight() - (offset), Color.LIGHT_GRAY.getRGB());
        }
        if (((Toggleable) hud.mconf.getByName("ping")).isEnabled() && !chatOpen) {
            if (Cornos.minecraft.getNetworkHandler() == null || Cornos.minecraft.player == null) return;
            PlayerListEntry ple = Cornos.minecraft.getNetworkHandler().getPlayerListEntry(Cornos.minecraft.player.getUuid());
            offset += 10;
            drawTextWithShadow(matrices, textRenderer, new LiteralText("Ping:"),
                    2, Cornos.minecraft.getWindow().getScaledHeight() - (offset), ClientConfig.latestRGBVal);
            drawTextWithShadow(matrices, textRenderer, new LiteralText(" " + (ple == null ? "?" : ple.getLatency()) + " ms"),
                    2 + textRenderer.getWidth(new LiteralText("Ping:")),
                    Cornos.minecraft.getWindow().getScaledHeight() - (offset), Color.LIGHT_GRAY.getRGB());
        }
        if (((Toggleable) hud.mconf.getByName("tps")).isEnabled() && !chatOpen) {
            offset += 10;
            drawTextWithShadow(matrices, textRenderer, new LiteralText("TPS:"),
                    2, Cornos.minecraft.getWindow().getScaledHeight() - (offset), ClientConfig.latestRGBVal);
            drawTextWithShadow(matrices, textRenderer, new LiteralText(" " + tps),
                    2 + textRenderer.getWidth(new LiteralText("FPS:")),
                    Cornos.minecraft.getWindow().getScaledHeight() - (offset), Color.LIGHT_GRAY.getRGB());
        }
        if (((Toggleable) hud.mconf.getByName("fps")).isEnabled() && !chatOpen) {
            offset += 10;
            drawTextWithShadow(matrices, textRenderer,
                    new LiteralText("FPS:"), 2,
                    Cornos.minecraft.getWindow().getScaledHeight() - (offset), ClientConfig.latestRGBVal);
            drawTextWithShadow(matrices, textRenderer,
                    new LiteralText(" " + Cornos.minecraft.fpsDebugString.split(" ")[0]),
                    2 + textRenderer.getWidth(new LiteralText("FPS:")),
                    Cornos.minecraft.getWindow().getScaledHeight() - (offset), Color.LIGHT_GRAY.getRGB());
        }
        if (((Toggleable) hud.mconf.getByName("context")).isEnabled()) {
            int w = Cornos.minecraft.getWindow().getScaledWidth();
            int h = Cornos.minecraft.getWindow().getScaledHeight();
            DrawableHelper.drawCenteredString(new MatrixStack(), Cornos.minecraft.textRenderer, Hud.currentContext, w / 2, h / 2 + 10, 0xFFFFFFFF);
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

                drawTextWithShadow(matrices, textRenderer, new LiteralText(StatusEffectUtil.durationToString(statusEffectInstance, 1)), Cornos.minecraft.getWindow().getScaledWidth() - (5 + textRenderer.getWidth(StatusEffectUtil.durationToString(statusEffectInstance, 1))), Cornos.minecraft.getWindow().getScaledHeight() - (10 + i), Color.LIGHT_GRAY.getRGB());
                drawTextWithShadow(matrices, textRenderer, new LiteralText(statusEffectInstance.getEffectType().getName().getString() + " " + (statusEffectInstance.getAmplifier() + 1) + " "), Cornos.minecraft.getWindow().getScaledWidth() - (5 + textRenderer.getWidth(statusEffectInstance.getEffectType().getName().getString() + " " + (statusEffectInstance.getAmplifier() + 1) + " " + StatusEffectUtil.durationToString(statusEffectInstance, 1))), Cornos.minecraft.getWindow().getScaledHeight() - (10 + i), formatting.getColorValue());
                i = i + 10;
            }
        }
        if (((Toggleable) hud.mconf.getByName("time")).isEnabled() && !chatOpen) {
            assert Cornos.minecraft.player != null;
            drawTextWithShadow(matrices, textRenderer,
                    new LiteralText(dateFormat.format(new Date())), 2 + textRenderer.getWidth(" " + Cornos.minecraft.player.getBlockPos().getX() + " " + Cornos.minecraft.player.getBlockPos().getY() + " " + Cornos.minecraft.player.getBlockPos().getZ() + dateFormat.format(new Date())), Cornos.minecraft.getWindow().getScaledHeight() - (10), Color.LIGHT_GRAY.getRGB());
        }
    }
}
