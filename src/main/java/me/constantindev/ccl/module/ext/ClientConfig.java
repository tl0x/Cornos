package me.constantindev.ccl.module.ext;

import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfMultiOption;
import me.constantindev.ccl.etc.config.MConfToggleable;

public class ClientConfig extends Module {
    public ClientConfig() {
        super("ClientConfig", "Configuration for the client.");
        this.mconf.add(new MConfMultiOption("prefix", ".", new String[]{"}", ".", "-", "+", "#", "@", "&", "%", "$"}));
        this.mconf.add(new MConfMultiOption("homescreen", "client", new String[]{"client", "vanilla"}));
        this.mconf.add(new MConfMultiOption("mpscreen", "client", new String[]{"client", "vanilla"}));
        this.mconf.add(new MConfToggleable("customProcessIcon", true));
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        super.onEnable();
    }
}
