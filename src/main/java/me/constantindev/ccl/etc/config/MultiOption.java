package me.constantindev.ccl.etc.config;

import me.constantindev.ccl.etc.helper.ClientHelper;

public class MultiOption extends ModuleConfig.ConfigKey {
    String[] possibleValues;

    public MultiOption(String k, String initialValue, String[] possible) {
        super(k, initialValue);
        this.possibleValues = possible;
    }

    @Override
    public void setValue(String newV) {
        boolean pass = false;
        for (String s : possibleValues) {
            if (s.equals(newV)) {
                pass = true;
                break;
            }
        }
        if (!pass) {
            ClientHelper.sendChat("[Config] You tried to set a value that is not possible. The values you can set are " + String.join(", ", possibleValues));
            return;
        }
        super.setValue(newV);
    }
}
