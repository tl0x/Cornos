package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ClientConfig;
import me.constantindev.ccl.etc.ms.MType;

public class TabGUI extends Module {
    public TabGUI() {
        super("TabGUI", "Opens a tab gui which can be used to toggle modules without opening a new menu.", MType.RENDER);
    }

    @Override
    public void onEnable() {
        if (ClientConfig.tabGUI == null) {
            ClientConfig.tabGUI = new me.constantindev.ccl.gui.TabGUI();
        }
        super.onEnable();
    }
}
