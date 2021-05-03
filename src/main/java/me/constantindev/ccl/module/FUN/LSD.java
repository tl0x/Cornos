package me.constantindev.ccl.module.FUN;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.ms.ModuleType;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class LSD extends Module {
    public LSD() {
        super("LSD", "drugs.", ModuleType.FUN);
    }

    @Override
    public void onHudRender(MatrixStack ms, float td) {
        Color c = Color.getHSBColor((float) (((double) (System.currentTimeMillis() % 10000)) / 10000), 1, 1);
        int w = Cornos.minecraft.getWindow().getScaledWidth();
        int h = Cornos.minecraft.getWindow().getScaledHeight();
        DrawableHelper.fill(ms, 0, 0, w, h, new Color(c.getRed(), c.getGreen(), c.getBlue(), 50).getRGB());
        super.onHudRender(ms, td);
    }
}
