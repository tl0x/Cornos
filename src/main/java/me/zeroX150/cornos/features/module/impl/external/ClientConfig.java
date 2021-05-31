package me.zeroX150.cornos.features.module.impl.external;

import me.zeroX150.cornos.etc.config.MConfMultiOption;
import me.zeroX150.cornos.etc.config.MConfToggleable;
import me.zeroX150.cornos.features.module.Module;

public class ClientConfig extends Module {
    public MConfToggleable particles = new MConfToggleable("particles", true, "Whether or not to show particles on the home screen, cgui and chat");

    public ClientConfig() {
        super("ClientConfig", "Config for the client");
        this.mconf.add(new MConfMultiOption("prefix", ".", new String[]{"}", ".", "-", "+", "#", "@", "&", "%", "$"}, "The chat prefix"));
        this.mconf.add(new MConfMultiOption("homescreen", "client", new String[]{"client", "vanilla"}, "The home screen to use"));
        this.mconf.add(new MConfMultiOption("mpscreen", "client", new String[]{"client", "vanilla"}, "The multiplayer screen to use"));
        this.mconf.add(new MConfToggleable("customProcessIcon", true, "Whether or not to show a custom window icon"));
        this.mconf.add(particles);
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
        super.onEnable();
    }
}
