package me.constantindev.ccl.mixin;

import com.google.common.collect.Lists;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.reg.ModuleRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(InGameHud.class)
public class IngameRendererHook {
    int rgbSeed = 0;
    int stage = 0;
    List<Integer> lastValues = new ArrayList<>();
    @Shadow
    private int scaledWidth;

    @Inject(method = "render", at = @At("RETURN"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        rgbSeed++;
        if (rgbSeed > 255) {
            rgbSeed = 0;
            stage++;
        }
        if (stage > 2) stage = 0;
        int r, g, b;
        switch (stage) {
            case 0:
                r = rgbSeed;
                g = 0;
                b = Math.abs(rgbSeed - 255);
                break;
            case 1:
                r = Math.abs(rgbSeed - 255);
                g = rgbSeed;
                b = 0;
                break;
            case 2:
                r = 0;
                g = Math.abs(rgbSeed - 255);
                b = rgbSeed;
                break;
            default:
                // shit hit the fan how is stage above 2
                r = 0;
                g = 0;
                b = 0;
                CrashReport rp = CrashReport.create(new Throwable(), "How the fuck");
                rp.addElement("Shit hit the fucking fan");
                rp.addElement("How is the rgb stage above 2");
                rp.addElement("Did you modify game memory?");
                throw new CrashException(rp);
        }
        int rgb = (0xFF << 24) + (r << 16) + (g << 8) + b;
        if (rgbSeed % 10 == 0) lastValues.add(rgb);

        if (ModuleRegistry.getByName("hud").isEnabled) {
            AtomicInteger offset = new AtomicInteger(1);
            List<Module> ml = ModuleRegistry.getAll();
            ml.sort(Comparator.comparingInt(o -> MinecraftClient.getInstance().textRenderer.getWidth(o.name)));
            List<Module> mlR = Lists.reverse(ml);
            if (lastValues.size() > mlR.size()) {
                lastValues.subList(0, 1).clear();
            }
            AtomicInteger current = new AtomicInteger(0);
            mlR.forEach(module -> {
                int colorToUse;
                try {
                    colorToUse = lastValues.get(current.addAndGet(1));
                } catch (Exception ignored) {
                    colorToUse = rgb;
                }
                MinecraftClient.getInstance().textRenderer.draw(matrices, module.name, scaledWidth - 1 - MinecraftClient.getInstance().textRenderer.getWidth(module.name), offset.getAndAdd(10), colorToUse);
            });
        }
    }
}
