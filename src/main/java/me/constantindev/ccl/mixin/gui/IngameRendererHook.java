package me.constantindev.ccl.mixin.gui;

import com.google.common.collect.Lists;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.exc.InvalidStateException;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import me.constantindev.ccl.module.ext.Hud;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(InGameHud.class)
public class IngameRendererHook {
    double rgbSeed = 0;
    double swap = 0;
    int stage = 0;
    long latestTime = System.currentTimeMillis();
    List<Integer> lastValues = new ArrayList<>();
    @Shadow
    private int scaledWidth;

    @Inject(method = "render", at = @At("RETURN"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) throws InvalidStateException {
        Hud hud = (Hud) ModuleRegistry.getByName("hud");
        double rgbMulti = ((Num) ModuleRegistry.getByName("hud").mconf.getByName("rgbSpeed")).getValue();
        long elapsed = System.currentTimeMillis() - latestTime;
        if (elapsed != 0) latestTime = System.currentTimeMillis();
        rgbSeed += (elapsed * rgbMulti) / 20;
        swap += (elapsed * rgbMulti) / 20;
        if (rgbSeed > 255) {
            rgbSeed = 0;
            stage++;
        }
        if (stage > 2) stage = 0;
        int r, g, b;
        switch (stage) {
            case 0:
                r = (int) rgbSeed;
                g = 0;
                b = (int) Math.abs(rgbSeed - 255);
                break;
            case 1:
                r = (int) Math.abs(rgbSeed - 255);
                g = (int) rgbSeed;
                b = 0;
                break;
            case 2:
                r = 0;
                g = (int) Math.abs(rgbSeed - 255);
                b = (int) rgbSeed;
                break;
            default:
                // shit hit the fan how is stage above 2
                throw new InvalidStateException("RgbStage", stage + "");
        }
        int rgb = (0xFF << 24) + (r << 16) + (g << 8) + b;
        ClientConfig.latestRGBVal = rgb;
        if (swap > 10) {
            lastValues.add(rgb);
            swap = 0;
        }

        if (ModuleRegistry.getByName("hud").isOn.isOn()) {
            if (((Toggleable) hud.mconf.getByName("modules")).isEnabled()) {
                boolean doRgb = Hud.themeColor.isRainbow();
                AtomicInteger offset = new AtomicInteger(0);
                List<Module> ml = ModuleRegistry.getAll();
                List<Module> mlR = new ArrayList<>();
                ml.forEach(module -> {
                    if (module.isOn.isOn() && (((Toggleable) module.mconf.getByName("visible")).isEnabled()))
                        mlR.add(module);
                });
                mlR.sort(Comparator.comparingInt(o -> Cornos.minecraft.textRenderer.getWidth(o.name)));
                List<Module> mlR1 = Lists.reverse(mlR);
                if (lastValues.size() > mlR1.size()) {
                    lastValues.subList(0, 1).clear();
                }
                AtomicInteger current = new AtomicInteger(0);
                mlR1.forEach(module -> {
                    int colorToUse;
                    try {
                        colorToUse = lastValues.get(current.addAndGet(1));
                    } catch (Exception ignored) {
                        colorToUse = Hud.themeColor.getRGB();
                    }
                    int off = offset.getAndAdd(11);
                    DrawableHelper.fill(matrices,scaledWidth - Cornos.minecraft.textRenderer.getWidth(module.name)-2-3,off,scaledWidth-2,off+11, new Color(47, 47, 47, 40).getRGB());
                    DrawableHelper.fill(matrices,scaledWidth-2,off,scaledWidth,off+11, doRgb ? colorToUse : Hud.themeColor.getRGB());
                    Cornos.minecraft.textRenderer.draw(matrices, module.name, scaledWidth - Cornos.minecraft.textRenderer.getWidth(module.name)-3, 2 + off, doRgb ? colorToUse : Hud.themeColor.getRGB());
                });
            }
            ClientConfig.hudElements.render(matrices, tickDelta);
        }
        if (ModuleRegistry.getByName("TabGUI").isOn.isOn() && ClientConfig.tabGUI != null) {
            ClientConfig.tabGUI.render(matrices, tickDelta);
        }
    }

    @Inject(at = {@At("HEAD")}, method = "renderStatusEffectOverlay", cancellable = true)
    private void renderStatusEffectOverlay(MatrixStack matrices, CallbackInfo ci) {
        if (ModuleRegistry.getByName("hud").isOn.isOn()) {
            ci.cancel();
        }
    }
}
