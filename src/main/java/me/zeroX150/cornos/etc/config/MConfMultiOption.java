package me.zeroX150.cornos.etc.config;

import me.zeroX150.cornos.etc.helper.STL;

public class MConfMultiOption extends MConf.ConfigKey {
    public String[] possibleValues;
    public int current = 0;

    public MConfMultiOption(String k, String initialValue, String[] possible) {
        super(k, initialValue);
        this.possibleValues = possible;
    }

    @Override
    public void setValue(String newV) {
        boolean pass = false;
        int currentIndex = 0;
        for (String s : possibleValues) {
            if (s.equals(newV)) {
                pass = true;
                current = currentIndex;
                break;
            }
            currentIndex++;
        }
        if (!pass) {
            STL.notifyUser("[Config] You tried to set a value that is not possible. The values you can set are "
                    + String.join(", ", possibleValues));
            return;
        }
        super.setValue(newV);
    }
}
