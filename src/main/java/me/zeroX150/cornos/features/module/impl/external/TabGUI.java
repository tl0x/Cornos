package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.etc.config.CConf;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class TabGUI extends Module {
    public TabGUI() {
        super("TabGUI", "top left watermark + module manager but small", ModuleType.RENDER);
    }

    @Override
    public void onEnable() {
        if (CConf.tabGUI == null) {
            CConf.tabGUI = new me.zeroX150.cornos.gui.screen.TabGUI();
        }
        super.onEnable();
    }
}
