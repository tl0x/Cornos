package me.constantindev.ccl.etc;

public class Toggleable implements com.lukflug.panelstudio.settings.Toggleable {
    boolean value;

    /**
     * Constructor.
     *
     * @param value intial sate
     */
    public Toggleable(boolean value) {
        this.value = value;
    }

    public void setState(boolean newS) {
        value = newS;
    }

    @Override
    public void toggle() {
        value = !value;
    }

    @Override
    public boolean isOn() {
        return value;
    }
}
