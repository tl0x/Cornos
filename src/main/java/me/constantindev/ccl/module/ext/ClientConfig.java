package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;
import me.constantindev.ccl.etc.config.Toggleable;

public class ClientConfig extends Module {
    public ClientConfig() {
        super("ClientConfig", "Configuration for the client.");
        this.mconf.add(new MultiOption("prefix", ".", new String[]{"}", ".", "-", "+", "#", "@", "&", "%", "$"}));
        this.mconf.add(new MultiOption("homescreen", "client", new String[]{"client", "vanilla"}));
        this.mconf.add(new MultiOption("mpscreen", "client", new String[]{"client", "vanilla"}));
        this.mconf.add(new Toggleable("customProcessIcon", true));
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        super.onEnable();
    }
}
