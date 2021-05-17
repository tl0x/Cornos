package me.constantindev.ccl.features.command.impl;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.helper.STL;
import me.constantindev.ccl.features.command.Command;
import me.constantindev.ccl.gui.screen.XrayConfigScreen;

public class XrayConfig extends Command {
    public XrayConfig() {
        super("XrayConfig", "Configuration for xray", new String[]{"xrayconfig", "xray", "xconf"});
    }

    @Override
    public void onExecute(String[] args) {
        new Thread(() -> {
            STL.sleep(10);
            Cornos.minecraft.openScreen(new XrayConfigScreen());
        }).start();
        super.onExecute(args);
    }
}
