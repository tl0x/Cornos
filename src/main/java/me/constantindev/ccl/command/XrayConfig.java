package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import me.constantindev.ccl.gui.XrayConfigScreen;

public class XrayConfig extends Command {
    public XrayConfig() {
        super("XrayConfig", "Configuration for xray", new String[]{"xrayconfig", "xray", "xconf"});
    }

    @Override
    public void onExecute(String[] args) {
        new Thread(() -> {
            ClientHelper.sleep(10);
            Cornos.minecraft.openScreen(new XrayConfigScreen());
        }).start();
        super.onExecute(args);
    }
}
