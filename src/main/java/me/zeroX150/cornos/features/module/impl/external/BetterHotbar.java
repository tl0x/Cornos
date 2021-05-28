package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.helper.Renderer;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;
import net.minecraft.client.util.Window;

public class BetterHotbar extends Module {
    public BetterHotbar() {
        super("BetterHotbar", "(very) slight improvements to the hotbar", ModuleType.RENDER);
    }

    public static void renderHotbar() {
        Window w = Cornos.minecraft.getWindow();
        double i = 182;
        Renderer.renderLineScreen((double) w.getScaledWidth() / 2 - (i / 2), w.getScaledHeight() - 22, (double) w.getScaledWidth() / 2 + (i / 2), w.getScaledHeight() - 22, Hud.themeColor.getColor(), 2);
        Renderer.renderLineScreen((double) w.getScaledWidth() / 2 - (i / 2), w.getScaledHeight() - 22, (double) w.getScaledWidth() / 2 - (i / 2), w.getScaledHeight(), Hud.themeColor.getColor(), 2);
        Renderer.renderLineScreen((double) w.getScaledWidth() / 2 + (i / 2), w.getScaledHeight() - 22, (double) w.getScaledWidth() / 2 + (i / 2), w.getScaledHeight(), Hud.themeColor.getColor(), 2);
    }
}
