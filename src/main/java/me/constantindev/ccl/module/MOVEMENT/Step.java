package me.constantindev.ccl.module.MOVEMENT;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Num;
import me.constantindev.ccl.etc.ms.MType;

public class Step extends Module {

    Num height = new Num("height", 1.0, 10, 0);

    public Step() {
        super("Step", "Step up full blocks", MType.MOVEMENT);
        this.mconf.add(height);
    }

    @Override
    public void onEnable() {
        assert Cornos.minecraft.player != null;
        System.out.println(Cornos.minecraft.player.stepHeight);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.stepHeight = 0.6F;
        super.onDisable();
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        Cornos.minecraft.player.stepHeight = (float) ((Num) this.mconf.getByName("height")).getValue();
        super.onExecute();
    }
}
