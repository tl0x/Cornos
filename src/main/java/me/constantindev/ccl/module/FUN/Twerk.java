package me.constantindev.ccl.module.FUN;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;

import java.util.concurrent.ThreadLocalRandom;

public class Twerk extends Module {

    Toggleable randomize = new Toggleable("randomize", false);

    public Twerk() {
        super("Twerk", "shake your ass for the whole server", MType.FUN);
        this.mconf.add(randomize);
    }

    @Override
    public void onExecute() {
        assert Cornos.minecraft.player != null;
        if (this.randomize.isEnabled()) {
            Cornos.minecraft.options.keySneak.setPressed(ThreadLocalRandom.current().nextBoolean());
        } else {
            Cornos.minecraft.options.keySneak.setPressed(!Cornos.minecraft.player.isSneaking());
        }
        super.onExecute();
    }
}
