package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MultiOption;

public class ClientConfig extends Module {
    public ClientConfig() {
        super("ClientConfig", "Configuration for the client.");
        this.mconf.add(new MultiOption("prefix", "}", new String[]{"}", ".", "-", "+", "#", "@", "&", "%", "$"}));
        this.mconf.add(new MultiOption("homescreen", "client", new String[]{"client", "vanilla"}));
    }

    @Override
    public void onEnable() {
        this.isOn.setState(false);
        super.onEnable();
    }
}
