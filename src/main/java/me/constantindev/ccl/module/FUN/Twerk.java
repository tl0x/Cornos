package me.constantindev.ccl.module.FUN;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.MConfToggleable;
import me.constantindev.ccl.etc.ms.ModuleType;

import java.util.concurrent.ThreadLocalRandom;

public class Twerk extends Module {

    MConfToggleable randomize = new MConfToggleable("randomize", false);

    public Twerk() {
        super("Twerk", "shake your ass for the whole server", ModuleType.FUN);
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
