package me.constantindev.ccl.etc.config;

public class Num extends ModuleConfig.ConfigKey {
    public double max;
    public double min;

    public Num(String k, double v, double max, double min) {
        super(k, v + "");
        this.max = max;
        this.min = min;
    }

    public double getValue() {
        return Double.parseDouble(this.value);
    }

    @Override
    public void setValue(String newV) {
        try {
            Double.parseDouble(newV);
            super.setValue(newV);
        } catch (Exception ignored) {
        }
    }
}
