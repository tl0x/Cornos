package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class BudgetGraphics extends Module {
    public BudgetGraphics() {
        super("BudgetGraphics", "Makes the client visuals ugly (and your fps higher)", ModuleType.RENDER);
    }
}
