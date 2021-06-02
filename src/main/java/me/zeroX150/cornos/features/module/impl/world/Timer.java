package me.zeroX150.cornos.features.module.impl.world;

import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.etc.helper.STL;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class Timer extends Module {
    MConfNum newTps = new MConfNum("tps", 20, 100, 1, "new tps");

    public Timer() {
        super("Timer", "Changes the tps of your client", ModuleType.WORLD);
        mconf.add(newTps);
    }

    @Override
    public void onExecute() {
        STL.setClientTPS((float) STL.roundToNTh(newTps.getValue(), 2));
    }

    @Override
    public void onDisable() {
        STL.setClientTPS(20f);
    }

    @Override
    public String getContext() {
        return STL.roundToNTh(newTps.getValue(), 2) + "";
    }
}
