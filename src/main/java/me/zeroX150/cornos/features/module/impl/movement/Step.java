package me.zeroX150.cornos.features.module.impl.movement;

import me.zeroX150.cornos.Cornos;
import me.zeroX150.cornos.etc.config.MConfNum;
import me.zeroX150.cornos.features.module.Module;
import me.zeroX150.cornos.features.module.ModuleType;

public class Step extends Module {

    MConfNum height = new MConfNum("height", 1.0, 10, 0);

    public Step() {
        super("Step", "spiders are impressed", ModuleType.MOVEMENT);
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
        Cornos.minecraft.player.stepHeight = (float) ((MConfNum) this.mconf.getByName("height")).getValue();
        super.onExecute();
    }
}
