package me.constantindev.ccl.mixin.gui;

import com.google.common.collect.Lists;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleRegistry;
import me.constantindev.ccl.features.module.impl.external.BetterHotbar;
import me.constantindev.ccl.features.module.impl.external.Hud;
import me.constantindev.ccl.features.module.impl.external.NoRender;
import me.constantindev.ccl.features.module.impl.external.TabGUI;
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
public class InGameHudMixin {
    double rgbSeed = 0;
    double swap = 0;
    int stage = 0;
    long latestTime = System.currentTimeMillis();
    List<Integer> lastValues = new ArrayList<>();
    @Shadow
    private int scaledWidth;

    @Inject(method = "render", at = @At("RETURN"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        Hud hud = (Hud) ModuleRegistry.search(Hud.class);
        double rgbMulti = ((MConfNum) ModuleRegistry.search(Hud.class).mconf.getByName("rgbSpeed")).getValue();
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
                stage = 0;
                r = 0;
                g = 0;
                b = 0;
                if (Cornos.minecraft.currentScreen == null) {
                    Cornos.openCongratsScreen();
                }
        }
        float[] ham = Color.RGBtoHSB(r, g, b, null);
        int rgb = Color.HSBtoRGB(ham[0], 0.6f, ham[2]);
        CConf.latestRGBVal = rgb;
        if (swap > 10) {
            lastValues.add(rgb);
            swap = 0;
        }

        if (ModuleRegistry.search(Hud.class).isEnabled()) {
            if (((MConfToggleable) hud.mconf.getByName("modules")).isEnabled()) {
                boolean doRgb = Hud.themeColor.isRainbow();
                AtomicInteger offset = new AtomicInteger(0);
                List<Module> ml = ModuleRegistry.getAll();
                List<Module> mlR = new ArrayList<>();
                for (Module module : ml) {
                    if (module.isEnabled() && (((MConfToggleable) module.mconf.getByName("visible")).isEnabled()))
                        mlR.add(module);
                }
                mlR.sort(Comparator.comparingInt(o -> Cornos.minecraft.textRenderer.getWidth(o.name)));
                List<Module> mlR1 = Lists.reverse(mlR);
                if (lastValues.size() > mlR1.size()) {
                    lastValues.subList(0, 1).clear();
                }
                int current = 0;
                for (Module module : mlR1) {
                    int colorToUse;
                    try {
                        colorToUse = lastValues.get(current);
                    } catch (Exception ignored) {
                        colorToUse = Hud.themeColor.getRGB();
                    }
                    current++;
                    int off = offset.getAndAdd(11);
                    DrawableHelper.fill(matrices, scaledWidth - Cornos.minecraft.textRenderer.getWidth(module.name) - 2 - 3, off, scaledWidth - 2, off + 11, new Color(47, 47, 47, 90).getRGB());
                    DrawableHelper.fill(matrices, scaledWidth - 2, off, scaledWidth, off + 11, doRgb ? colorToUse : Hud.themeColor.getRGB());
                    Cornos.minecraft.textRenderer.draw(matrices, module.name, scaledWidth - Cornos.minecraft.textRenderer.getWidth(module.name) - 3, 2 + off, doRgb ? colorToUse : Hud.themeColor.getRGB());
                }
            }
            try {
                CConf.hudElements.render(matrices, tickDelta);
            } catch (Exception ignored) {
            }
        }
        for (Module m : ModuleRegistry.getAll()) {
            if (m.isEnabled()) m.onHudRender(matrices, tickDelta);
        }
        if (ModuleRegistry.search(TabGUI.class).isEnabled() && CConf.tabGUI != null) {
            CConf.tabGUI.render(matrices, tickDelta);
        }
    }

    @Inject(at = {@At("HEAD")}, method = "renderStatusEffectOverlay", cancellable = true)
    private void renderStatusEffectOverlay(MatrixStack matrices, CallbackInfo ci) {
        if (ModuleRegistry.search(Hud.class).isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    public void renderPumpkinOverlayReplacement(CallbackInfo ci) {
        if (NoRender.pumpkin.isEnabled() && ModuleRegistry.search(NoRender.class).isEnabled()) {
            ci.cancel();
            MatrixStack defaultM = new MatrixStack();
            int w2d = Cornos.minecraft.getWindow().getScaledWidth() / 2;
            Cornos.minecraft.textRenderer.draw(defaultM, "You are wearing a pumpkin", w2d - ((float) Cornos.minecraft.textRenderer.getWidth("You are wearing a pumpkin") / 2), 50, 0xFFFFFFFF);
        }
    }

    @Inject(method = "renderHotbar", at = @At("TAIL"))
    public void renderHotbar(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        if (ModuleRegistry.search(BetterHotbar.class).isEnabled()) BetterHotbar.renderHotbar();
    }
}
