package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

public class TabGUI extends Module {
    public TabGUI() {
        super("TabGUI", "top left watermark + module manager but small", ModuleType.RENDER);
    }

    @Override
    public void onEnable() {
        if (CConf.tabGUI == null) {
            CConf.tabGUI = new me.constantindev.ccl.gui.screen.TabGUI();
        }
        super.onEnable();
    }
}
