package me.zeroX150.cornos.etc.config;

public class Toggleable$1 implements com.lukflug.panelstudio.settings.Toggleable {
    boolean value;

    /**
     * Constructor.
     *
     * @param value intial sate
     */
    public Toggleable$1(boolean value) {
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
