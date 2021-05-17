package me.constantindev.ccl.features.module.impl.movement;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.config.MConfNum;
import me.constantindev.ccl.features.module.Module;
import me.constantindev.ccl.features.module.ModuleType;

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
