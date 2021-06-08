package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class BudgetGraphics extends Module {
    boolean prev;

    public BudgetGraphics() {
        super("BudgetGraphics", "Makes the client visuals ugly (and your fps higher)", ModuleType.RENDER);
    }

    @Override
    public void onEnable() {
        prev = Hud.themeColor.isRainbow();
    }

    @Override
    public void onExecute() {
        Hud.themeColor.setRainbow(false);
    }

    @Override
    public void onDisable() {
        Hud.themeColor.setRainbow(prev);
    }
}
