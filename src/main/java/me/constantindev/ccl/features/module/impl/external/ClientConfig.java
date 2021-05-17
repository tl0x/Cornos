package me.constantindev.ccl.features.module.impl.external;

import me.constantindev.ccl.etc.config.MConfMultiOption;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.features.module.Module;

public class ClientConfig extends Module {
    public MConfToggleable particles = new MConfToggleable("particles", true);

    public ClientConfig() {
        super("ClientConfig", "Config for the client");
        this.mconf.add(new MConfMultiOption("prefix", ".", new String[]{"}", ".", "-", "+", "#", "@", "&", "%", "$"}));
        this.mconf.add(new MConfMultiOption("homescreen", "client", new String[]{"client", "vanilla"}));
        this.mconf.add(new MConfMultiOption("mpscreen", "client", new String[]{"client", "vanilla"}));
        this.mconf.add(new MConfToggleable("customProcessIcon", true));
        this.mconf.add(particles);
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        super.onEnable();
    }
}
