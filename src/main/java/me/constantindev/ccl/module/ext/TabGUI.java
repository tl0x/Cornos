package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.CConf;
import me.constantindev.ccl.etc.ms.ModuleType;

public class TabGUI extends Module {
    public TabGUI() {
        super("TabGUI", "top left watermark + module manager but small", ModuleType.RENDER);
    }

    @Override
    public void onEnable() {
        if (CConf.tabGUI == null) {
            CConf.tabGUI = new me.constantindev.ccl.gui.TabGUI();
        }
        super.onEnable();
    }
}
