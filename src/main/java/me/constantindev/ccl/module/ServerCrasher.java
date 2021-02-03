package me.constantindev.ccl.module;

import me.constantindev.ccl.etc.ServerCrasherManager;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.ModuleConfig;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.helper.ClientHelper;

public class ServerCrasher extends Module {
    public ServerCrasher() {
        super("ServerCrasher", "Several ways to crash a server");
        this.mconf.add(new MultiOption("mode", "rotation", new String[]{"rotation", "location", "biglocation"}));
        this.mconf.add(new ModuleConfig.ConfigKey("strength", "100"));
        ServerCrasherManager.runner.start();
    }

    @Override
    public void onExecute() {
        int strength;
        try {
            strength = Integer.parseInt(this.mconf.getByName("strength").value);
            if (strength < 0 || strength > 100) throw new Exception();
        } catch (Exception ignored) {
            ClientHelper.sendChat("Invalid strength for ServerCrasher. Choose one between 0 and 100");
            this.mconf.getByName("strength").setValue("100");
            return;
        }
        ServerCrasherManager.mode = this.mconf.getByName("mode").value;
        ServerCrasherManager.strength = strength;
        super.onExecute();
    }
}
