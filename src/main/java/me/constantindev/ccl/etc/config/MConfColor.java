/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: RGBAColor
# Created by constantin at 08:04, Mär 17 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.etc.config;

import me.constantindev.ccl.etc.helper.STL;

import java.awt.*;

public class MConfColor extends MConf.ConfigKey {
    Color c;
    boolean rainbow;

    public MConfColor(String k, Color rgb) {
        super(k, rgb.getRGB() + "");
        this.c = rgb;
    }

    public Color getColor() {
        Color finalcol = new Color(CConf.latestRGBVal);
        if (!rainbow) finalcol = c;
        return finalcol;
    }

    public void setColor(Color newColor) {
        c = newColor;
    }

    public int getRGB() {
        Color finalcol = new Color(CConf.latestRGBVal);
        if (!rainbow) finalcol = c;
        return finalcol.getRGB();
    }

    @Override
    public void setValue(String newV) {
        if (!STL.tryParseI(newV)) return;
        int bruh = Integer.parseInt(newV);
        if (bruh > 0xFFFFFF || bruh < 0) return;
        super.setValue(new Color(Integer.parseInt(newV)).getRGB() + "");
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public void setRainbow(boolean isRainbow) {
        rainbow = isRainbow;
    }
}
