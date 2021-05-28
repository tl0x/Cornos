package me.zeroX150.cornos.etc.config;

public class MConfToggleable extends MConf.ConfigKey {
    boolean enabled;

    public MConfToggleable(String name, boolean initialStatus) {
        super(name, initialStatus ? "on" : "off");
        enabled = initialStatus;
    }

    public void toggle() {
        enabled = !enabled;
        this.setValue(enabled ? "on" : "off");
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setValue(String newV) {
        switch (newV) {
            case "on":
                enabled = true;
                break;
            case "off":
                enabled = false;
                break;
            default:
                return;
        }
        super.setValue(newV);

    }
}
